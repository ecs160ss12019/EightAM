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
    private int SAFEDIST; //TODO: Determine the safe distance from ship to spawn Asteroids

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

    protected Rocks(int xTotalPix, int yTotalPix, double xShipPos, double yShipPos) {
        this.objectID = ObjectID.ASTEROID;
        rockSize = Size.LARGE;

        double xRand, yRand;
        double xDistFromShip, yDistFromShip, DistFromShip;
        Random rand = new Random();

        /*
         * We only want to spawn asteroids we are a certain distance away from the ship
         * NOTE: May be inefficient, but more fair to the player
         *
         * Alternative:
         * Only spawn asteroids close to the borders/outskirts of the screen
         * Alternative Implementation:
         * new Random().nextInt((max-min+1))+min to set bounds
         */
        do {
            xRand = (double) rand.nextInt(xTotalPix);
            yRand = (double) rand.nextInt(yTotalPix);
            xDistFromShip = Math.abs(xRand - xShipPos);
            yDistFromShip = Math.abs(yRand - yShipPos);
            DistFromShip = Math.sqrt(Math.pow(xDistFromShip, 2) + Math.pow(yDistFromShip, 2));
        } while(DistFromShip > SAFEDIST);

        this.spawn(xRand, yRand);
        this.setHitBox();

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
        this.objectID = ObjectID.ASTEROID;

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

        this.spawn(currentX, currentY);
        this.setHitBox();
    }

    /**
     * Sets and/or updates the position of the hit box of the asteroid
     *
     */
    //TODO: Set RecF dependent on size of screen and position of Asteroid
    protected void setHitBox() {
        switch (rockSize){
            case LARGE:

                break;
            case MEDIUM:

                break;
            case SMALL:

                break;
        }
    }

    protected void draw(){
        //TODO: Draw on canvas dependent on rockSize
        switch (rockSize){
            case LARGE:

                break;
            case MEDIUM:

                break;
            case SMALL:

                break;
        }
    }

    protected void collision() {

    }

    protected void update() {

    }
}
