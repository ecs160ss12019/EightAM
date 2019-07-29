package EightAM.asteroids;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BasicShipSpec;

public class GameModel implements GameState, EventHandler, ShotListener {
    Context context;

    Point spaceSize;
    boolean isPaused;
    GameStats stats;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> collideables;
    private boolean gameOver;
    Set<ObjectID> deleteSet;
    private Lock lock;

    int activeAsteroids;
    int activeAliens;

    ObjectID currPlayerShip;

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
        deleteSet = new HashSet<>();
        collideables = new HashSet<>();

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
        respawnShip();
        AsteroidGenerator.getInstance().createBelt(this);
        // Testing for particle effect, wait for the onCollision to be completed
        Point p = new Point(spaceSize.x/2, spaceSize.y/2);
        ParticleGenerator.getInstance().createParticles(objectMap, spaceSize, p);
    }

    //Ship stuff *START*
    private void resetObjects() {
        this.stats = new GameStats(spaceSize, context);
        objectMap.clear();
        this.currPlayerShip = null;
    }

//    protected void destroyShip() {
//        objectMap.remove(currPlayerShip);
//        stats.subLive();
//        //TODO: push currPlayerShip explosion event
//    }

    private void respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new BasicShipSpec());
        ((Ship) ship).linkShotListener(this);
        ((Ship) ship).registerDestructListener(this);
        ((Ship) ship).registerEventHandler(this);
        ship.hitbox.offsetTo(spaceSize.x/2.0f, spaceSize.y/2.0f);
        currPlayerShip = ship.getID();
        objectMap.put(ship.getID(), ship);
        collideables.add(ship.getID());
    }
    //Ship stuff *END*

    private boolean isInvulnerable(GameObject gameObject) {
        if (gameObject instanceof Invulnerable) {
            return ((Invulnerable) gameObject).isInvulnerable();
        }
        return false;
    }

    @Deprecated
    public void onCollision(ObjectID actorID, ObjectID targetID) {
        GameObject target = objectMap.get(targetID);
        GameObject actor = objectMap.get(actorID);
        if (target == null) return;
        if (isInvulnerable(target) || isInvulnerable(actor)) return;
        // if target is not invulnerable, destroy it
        if (actorID.getFaction() == Faction.Player) stats.score(target);
        if (actorID == currPlayerShip || targetID == currPlayerShip) onDeath();
        deleteSet.add(targetID);
    }

    public void removeObjects() {
        GameObject objectToDel;
        for (ObjectID id : deleteSet) {
            objectToDel = objectMap.get(id);
            if (objectToDel instanceof Collision) {
                collideables.remove(id);
            }
            objectMap.remove(id);
            if (id == currPlayerShip) onDeath();
        }
        deleteSet.clear();
        /*
        Deque<ObjectID> deleteQueue = new ArrayDeque<>(deleteSet);
        while (!deleteQueue.isEmpty()) {
            ObjectID id = deleteQueue.pop();
            deleteSet.remove(id);
            collideables.remove(id);
            objectMap.remove(id);
            if (id == currPlayerShip) onDeath();
        }
        */
    }

    public void onGameEnd() {
        this.gameOver = true;
        endStats = new EndGameStats(stats);
    }

    public void onDeath() {
        currPlayerShip = null;
        stats.subLive();
        if (stats.getLife() > 0) {
            respawnShip();
        } else {
            onGameEnd();
        }
    }

    EndGameStats endGameStats() {
        return endStats;
    }

    void update(long timeInMillisecond) {
        for (GameObject o : objectMap.values()) {
            o.update(spaceSize, timeInMillisecond);
        }
        //Collisions
        CollisionChecker.enumerateCollisions(this);

        if (activeAsteroids == 0 && activeAliens == 0) {
            onWaveComplete();
            startNextWave();
        }

        //Remove Collided Objects
        if(!deleteSet.isEmpty())removeObjects();
    }

    private void startNextWave() {
        AsteroidGenerator.getInstance().createBelt(this);
        //AlienGenerator.getInstance().createAlien(this);
    }

    private void onWaveComplete() {
        AsteroidGenerator.getInstance().numOfAsteroids++;
    }

    /**
     * TODO: change handle name to reflect functionality
     *
     * @param i input by user.
     * @return if to pause game.
     */
    // Indirection of input to update currPlayerShip parameters
    void input(InputControl.Input i) {
        Log.d("check ship id",""+currPlayerShip);
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

    @Override
    public Set<ObjectID> getCollideableIDs() {
        return collideables;
    }


    @Override
    public GameObject getGameObject(ObjectID id) {
        return objectMap.get(id);
    }

    private void addObject(Collection<GameObject> objects) {
        for (GameObject o : objects) {
            ObjectID id = o.getID();
            if (!objectMap.containsKey(id)) {
                objectMap.put(id, o);
                if (o instanceof Collision) {
                    collideables.add(id);
                }
                if (o instanceof Bullet) {
                    ((Bullet)o).registerDestructListener(this);
                }
                if (o instanceof Ship) {
                    currPlayerShip = id;
                }
            }
        }
    }

    @Override
    public void onDestruct(Destructable destructable) {
        ObjectID id = destructable.getID();
        deleteSet.add(id);
    }

    @Override
    public void onShotFired(Shooter shooter) {
        if (getPlayerShip().canShoot()) {
            addObject(BulletGenerator.createBullets(shooter));
        }
    }

    @Override
    public void createObjects(Collection<GameObject> objects) {
        addObject(objects);
    }

    @Override
    public void destroyObjects(Collection<Destructable> objects) {
        for (Destructable d : objects) {
            d.destruct();
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    Lock getLock() {
        return lock;
    }
}
