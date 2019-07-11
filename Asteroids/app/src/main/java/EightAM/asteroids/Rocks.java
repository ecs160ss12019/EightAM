package EightAM.asteroids;

import android.graphics.RectF;
import java.util.Random;

class Rocks extends GameObject{

    /*
    Goals:
    - Spawn on space outskirts
    - rocks generates rocks in a collision
    - rocks float in space
    - rocks collide with everything other object except itself
    - speed: constant and dependent on size

    Interact with:
    Space
    GameObject
     */

    /**
     * Enum to denote 3 size types of asteroid rock
     */
    enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    private Size rockSize;
    private int MAXSPEED; //TODO: Decide what MAXSPEED should be and set to static final
    
    /**
     * Sets and/or updates the position of the hit box of the asteroid
     *
     * @param x - horizontal position of asteroid
     * @param y - vertical position of asteroid
     */
    //TODO: Set RecF dependent on size of screen and position of Asteroid
    private void setHitBox(double x, double y) {
        if (rockSize == Size.LARGE) {

        }
        else if (rockSize == Size.MEDIUM) {

        }
        else {

        }
    }


    /**
     * This constructor constructs the asteroid rocks when there are no
     * asteroids in space, i.e. when there's a new game or when all
     * asteroids in the field are destroyed.
     *
     * Its velocity and position are random. However, its max possible
     * velocity is slower than that of smaller asteroids
     *
     * These asteroids only spawn when the ship/player is a certain
     * distance away from the spawn point.
     *
     * @param xTotalPix - total horizontal pixels
     * @param yTotalPix - total vertical pixels
     */

    protected Rocks(int xTotalPix, int yTotalPix) {
        rockSize = Size.LARGE;
        Random rand = new Random();
        double xRand, yRand;
        /*
         * We only want to spawn asteroids we are a certain distance away from the ship
         * NOTE: May be inefficient, but more fair to the player
         *
         * Alternative:
         * Only spawn asteroids close to the borders/outskirts of the screen
         * Alternative Implementation:
         * new Random().nextInt((max-min+1))+min to set bounds
         *
         */
        //TODO: Find a way to get "a certain distance away from the ship"
        //do {
            xRand = (double) rand.nextInt(xTotalPix);
            yRand = (double) rand.nextInt(yTotalPix);
        //} while(a certain distance away from the ship);

        this.setHitBox(xRand, yRand);
        this.spawn(xRand, yRand);

        this.velX = (double) rand.nextInt(MAXSPEED/4);
        this.velY = (double) rand.nextInt(MAXSPEED/4);
    }

    /**
     * This constructor constructs the asteroid rocks when there an
     * asteroid of a Size of LARGE or MEDIUM gets destroyed.
     *
     * These asteroids spawn in the wake of the destruction of its parent
     * i.e. the parent's position.
     *
     * Its velocity is randomized in accordance to its size. That is,
     * smaller asteroids, higher possible max speed.
     *
     * @param currentX - current horizontal position of the parent asteroid
     * @param currentY - current vertical position of the parent asteroid
     * @param parentSize - Size of parent
     */
    protected Rocks(int currentX, int currentY, Size parentSize) {
        Random rand = new Random();
        if (parentSize == Size.LARGE) {
            rockSize = Size.MEDIUM;
            this.velX = (double) rand.nextInt(MAXSPEED/2);
            this.velY = (double) rand.nextInt(MAXSPEED/2);
        }
        else {
            rockSize = Size.SMALL;
            this.velX = (double) rand.nextInt(MAXSPEED);
            this.velY = (double) rand.nextInt(MAXSPEED);
        }
        this.setHitBox(currentX, currentY);
        this.spawn(currentX, currentY);
    }

    protected void draw(){
        //TODO: Draw on canvas dependent on rockSize
        if (rockSize == Size.LARGE) {

        }
        else if (rockSize == Size.MEDIUM) {

        }
        else {

        }
    }

    protected void collision() {

    }
}
