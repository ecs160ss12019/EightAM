package EightAM.asteroids;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;
import static EightAM.asteroids.Constants.STARTING_LIVES;

class GameModel {

    Lock lock;
    Context context;
    int numOfAsteroids;
    int spaceWidth;
    int spaceHeight;
    int livesLeft;
    int score;
    boolean isPaused;

    //temp
    float shipPosX, shipPosY;

    //player stats
    PlayerStats stats;

    ArrayList<Asteroid> asteroidBelt;
    ArrayList<Bullet> bulletsFired;
    protected Ship playerShip;
    protected Alien alien;

    AsteroidFactory asteroidFactory;
    BulletFactory bulletFactory;
    CollisionChecker collisionChecker;


    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    protected GameModel(int screenWidth, int screenHeight, Context context) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        this.context = context;
        spaceWidth = screenWidth;
        spaceHeight = screenHeight;

        resetObjects();
        resetGameParam();
        initFactories();
        createObjects();

        // set player stats
        this.stats = new PlayerStats(screenWidth, screenHeight, context);
    }

    private void resetGameParam() {
        score = 0;
        numOfAsteroids = STARTING_ASTEROIDS;
        livesLeft = STARTING_LIVES;

        //Melissa's Code
        isPaused = false;
    }

    private void initFactories() {
        asteroidFactory = new AsteroidFactory(this);
        bulletFactory = new BulletFactory(this);
        collisionChecker = new CollisionChecker(this);
    }

    private void createObjects() {
        this.playerShip = new Ship(this, spaceWidth, spaceHeight, context);
        this.alien = new BigAlien(spaceWidth, spaceHeight, context);
        asteroidFactory.createAsteroidBelt(numOfAsteroids);
    }

    private void resetObjects() {
        this.asteroidBelt = new ArrayList<Asteroid>();
        this.bulletsFired = new ArrayList<Bullet>();
        this.playerShip = null;
        this.alien = null;
    }

    protected void destroyShip() {
        livesLeft--;
        if (livesLeft > 0) {
            respawnShip();
        } else {
            this.playerShip = null;
        }
        //TODO: push playerShip explosion event
    }

    private void respawnShip() {
        playerShip = new Ship(this, spaceWidth, spaceHeight, context);
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

    void update(long timeInMillisecond) {
        if (asteroidBelt.size() > 0) updateAsteroidBelt(timeInMillisecond);
        if (bulletsFired.size() > 0) updateBullets(timeInMillisecond);
        if (playerShip != null) playerShip.update(spaceWidth, spaceHeight, timeInMillisecond);
        if (alien != null) alien.update(spaceWidth, spaceHeight, timeInMillisecond);
        collisionChecker.shipCollision();
        collisionChecker.bulletsCollision();
    }

    /**
     *  TODO: change handle name to reflect functionality
     *
     * @param i input by user.
     * @return if to pause game.
     */
    // Indirection of input to update playerShip parameters
    boolean input(InputControl.Control i) {
        if (i.PAUSE) {
            Log.d("in gamemodel", "paused");
            isPaused = true;
            return true;
        }
        if (playerShip != null) {
            if(i.SPECIAL_1 && playerShip.canShoot()){
                Log.d("in gamemodel", "Fired");
                bulletFactory.fireBullet(playerShip);
            }
            playerShip.input(i.UP, i.LEFT, i.RIGHT, i.DOWN, i.SPECIAL_1);
            return false;
        }

        return false;
    }

}
