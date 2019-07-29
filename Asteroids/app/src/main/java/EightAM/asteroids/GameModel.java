package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DeathEvent;
import EightAM.asteroids.interfaces.DeathHandler;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BasicShipSpec;

public class GameModel implements GameState, DeathHandler, ShotListener {

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
    Set<ObjectID> collideables;
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
        ((Ship) ship).linkShotListener(this);
        currPlayerShip = ship.getID();
        objectMap.put(ship.getID(), ship);

        Log.d("respawn ship", "hitboxX:" + ship.getObjPos().x);
        Log.d("respawn ship", "hitboxY:" + ship.getObjPos().y);
    }

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
        // destruction side effect
        if (target instanceof Destructable) ((Destructable) target).destruct();
        if (actorID == currPlayerShip || targetID == currPlayerShip) onDeath();
        deleteSet.add(targetID);
    }

    public void removeObjects() {
        GameObject objectToDel;
        for (ObjectID id : deleteSet) {
            deleteSet.remove(id);
            objectToDel = objectMap.get(id);
            if (objectToDel instanceof Collision) {
                //collideables.remove(id);
            }
            if (objectToDel instanceof Asteroid) {
                asteroids.remove(id);
            } else if (objectToDel instanceof Alien) {
                aliens.remove(id);
            } else if (objectToDel instanceof Bullet) {
                bullets.remove(id);
            } else {
                throw new AssertionError("Unrecognized GameObject type");
            }
            objectMap.remove(id);
        }
    }

    public void onGameEnd() {
        this.gameOver = true;
        endStats = new EndGameStats(stats);
    }

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
        enumerateCollision(bullets);
        enumerateCollision(aliens);
        //enumerateCollision(collideables);

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
            //((Collision) objectMap.get(objectID)).onCollide(objectMap.get(collisionID));
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
    public Set<ObjectID> getAsteroidIDs() {
        return asteroids;
    }

    @Override
    public Set<ObjectID> getAlienIDs() {
        return aliens;
    }

    @Override
    public Set<ObjectID> getBulletIDs() {
        return bullets;
    }

    @Override
    public GameObject getGameObject(ObjectID id) {
        return objectMap.get(id);
    }

    @Override
    public void processDeathEvent(DeathEvent event) {
        for (GameObject o : event.createOnDeath()) {
            this.addObject(o);
        }
    }

    private void addObject(GameObject o) {
        ObjectID id = o.getID();
        if (!objectMap.containsKey(id)) {
            objectMap.put(id, o);
            if (o instanceof Collision) {
                collideables.add(id);
            }
            if (o instanceof Alien) {
                aliens.add(id);
            } else if (o instanceof Asteroid) {
                asteroids.add(id);
            } else if (o instanceof Bullet) {
                bullets.add(id);
            } else if (o instanceof Ship) {
                currPlayerShip = id;
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
        if (getPlayerShip().canShoot())
            BulletGenerator.getInstance().createBullet(bullets, objectMap, shooter, this);
    }
}
