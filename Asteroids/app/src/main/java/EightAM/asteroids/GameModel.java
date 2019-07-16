package EightAM.asteroids;

import android.content.Context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

class GameModel {

    static final int STARTING_ASTEROIDS = 5;
    static final int STARTING_LIVES = 1;

    static int numOfAsteroids;
    static int spaceWidth;
    static int spaceHeight;
    static int livesLeft;

    static int score;

    //temp
    float shipPosX, shipPosY;

    protected ArrayList<Asteroid> asteroidBelt;
    protected ArrayList<Bullet> bulletsFired;
    protected Ship ship;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     *
     * @param screenWidth
     * @param screenHeight
     * @param context
     */
    protected GameModel(int screenWidth, int screenHeight, Context context) {
        //TODO: Initialize Grid... Maybe?

        resetObjects();

        this.score = 0;
        this.numOfAsteroids = STARTING_ASTEROIDS;
        this.livesLeft = STARTING_LIVES;
        this.ship = new Ship(spaceWidth,spaceHeight, context);
        this.createAsteroidBelt(context);
    }

    private void resetObjects() {
        this.asteroidBelt = new ArrayList<Asteroid>();
        this.bulletsFired = new ArrayList<Bullet>();
        this.ship = null;
    }

    /**
     * Creates/Initializes an Asteroid belt (array of asteroids).
     *
     * @param context
     */
    private void createAsteroidBelt(Context context /*, float shipPosX, float shipPosY*/){
        for (int i = 0; i < numOfAsteroids; i ++){
            asteroidBelt.add(new Asteroid(spaceWidth,spaceHeight,shipPosX, shipPosY, context));
        }
    }

    /**
     * Creates a bullet based on the shooter's orientation and position
     *
     * @param shooter - ObjectID of the shooter i.e. who's shooting the bullet
     */
    private void createBullet(GameObject.ObjectID shooter){
        //TODO: get Position and Angle/Orientation of the Shooter (Ship and Alien)
        //TODO: Consult with ship team to retrieve orientation and position
        float shooterPosX, shooterPosY, shooterAngle;
        //bulletsFired.add(new Bullet(shooter, shooterPosX, shooterPosY, shooterAngle));
    }

    private void updateAsteroidBelt(long timeInMillisecond){
        for (int i = 0; i < numOfAsteroids; i ++){
            asteroidBelt.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    /**
     * Checks if any of the bullets have reached their max range.
     * If so, these bullets are deleted.
     */
    private void checkBulletRange() {
        Deque<Integer> bulletsToDelete = new ArrayDeque<Integer>();
        for (int i = 0; i < bulletsFired.size(); i ++){
            if (bulletsFired.get(i).reachedMaxRange()){
                bulletsToDelete.push(i);
            }
        }
        while(bulletsToDelete.size() > 0){
            int bulletIndex = bulletsToDelete.pop();
            bulletsFired.remove(bulletIndex);
        }
    }

    /**
     * Updates bullets' positions within the game model
     * @param timeInMillisecond
     */
    private void updateBullets(long timeInMillisecond){
        this.checkBulletRange();
        for (int i = 0; i < bulletsFired.size(); i++){
            bulletsFired.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    private void destroyShip() {
        this.ship = null;
        //TODO: push ship explosion event
    }

    private void destroyThisAsteroid(int asteroidIndex) {
        asteroidBelt.remove(asteroidIndex);
        //TODO: push asteroid explosion event
    }

    private void shipCollision(){
        for (int i = 0; i < asteroidBelt.size(); i++) {
            if (ship.detectCollisions(asteroidBelt.get(i).hitbox)) {
                this.destroyShip();
                this.destroyThisAsteroid(i);
                break;
            }
        }
    }

    private void bulletsCollision(){
        Deque<Integer> bulletsToDelete = new ArrayDeque<Integer>();
        Deque<Integer> asteroidsToDelete = new ArrayDeque<Integer>();

        for (int i = 0; i < bulletsFired.size(); i++) {
            for (int j = 0; j < asteroidBelt.size(); j++) {
                if (bulletsFired.get(i).detectCollisions(asteroidBelt.get(j).hitbox)) {
                    bulletsToDelete.push(i);
                    asteroidsToDelete.push(j);
                }
            }
        }

        while(bulletsToDelete.size() > 0){
            int bulletIndex = bulletsToDelete.pop();
            bulletsFired.remove(bulletIndex);
        }

        while(asteroidsToDelete.size() > 0){
            int asteroidIndex = asteroidsToDelete.pop();
            destroyThisAsteroid(bulletIndex);
        }
    }

    protected void computeCollisions() {
        this.shipCollision();
        this.bulletsCollision();
    }

    protected void update(long timeInMillisecond) {
        this.ship.update(spaceWidth, spaceHeight, timeInMillisecond);
        this.updateAsteroidBelt(timeInMillisecond);
        if (bulletsFired.size() != 0) updateBullets(timeInMillisecond);
    }

    /**
     * Changes ship values with respect to user input
     *
     * @param accelerate
     * @param left
     * @param right
     */
    protected  void controlShip(boolean accelerate, boolean left, boolean right){
        //TODO: For Ship team
        //TODO: Accelerate (increment velocity)
        //TODO: Rotate Left (Set angular velocity to some negative constant)
        //TODO: Rotate Right (Set angular velocity to some positive constant)
    }

    protected void removeEntity() {

    }
}
