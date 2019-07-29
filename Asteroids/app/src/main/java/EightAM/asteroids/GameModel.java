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

    Point spaceSize;
    boolean isPaused;
    GameStats stats;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> deleteSet;
    Set<ObjectID> collidables;

    int activeAsteroids;
    int activeAliens;

    ObjectID currPlayerShip;

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
        collidables = new HashSet<>();
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
    }

    private void createObjects() {
        respawnShip();
        AsteroidGenerator.getInstance().createBelt(this);
        Point p = new Point(spaceSize.x/2, spaceSize.y/2);
        ParticleGenerator.getInstance().createParticles(objectMap, spaceSize, p);
    }

    //Ship stuff *START*
    private void resetObjects() {
        this.stats = new GameStats(spaceSize, context);
        objectMap.clear();
        this.currPlayerShip = null;
        this.alien = null;
    }

    protected void destroyShip() {
        stats.subLive();
        //TODO: push currPlayerShip explosion event
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

    private void respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new BasicShipSpec());
        ((Ship) ship).linkShotListener(this);
        ((Ship) ship).registerDestructListener(this);
        ship.hitbox.offsetTo(spaceSize.x/2.0f, spaceSize.y/2.0f);
        currPlayerShip = ship.getID();
        objectMap.put(ship.getID(), ship);
        collidables.add(ship.getID());
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
        for (ObjectID id : deleteSet) {
            //Log.d("deleteSet id",""+ id.getId());
        }
        Deque<ObjectID> deleteQueue = new ArrayDeque<>(deleteSet);
        while (!deleteQueue.isEmpty()) {
            ObjectID id = deleteQueue.pop();
            deleteSet.remove(id);
            collidables.remove(id);
            objectMap.remove(id);
            if (id == currPlayerShip) onDeath();
        }
    }

    public void onGameEnd() {
        this.gameOver = true;
        endStats = new EndGameStats(stats);
    }

    EndGameStats endGameStats() {
        return endStats;
    }


    void update(long timeInMillisecond) {
        for (GameObject o : objectMap.values()) {
            o.update(spaceSize, timeInMillisecond);
        }
        //Collisions
        CollisionChecker.getInstance().enumerateCollisions(collidables, objectMap);

        if (activeAsteroids == 0 && activeAliens == 0) {
            // wave update
            onWaveComplete();
            // might want to implement idle period to display messages
            startNextWave();
        }

        //Remove Collided Objects
        if(!deleteSet.isEmpty())removeObjects();
    }

    private void startNextWave() {
        AsteroidGenerator.getInstance().createBelt(this);
        AlienGenerator.getInstance().createAlien(this);
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

    public Set<ObjectID> getCollidableIDs() {
        return collidables;
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
                collidables.add(id);
            }
            if (o instanceof Ship) {
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
            BulletGenerator.getInstance().createBullet(this, shooter);
    }
}
