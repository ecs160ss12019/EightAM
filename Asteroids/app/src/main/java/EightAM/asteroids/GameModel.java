package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.GameListener;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.specs.BasicShipSpec;

public class GameModel implements GameListener, GameState {

    protected Alien alien;
    boolean gameOver;
    Lock lock;
    Context context;
    int numOfAsteroids;
    Point spaceSize;
    boolean isPaused;
    GameStats stats;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> asteroids;
    Set<ObjectID> aliens;
    Set<ObjectID> bullets;
    Set<ObjectID> deleteSet;
    Set<ObjectID> collidables;
    Deque<ObjectID> deleteQueue;
    ObjectID currPlayerShip;
    ObjectID collisionID;

    EndGameStats endStats;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    protected GameModel(Point screenSize, Context context) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        this.context = context;
        spaceSize = screenSize;
        objectMap = new HashMap<>();
        asteroids = new HashSet<>();
        aliens = new HashSet<>();
        bullets = new HashSet<>();
        deleteSet = new HashSet<>();
        deleteQueue = new ArrayDeque<>();

        resetObjects();
        resetGameParam();
        createObjects();

        this.gameOver = false;
        // set player stats
        this.stats = new GameStats(spaceSize, context);
    }

    private void resetGameParam() {
        stats.newGame();
        numOfAsteroids = STARTING_ASTEROIDS;
    }

    private void createObjects() {
        respawnShip();
        AsteroidGenerator.getInstance().createBelt(asteroids, objectMap, spaceSize,
                getPlayerShip());
        //this.alien = new BigAlien(spaceSize, context);
    }

    private void resetObjects() {
        this.stats = new GameStats(spaceSize, context);
        objectMap.clear();
        asteroids.clear();
        aliens.clear();
        bullets.clear();
        this.currPlayerShip = null;
        this.alien = null;
    }

    protected void destroyShip() {
        objectMap.remove(currPlayerShip);
        stats.subLive();
        //TODO: push currPlayerShip explosion event
    }

    private void respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new BasicShipSpec());
        currPlayerShip = ship.getID();
        objectMap.put(ship.getID(), ship);
    }

    private boolean isInvulnerable(GameObject gameObject) {
        if (gameObject instanceof Invulnerable) {
            return ((Invulnerable) gameObject).isInvulnerable();
        }
        return false;
    }

    @Override
    public void onCollision(ObjectID actorID, ObjectID targetID) {
        GameObject target = objectMap.get(targetID);
        GameObject actor = objectMap.get(actorID);
        if (target == null) return;
        if (isInvulnerable(target) || isInvulnerable(actor)) return;
        // if target is not invulnerable, destroy it
        if (actorID.getFaction() == Faction.Player) stats.score(target);
        // destruction side effect
        if (target instanceof Destructable) ((Destructable) target).destruct();
        if (actorID == currPlayerShip || targetID == currPlayerShip) onDeath();
        if (!deleteSet.contains(targetID)) {
            deleteSet.add(targetID);
            deleteQueue.push(targetID);
        }
    }

    public void removeObjects() {
        GameObject objectToDel;
        ObjectID objectID;
        deleteSet.clear();
        while (deleteQueue.size() > 0) {
            objectID = deleteQueue.pop();
            objectToDel = objectMap.get(objectID);
            if (objectToDel instanceof Asteroid) {
                asteroids.remove(objectID);
            } else if (objectToDel instanceof Alien) {
                aliens.remove(objectID);
            } else if (objectToDel instanceof Bullet) {
                bullets.remove(objectID);
            } else {
                throw new AssertionError("Unrecognized GameObject type");
            }
            objectMap.remove(objectID);
        }
    }

    @Override
    public void onGameEnd() {
        this.gameOver = true;
        endStats = new EndGameStats(stats);
    }

    @Override
    public void onDeath() {
        destroyShip();
        currPlayerShip = null;
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
        computeCollision(currPlayerShip);
//        enumerateCollision(bullets);
//        enumerateCollision(aliens);
        enumerateCollision(collidables);

        if (asteroids.size() == 0 && aliens.size() == 0) {
            // wave update
            onWaveComplete();
            // might want to implement idle period to display messages
            startNextWave();
        }

        //Remove Collided Objects
        removeObjects();
    }

    private void startNextWave() {

    }

    private void onWaveComplete() {

    }

    private void computeCollision(ObjectID objectID) {
        collisionID = CollisionChecker.collidesWith(objectMap.get(objectID), objectMap.values());
        if (collisionID != null) {
            ((Collision) objectMap.get(objectID)).
                    onCollision(objectID, collisionID);
        }
    }

    private void enumerateCollision(Set<ObjectID> objectIDSet) {
        for (ObjectID objectID : objectIDSet) {
            computeCollision(objectID);
        }
    }

    /**
     * TODO: change handle name to reflect functionality
     *
     * @param i input by user.
     * @return if to pause game.
     */
    // Indirection of input to update currPlayerShip parameters
    void input(InputControl.Input i) {
        if (currPlayerShip != null) {
            if (i.SHOOT && getPlayerShip().canShoot()) {
                Log.d("in gamemodel", "Fired");
                //TODO:Fire Bullet
            }
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
}
