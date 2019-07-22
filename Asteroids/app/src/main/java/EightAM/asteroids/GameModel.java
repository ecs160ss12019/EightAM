package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;
import static EightAM.asteroids.Constants.STARTING_LIVES;

import android.content.Context;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    ArrayList<Asteroid> asteroidBelt;
    ArrayList<Bullet> bulletsFired;
    protected Ship playerShip;
    protected Alien alien;


    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    protected GameModel(int screenWidth, int screenHeight, Context context) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        resetObjects();
        this.context = context;
        score = 0;
        numOfAsteroids = STARTING_ASTEROIDS;
        livesLeft = STARTING_LIVES;
        spaceWidth = screenWidth;
        spaceHeight = screenHeight;
        isPaused = false;

        // TODO: should use factories to create entities
        this.playerShip = new Ship(this, spaceWidth, spaceHeight, context);
        this.alien = new BigAlien(spaceWidth, spaceHeight, context);

        //this.createAsteroidBelt(context);
        //this.asteroid = new Asteroid(spaceWidth, spaceHeight, playerShip.shipWidth, playerShip.shipHeight, context);
        this.createAsteroidBelt(context);
    }

    private void resetObjects() {
        this.asteroidBelt = new ArrayList<Asteroid>();
        this.bulletsFired = new ArrayList<Bullet>();
        this.playerShip = null;
        this.alien = null;
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
                createBullet(playerShip);
            }
            playerShip.input(i.UP, i.LEFT, i.RIGHT, i.DOWN, i.SPECIAL_1);
            return false;
        }

        return false;
    }

    /**
     * Creates/Initializes an Asteroid belt (array of asteroids).
     */
    private void createAsteroidBelt(Context context) {
        for (int i = 0; i < numOfAsteroids; i++) {
            asteroidBelt.add(new Asteroid(this, spaceWidth, spaceHeight, this.playerShip, context));
        }
        Log.d("in gamemodel", "asteroidbelt has " + asteroidBelt.size() + " object id is " + asteroidBelt.get(0).objectID);
    }

    /**
     * Creates a bullet based on the shooter's orientation and position
     *
     * @param shooter - ObjectID of the shooter i.e. who's shooting the bullet
     */
    private void createBullet(Shooter shooter) {
        //TODO: get Position and Angle/Orientation of the Shooter (Ship and Alien)
        //TODO: Consult with playerShip team to retrieve orientation and position
        Log.d("in gamemodel", "Creating Bullet");
        bulletsFired.add(new Bullet(shooter));
    }


    private void updateAsteroidBelt(long timeInMillisecond) {
        for (int i = 0; i < asteroidBelt.size(); i++) {
            asteroidBelt.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    /**
     * Checks if any of the bullets have reached their max range.
     * If so, these bullets are deleted.
     */
    private void checkBulletRange() {
        Deque<Integer> bulletsToDelete = new ArrayDeque<Integer>();
        for (int i = 0; i < bulletsFired.size(); i++) {
            if (bulletsFired.get(i).reachedMaxRange()) {
                bulletsToDelete.push(i);
            }
        }
        while (bulletsToDelete.size() > 0) {
            int bulletIndex = bulletsToDelete.pop();
            bulletsFired.remove(bulletIndex);
        }
    }

    /**
     * Updates bullets' positions within the game model
     */
    private void updateBullets(long timeInMillisecond) {
        this.checkBulletRange();
        for (int i = 0; i < bulletsFired.size(); i++) {
            bulletsFired.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    private void destroyShip() {
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

    private void destroyAsteroid(int asteroidIndex) {
        asteroidBelt.remove(asteroidIndex);
        //TODO: push asteroid explosion event
    }

    private void detectShipCollision() {
        for (int i = 0; i < asteroidBelt.size(); i++) {
            if (playerShip.detectCollisions(asteroidBelt.get(i))) {
                this.destroyShip();
                this.destroyAsteroid(i);
                break;
            }
        }
        // TODO: Add aliens collisions
    }

    private void bulletsCollision() {
        /*
        Iterator <Bullet>bulletsFiredIter = bulletsFired.iterator();
        Iterator <Asteroid>asteroidBeltIter = asteroidBelt.iterator();

        while (bulletsFiredIter.hasNext()) {
            while (asteroidBeltIter.hasNext()) {
                if (bulletsFiredIter.next().detectCollisions(asteroidBeltIter.next())){
                    bulletsFiredIter.remove();
                    asteroidBeltIter.remove();
                }
            }
        }
        */
        Deque<Integer> bulletsToDelete = new ArrayDeque<>();
        Deque<Integer> asteroidsToDelete = new ArrayDeque<>();
        HashSet<Integer> asteroidSet = new HashSet<>();
        for (int i = 0; i < bulletsFired.size(); i++) {
            for (int j = 0; j < asteroidBelt.size(); j++) {
                if (bulletsFired.get(i).detectCollisions(asteroidBelt.get(j))) {
                    bulletsToDelete.push(i);
                    if(!asteroidSet.contains(j)){
                        asteroidSet.add(j);
                        asteroidsToDelete.push(j);
                    }
                }
            }
        }

        while (bulletsToDelete.size() > 0) {
            int bulletIndex = bulletsToDelete.pop();
            bulletsFired.remove(bulletIndex);
        }

        while (asteroidsToDelete.size() > 0) {
            int asteroidIndex = asteroidsToDelete.pop();
            destroyAsteroid(asteroidIndex);
        }
    }

    //    protected void computeCollisions() {
    //        this.detectShipCollision();
    //        this.bulletsCollision();
    //    }

    void update(long timeInMillisecond) {
        alien.update(spaceWidth, spaceHeight, timeInMillisecond);
        updateAsteroidBelt(timeInMillisecond);
        updateBullets(timeInMillisecond);
        if (playerShip != null) playerShip.update(spaceWidth, spaceHeight, timeInMillisecond);
        detectShipCollision();
        bulletsCollision();
    }

    protected void removeEntity() {

    }

}
