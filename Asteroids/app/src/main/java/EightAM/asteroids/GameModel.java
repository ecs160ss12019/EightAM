package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_SPAWN_PROB;

import android.content.Context;
import android.graphics.Point;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BasicShipSpec;

public class GameModel implements GameState, EventHandler, ShotListener {
    Context context;

    Point spaceSize;
    GameStats stats;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> collideables;
    private boolean gameOver;
    List<GameObject> createList;
    List<ObjectID> deleteList;
    private Lock lock;

    int activeAsteroids;
    int activeAliens;

    ObjectID currPlayerShip;

    //TODO: might change
    ObjectID alienID;

    EndGameStats endStats;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    public GameModel(Point screenSize, Context context) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        this.context = context;
        spaceSize = screenSize;
        objectMap = new HashMap<>();
        collideables = new HashSet<>();

        createList = new ArrayList<>();
        deleteList = new ArrayList<>();

        this.gameOver = false;
        this.stats = new GameStats(spaceSize, context);
    }

    void startGame() {
        this.gameOver = false;
        resetObjects();
        createObjects();
        stats.newGame();
    }

    private void createObjects() {
        addObjects(respawnShip());
        //addObjects(AsteroidGenerator.getInstance().createBelt(spaceSize, getPlayerShip()));
        addObjects(AlienGenerator.getInstance().createAlien(spaceSize));
    }

    //Ship stuff *START*
    private void resetObjects() {
        this.stats = new GameStats(spaceSize, context);
        objectMap.clear();
        this.currPlayerShip = null;
        //TODO: Might change
        this.alienID = null;
    }


    private Collection<GameObject> respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new BasicShipSpec());
        ship.hitbox.offsetTo(spaceSize.x / 2.0f, spaceSize.y / 2.0f);
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
        stats.setHighScore();
        endStats = new EndGameStats(stats);
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

    EndGameStats endGameStats() {
        return endStats;
    }

    /**
     * Updates the Game, i.e. move objects, check collisions, create new waves
     *
     * @param timeInMillisecond - time interval between frames, to be used
     *                          for moving objects.
     */
    void update(long timeInMillisecond) {
        for (GameObject o : objectMap.values()) {
            o.update(spaceSize, timeInMillisecond);
        }

        if (alienID != null) getAlien().tryShoot(getPlayerShip().getObjPos());

        for (Pair<Collision, GameObject> objectPair : CollisionChecker.enumerateCollisions(this)) {
            objectPair.first.onCollide(objectPair.second);
        }

        if (activeAsteroids == 0 && activeAliens == 0) {
            onWaveComplete();
            startNextWave();
        }

        //Remove Collided Objects
        removeObjects();
        putObjects();
    }

    private void startNextWave() {
        addObjects(AsteroidGenerator.getInstance().createBelt(spaceSize, getPlayerShip()));

        if (shouldSpawnAlien()) {
            addObjects(AlienGenerator.getInstance().createAlien(spaceSize));
        }
    }

    private void onWaveComplete() {
        AsteroidGenerator.getInstance().numOfAsteroids++;
    }

    /**
     * Probability function determine whether alien should spawn.
     * Called in startNextWave().
     *
     * @return true to spawn alien
     */
    private boolean shouldSpawnAlien() {
        Random rand = new Random();

        float f = rand.nextFloat();
        return (f < ALIEN_SPAWN_PROB);
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
                setListeners(o);
                updateGameParam(o, 1);
            }
        }
    }

    /**
     * Updates the parameters of the game depending on what object
     * is add to the game.
     *
     * @param object - the GameObject to check
     */
    private void updateGameParam(GameObject object, int i) {
        if (object instanceof Asteroid) {
            activeAsteroids += i;
        } else if (object instanceof Alien) {
            activeAliens += i;
        }
    }

    /**
     * Links/Registers the listeners for the GameObject
     * dependent on their Interfaces
     *
     * @param object - the GameObject to check
     */
    private void setListeners(GameObject object) {
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

    private void createDebris(GameObject object) {
        if (!((object instanceof Particle) || (object instanceof Bullet))) {
            addObjects(ParticleGenerator.getInstance().createParticles(object.getObjPos()));
        }
        if (object instanceof Asteroid) {
            addObjects(AsteroidGenerator.getInstance().breakUpAsteroid((Asteroid) object));
        }
    }

    @Override
    public void onDestruct(Destructable destructable) {
        updateGameParam((GameObject) destructable, -1);
        createDebris((GameObject) destructable);
        ObjectID id = destructable.getID();
        deleteList.add(id);
    }

    @Override
    public void onShotFired(Shooter shooter) {
        if (getPlayerShip().canShoot()) {
            addObjects(BulletGenerator.createBullets(shooter));
        }

        if (alienID != null) {
            if (getAlien().canShoot()) {
                addObjects(BulletGenerator.createBullets(shooter));
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
}
