package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_SPAWN_PROB;
import static EightAM.asteroids.Constants.ASTEROID_INC_WAVE;
import static EightAM.asteroids.Constants.BOUNDARY_OFFSET;
import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BasicShipSpec;

public class GameModel implements GameState, EventHandler, ShotListener {

    GameStats stats;
    Context context;
    Wave wave;
    private Lock lock;
    RectF boundaries;
    RectF spawnBoundaries;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> collideables;
    List<GameObject> createList;
    List<ObjectID> deleteList;

    ObjectID currPlayerShip;
    private boolean gameOver;

    //TODO: might change
    ObjectID alienID;

    AudioListener audioListener;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    public GameModel(Point screenSize, Context context, AudioListener audio) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        this.context = context;
        boundaries = new RectF(0, 0, screenSize.x, screenSize.y);
        spawnBoundaries = new RectF(boundaries.left - BOUNDARY_OFFSET,
                boundaries.top - BOUNDARY_OFFSET, boundaries.right + BOUNDARY_OFFSET,
                boundaries.bottom + BOUNDARY_OFFSET);
        objectMap = new HashMap<>();
        collideables = new HashSet<>();

        createList = new ArrayList<>();
        deleteList = new ArrayList<>();

        this.gameOver = false;
        this.stats = new GameStats(boundaries);
        this.audioListener = audio;
    }

    void startGame() {
        this.gameOver = false;
        resetObjects();
        stats.newGame();
        wave = new Wave(this, boundaries, spawnBoundaries);
        wave.asteroidSpawnCount = STARTING_ASTEROIDS;
        wave.asteroidInc = ASTEROID_INC_WAVE;
        wave.alienSpawnProb = ALIEN_SPAWN_PROB;
        addObjects(respawnShip());
        putObjects();
    }

    //Ship stuff *START*
    private void resetObjects() {
        this.stats = new GameStats(boundaries);
        objectMap.clear();
        this.currPlayerShip = null;
        //TODO: Might change
        this.alienID = null;
    }


    private Collection<GameObject> respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new BasicShipSpec());
        ship.hitbox.offsetTo(boundaries.width() / 2.0f, boundaries.height() / 2.0f);
        return Collections.singleton(ship);
    }
    //Ship stuff *END*

    @Deprecated
    public void onCollision(ObjectID actorID, ObjectID targetID) {
        GameObject target = objectMap.get(targetID);
        GameObject actor = objectMap.get(actorID);
        if (actorID.getFaction() == Faction.Player) stats.score(target);
    }

    public void removeObjects() {
        GameObject objectToDel;
        for (ObjectID id : deleteList) {
            objectToDel = objectMap.get(id);
            if (objectToDel instanceof Collision) {
                collideables.remove(id);
            }
            objectMap.remove(id);
            if (id == currPlayerShip) onDeath();
            if (id == alienID) alienID = null;
        }
        deleteList.clear();
    }

    public void onGameEnd() {
        this.gameOver = true;
    }

    public void onDeath() {
        currPlayerShip = null;
        stats.subLive();
        if (stats.getLife() > 0) {
            addObjects(respawnShip());
        } else {
            onGameEnd();
        }
    }

    /**
     * Updates the Game, i.e. move objects, check collisions, create new waves
     *
     * @param timeInMillisecond - time interval between frames, to be used
     *                          for moving objects.
     */
    void update(long timeInMillisecond) {
        putObjects();
        for (GameObject o : objectMap.values()) {
            o.update(timeInMillisecond);
        }

        if (alienID != null) getAlien().tryShoot(getPlayerShip().getObjPos());

        for (Pair<Collision, GameObject> objectPair : CollisionChecker.enumerateCollisions(this)) {
            objectPair.first.onCollide(objectPair.second);
        }

        wave.updateDuration(timeInMillisecond);

        removeObjects();
    }

    /**
     * TODO: change handle name to reflect functionality
     *
     * @param i input by user.
     * @return if to pause game.
     */
    // Indirection of input to update currPlayerShip parameters
    void input(InputControl.Input i) {
        //Log.d("check ship id",""+currPlayerShip);
        if (currPlayerShip != null) {
            getPlayerShip().input(i);
            if (i.UP) {
                audioListener.onAccelerate();
            }
        }
    }

    public Ship getPlayerShip() {
        if (currPlayerShip != null && objectMap.containsKey(currPlayerShip)) {
            return (Ship) objectMap.get(currPlayerShip);
        } else {
            return null;
        }
    }

    //TODO: Might change
    public Alien getAlien() {
        if (alienID != null && objectMap.containsKey(alienID)) {
            return (Alien) objectMap.get(alienID);
        } else {
            return null;
        }
    }

    @Override
    public Set<ObjectID> getCollideableIDs() {
        return collideables;
    }

    @Override
    public GameObject getGameObject(ObjectID id) {
        return objectMap.get(id);
    }

    /**
     * Adds objects to the create list
     *
     * @param objects - a collection of objects to be passed in by
     *                the generators
     */
    private void addObjects(Collection<GameObject> objects) {
        createList.addAll(objects);
    }

    /**
     * Transfers the objects on the create list to objectMap
     */
    private void putObjects() {
        for (GameObject o : createList) {
            ObjectID id = o.getID();
            if (!objectMap.containsKey(id)) {
                objectMap.put(id, o);
                if (o instanceof Collision) collideables.add(id);
                if (o instanceof Ship) currPlayerShip = id;
                //TODO: Might change
                if (o instanceof Alien) alienID = id;
                addListeners(o);
                addMoveStrategy(o);
                updateWaveParam(o, 1);
            }
        }
        createList.clear();
    }

    /**
     * Updates the parameters of the game depending on what object
     * is add/del to the game.
     *
     * @param object - the GameObject to check
     */
    private void updateWaveParam(GameObject object, int i) {
        if (object instanceof Asteroid) {
            wave.updateAsteroids(i);
        } else if (object instanceof Alien) {
            wave.updateAliens(i);
        }
    }

    /**
     * Links/Registers the listeners for the GameObject
     * dependent on their Interfaces
     *
     * @param object - the GameObject to check
     */
    private void addListeners(GameObject object) {
        if (object instanceof Destructable) {
            ((Destructable) object).registerDestructListener(this);
        }
        if (object instanceof Shooter) {
            ((Shooter) object).linkShotListener(this);
        }
        if (object instanceof EventGenerator) {
            ((EventGenerator) object).registerEventHandler(this);
        }
    }

    private void addMoveStrategy(GameObject object) {
        if (object instanceof Alien || object instanceof Asteroid) {
            object.setMoveStrategy(new MoveWrapWithSpawn(boundaries, spawnBoundaries));
        } else {
            object.setMoveStrategy(new MoveWrap(boundaries));
        }
    }

    private void createDebris(GameObject object) {
        if (!((object instanceof Particle) || (object instanceof Bullet))) {
            addObjects(ParticleGenerator.getInstance().createParticles(object.getObjPos()));
            audioListener.onExplosion();
        }
        if (object instanceof Asteroid) {
            addObjects(AsteroidGenerator.breakUpAsteroid((Asteroid) object));
        }
    }

    @Override
    public void onDestruct(Destructable destructable) {
        updateWaveParam((GameObject) destructable, -1);
        createDebris((GameObject) destructable);
        ObjectID id = destructable.getID();
        deleteList.add(id);
    }

    @Override
    public void onShotFired(Shooter shooter) {
        if (getPlayerShip().canShoot()) {
            addObjects(BulletGenerator.createBullets(shooter));
            audioListener.onShoot();
        }

        if (alienID != null) {
            if (getAlien().canShoot()) {
                addObjects(BulletGenerator.createBullets(shooter));
                audioListener.onShoot();

            }
        }
    }

    @Override
    public void createObjects(Collection<GameObject> objects) {
        addObjects(objects);
    }

    @Override
    public void destroyObjects(Collection<Destructable> objects) {
        for (Destructable d : objects) {
            d.destruct(null);
        }
    }

    @Override
    public void teleportObjects(Collection<ObjectID> tpList) {
        for (ObjectID objectID : tpList) {
            GameObject obj = objectMap.get(objectID);
            if (obj != null) {
                obj.hitbox.offsetTo((float) (Math.random() * (boundaries.right - boundaries.left)
                                + boundaries.left),
                        (float) (Math.random() * (boundaries.bottom - boundaries.top)
                                + boundaries.top));
            }
            // TODO: play a sound
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    Lock getLock() {
        return lock;
    }

    @Override
    public void processScore(DestroyedObject object) {
        if (object.getKiller().getFaction() == Faction.Player) {
            stats.plusScore(object.score());
        }
    }

    @Override
    public void sendMessage(Messages.Message message) {
        Messages.addMessage(message);
    }
}
