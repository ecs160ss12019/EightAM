package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import java.util.Random;

/**
 * Goals:
 * - Spawn on space outskirts
 * - rocks generates rocks in a collision
 * - rocks float in space
 * - rocks collide with everything other object except itself
 * - speed: constant and dependent on size
 *
 * Interact with:
 * - Space GameObject
 */

class Asteroid extends GameObject {

    // ---------------Member statics ---------------

    static final int LARGE_RADIUS = 3;
    static final int MEDIUM_RADIUS = 2;
    static final int SMALL_RADIUS = 1;
    static final int MAXSPEED = 3; // TODO: subject to change

    // ---------------Member variables-------------

    // enum size used to denote three size types of asteroid rock
    enum Size { SMALL, MEDIUM, LARGE }
    private Size rockSize;
    private int SAFEDIST;  // TODO: Determine the safe distance from ship to spawn Asteroids

    // ---------------Member methods---------------

    /**
     * First onstructor constructs the asteroid rocks when there are no asteroids in
     * space, i.e. when there's a new game or when all asteroids in the field are
     * destroyed.
     *
     * Its velocity and position are random. However, its max possible velocity is
     * slower than that of smaller asteroids
     *
     * These asteroids only spawn when the ship/player is a certain distance away
     * from the spawn point.
     *
     * @param xTotalPix - total horizontal pixels
     * @param yTotalPix - total vertical pixels
     */
    protected Asteroid(int xTotalPix, int yTotalPix, float xShipPos, float yShipPos, Context context) {
        float xRand, yRand;
        float xDistFromShip, yDistFromShip, DistFromShip;
        Random rand = new Random();

        // might use later
        this.objectID = ObjectID.ASTEROID;

        // crate large rock only
        rockSize = Size.LARGE;

        // We only want to spawn asteroids we are a certain distance away from the ship
        // NOTE: May be inefficient, but more fair to the player.
        // Alternative: Only spawn asteroids close to the borders/outskirts of the
        // screen Alternative Implementation: new Random().nextInt((max-min+1))+min to
        // set bounds
        do {
            xRand = rand.nextInt(xTotalPix);
            yRand = rand.nextInt(yTotalPix);
            xDistFromShip = Math.abs(xRand - xShipPos);
            yDistFromShip = Math.abs(yRand - yShipPos);
            DistFromShip = (float) Math.sqrt(Math.pow(xDistFromShip, 2) + Math.pow(yDistFromShip, 2));
        } while (DistFromShip < SAFEDIST);

        this.spawn(xRand, yRand);
        this.setHitBox();

        this.velX = rand.nextInt(MAXSPEED / 4);
        this.velY = rand.nextInt(MAXSPEED / 4);

        // Prepare the bitmap
        // Load .png file in res/drawable
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_asteroid_large);
    }

    /**
     * Second constructor constructs the asteroid rocks when there an asteroid of a
     * Size of LARGE or MEDIUM gets destroyed.
     *
     * These asteroids spawn in the wake of the destruction of its parent i.e. the
     * parent's position.
     *
     * Its velocity is randomized in accordance to its size. That is, smaller
     * asteroids, higher possible max speed.
     *
     * @param currentX   - current horizontal position of the parent asteroid
     * @param currentY   - current vertical position of the parent asteroid
     * @param parentSize - Size of parent
     */
    protected Asteroid(int currentX, int currentY, Size parentSize, Context context) {
        this.objectID = ObjectID.ASTEROID;

        Random rand = new Random();
        if (parentSize == Size.LARGE) {
            rockSize = Size.MEDIUM;
            this.velX = rand.nextInt(MAXSPEED / 2);
            this.velY = rand.nextInt(MAXSPEED / 2);

            // Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_asteroid_large);
        } else {
            rockSize = Size.SMALL;
            this.velX = rand.nextInt(MAXSPEED);
            this.velY = rand.nextInt(MAXSPEED);

            /// Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_asteroid_large);
        }
        this.spawn(currentX, currentY);
        this.setHitBox();
    }

    /**
     * Sets and/or updates the position of the hitbox of the asteroid
     */
    // TODO: Set RecF dependent on size of screen and position of Asteroid
    protected void setHitBox() {
        hitbox = new RectF(this.posX, this.posY, this.posX, this.posY);
        switch (rockSize) {
            case LARGE:
                hitbox.left -= LARGE_RADIUS;
                hitbox.top -= LARGE_RADIUS;
                hitbox.right += LARGE_RADIUS;
                hitbox.bottom += LARGE_RADIUS;
                break;
            case MEDIUM:
                hitbox.left -= MEDIUM_RADIUS;
                hitbox.top -= MEDIUM_RADIUS;
                hitbox.right += MEDIUM_RADIUS;
                hitbox.bottom += MEDIUM_RADIUS;
                break;
            case SMALL:
                hitbox.left -= SMALL_RADIUS;
                hitbox.top -= SMALL_RADIUS;
                hitbox.right += SMALL_RADIUS;
                hitbox.bottom += SMALL_RADIUS;
                break;
        }
    }
}
