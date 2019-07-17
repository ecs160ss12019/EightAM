package EightAM.asteroids;

import static EightAM.asteroids.Constants.ASTEROID_LARGE_RADIUS;
import static EightAM.asteroids.Constants.ASTEROID_MAXANGLE;
import static EightAM.asteroids.Constants.ASTEROID_MAXSPEED;
import static EightAM.asteroids.Constants.ASTEROID_MEDIUM_RADIUS;
import static EightAM.asteroids.Constants.ASTEROID_SMALL_RADIUS;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    // ---------------Member variables-------------
    private Size rockSize;
    private int SAFEDIST;  // TODO: Determine the safe distance from ship to spawn Asteroids

    /**
     * First constructor constructs the asteroid rocks when there are no asteroids in
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
     * @param xShipPos  - Ship horizontal position
     * @param yShipPos  - Ship vertical position
     * @param context   - Context for setting bitmap
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
            xRand = rand.nextInt(((xTotalPix - 1) + 1) + 1);
            yRand = rand.nextInt(((yTotalPix - 1) + 1) + 1);
            xDistFromShip = Math.abs(xRand - xShipPos);
            yDistFromShip = Math.abs(yRand - yShipPos);
            DistFromShip = (float) Math.sqrt(Math.pow(xDistFromShip, 2) + Math.pow(yDistFromShip, 2));
        } while (DistFromShip < SAFEDIST);

        float speed = rand.nextInt(((ASTEROID_MAXSPEED / 4 - 1) + 1) + 1);
        float direction = Float.MIN_VALUE + rand.nextFloat() * (float) (ASTEROID_MAXANGLE - Float.MIN_VALUE);

        this.vel = new Velocity(speed, direction);
        this.setHitBox(xRand, yRand);

        // Prepare the bitmap
        // Load .png file in res/drawable
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_asteroid_large);
    }

    // ---------------Member methods---------------

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
     * @param context    - Context for setting bitmap
     */
    protected Asteroid(int currentX, int currentY, Size parentSize, Context context) {
        this.objectID = ObjectID.ASTEROID;

        Random rand = new Random();
        float direction = 1 + rand.nextFloat() * (float) (ASTEROID_MAXANGLE - 1);
        float speed = rand.nextInt(ASTEROID_MAXSPEED);

        if (parentSize == Size.LARGE) {
            rockSize = Size.MEDIUM;
            speed /= 2;

            // Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            this.bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
        } else {
            rockSize = Size.SMALL;
            speed /= 4;

            /// Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            this.bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
        }
        this.vel = new Velocity(speed, direction);
        this.setHitBox(currentX, currentY);
    }

    /**
     * Sets and/or updates the position of the hitbox of the asteroid
     */
    // TODO: Set RecF dependent on size of screen and position of Asteroid
    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);
        switch (rockSize) {
            case LARGE:
                hitbox.left -= ASTEROID_LARGE_RADIUS;
                hitbox.top -= ASTEROID_LARGE_RADIUS;
                hitbox.right += ASTEROID_LARGE_RADIUS;
                hitbox.bottom += ASTEROID_LARGE_RADIUS;
                break;
            case MEDIUM:
                hitbox.left -= ASTEROID_MEDIUM_RADIUS;
                hitbox.top -= ASTEROID_MEDIUM_RADIUS;
                hitbox.right += ASTEROID_MEDIUM_RADIUS;
                hitbox.bottom += ASTEROID_MEDIUM_RADIUS;
                break;
            case SMALL:
                hitbox.left -= ASTEROID_SMALL_RADIUS;
                hitbox.top -= ASTEROID_SMALL_RADIUS;
                hitbox.right += ASTEROID_SMALL_RADIUS;
                hitbox.bottom += ASTEROID_SMALL_RADIUS;
                break;
        }
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {

    }


    // enum size used to denote three size types of asteroid rock
    enum Size {
        SMALL, MEDIUM, LARGE
    }
}
