package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.GameListener;
import EightAM.asteroids.interfaces.Invulnerable;

class GameModel implements GameListener {

    protected Alien alien;
    boolean gameOver;
    Lock lock;
    Context context;
    int numOfAsteroids;
    int spaceWidth;
    int spaceHeight;
    boolean isPaused;
    //temp
    float shipPosX, shipPosY;
    //player stats
    GameStats stats;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> asteroids;
    Set<ObjectID> aliens;
    //    Set<ObjectID> playerShips;
    Set<ObjectID> bullets;
    ArrayList<Asteroid> asteroidBelt;
    ArrayList<Bullet> bulletsFired;
    ObjectID currPlayerShip;
    AsteroidFactory asteroidFactory;
    BulletFactory bulletFactory;

    EndGameStats endStats;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    protected GameModel(int screenWidth, int screenHeight, Context context) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        this.context = context;
        spaceWidth = screenWidth;
        spaceHeight = screenHeight;
        objectMap = new HashMap<>();
        asteroids = new HashSet<>();
        aliens = new HashSet<>();
        bullets = new HashSet<>();
        //        playerShips = new HashSet<>();

        resetObjects();
        resetGameParam();
        initFactories();
        createObjects();

        this.gameOver = false;
        // set player stats
        this.stats = new GameStats(screenWidth, screenHeight, context);
    }

    private void resetGameParam() {
        stats.newGame();
        numOfAsteroids = STARTING_ASTEROIDS;
    }

    private void initFactories() {
        asteroidFactory = new AsteroidFactory(this);
        bulletFactory = new BulletFactory(this);
    }

    private void createObjects() {
        Ship ship = new Ship(this, spaceWidth, spaceHeight, context);
        objectMap.put(ship.getID(), ship);
        this.alien = new BigAlien(spaceWidth, spaceHeight, context);
        asteroidFactory.createAsteroidBelt(numOfAsteroids);
    }

    private void resetObjects() {
        this.stats = new GameStats(spaceWidth, spaceHeight, context);
        objectMap.clear();
        asteroids.clear();
        aliens.clear();
        bullets.clear();
        //        playerShips.clear();
        this.asteroidBelt = new ArrayList<Asteroid>();
        this.bulletsFired = new ArrayList<Bullet>();
        this.currPlayerShip = null;
        this.alien = null;
    }

    protected void destroyShip() {
        //        playerShips.remove(currPlayerShip);
        objectMap.remove(currPlayerShip);
        stats.subLive();
        //TODO: push currPlayerShip explosion event
    }

    private void respawnShip() {
        Ship ship = new Ship(this, spaceWidth, spaceHeight, context);
    }

    private void updateAsteroidBelt(long timeInMillisecond) {
        for (int i = 0; i < asteroidBelt.size(); i++) {
            asteroidBelt.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    private void updateBullets(long timeInMillisecond) {
        bulletFactory.deleteOutOfRange();
        for (int i = 0; i < bulletsFired.size(); i++) {
            bulletsFired.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }


    @Override
    public void onCollision(ObjectID actorID, ObjectID targetID) {
        GameObject target = objectMap.get(targetID);
        if (target == null) return;
        if (target instanceof Invulnerable) {
            if (((Invulnerable) target).isInvulnerable()) return;
        }
        // if target is not invulnerable, destroy it
        if (actorID.getFaction() == Faction.Player) stats.score(target);
        // destruction side effect
        if (target instanceof Destructable) ((Destructable) target).destruct();
        if (target instanceof Asteroid) {
            asteroids.remove(targetID);
        } else if (target instanceof Alien) {
            aliens.remove(targetID);
        } else if (target instanceof Bullet) {
            bullets.remove(targetID);
        } else if (target instanceof Ship) {
            //            playerShips.remove(targetID);
            onDeath();
            return;
        } else {
            throw new AssertionError("Unrecognized GameObject type");
        }
        objectMap.remove(targetID);
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

    EndGameStats endGameStats() { return endStats;}


    void update(long timeInMillisecond) {
        for (GameObject o : objectMap.values()) {
            o.update(spaceWidth, spaceHeight, timeInMillisecond);
        }
        getPlayerShip().update(spaceWidth, spaceHeight, timeInMillisecond);
        if (alien != null) {
            alien.update(spaceWidth, spaceHeight, timeInMillisecond);
            if (alien.canShoot) {
                alien.shoot(getPlayerShip().hitbox.centerX(), getPlayerShip().hitbox.centerY());
                bulletFactory.fireBullet(alien);
            }
        }
        // Ship Collision
        for (GameObject o : CollisionChecker.collidesWith(getPlayerShip(), objectMap.values())) {
            onCollision(currPlayerShip, o.getID());
        }
        //        CollisionChecker.shipCollision(this);
        //        CollisionChecker.bulletsCollision(this);
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
                bulletFactory.fireBullet(getPlayerShip());
            }
            getPlayerShip().input(i);
        }
    }

    Ship getPlayerShip() {
        if (currPlayerShip != null && objectMap.containsKey(currPlayerShip)) {
            return (Ship) objectMap.get(currPlayerShip);
        } else {
            return null;
        }
    }
}
