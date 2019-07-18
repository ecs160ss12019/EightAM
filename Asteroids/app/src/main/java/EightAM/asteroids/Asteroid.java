package EightAM.asteroids;

import static EightAM.asteroids.Constants.ASTEROID_MAXANGLE;
import static EightAM.asteroids.Constants.ASTEROID_MAXSPEED;
import static EightAM.asteroids.Constants.ASTEROID_MEDIUM_RADIUS;
import static EightAM.asteroids.Constants.ASTEROID_SMALL_RADIUS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
    static Bitmap bitmap;

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
    protected Asteroid(GameModel gameModel, int xTotalPix, int yTotalPix, float xShipPos, float yShipPos, Context context) {
        model = gameModel;
        float xRand, yRand;
        float xDistFromShip, yDistFromShip, DistFromShip;
        Random rand = new Random();

        // might use later
        this.objectID = ObjectID.ASTEROID;

        // create large rock only
        rockSize = Size.LARGE;

        // Prepare the bitmap
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
        bitmapWidth = bitmap.getWidth() * 0.5f;
        bitmapHeight = bitmap.getHeight() * 0.5f;


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

        float speed = rand.nextFloat() * ASTEROID_MAXSPEED;
        float direction = Float.MIN_VALUE + rand.nextFloat() * (float) (ASTEROID_MAXANGLE - Float.MIN_VALUE);
        this.vel = new Velocity(speed, direction);
        this.setHitBox(xRand, yRand);
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
        float speed = Float.MIN_VALUE + rand.nextFloat() * ((ASTEROID_MAXSPEED / 4) - Float.MIN_VALUE);

        if (parentSize == Size.LARGE) {
            rockSize = Size.MEDIUM;
            speed /= 2;

            // Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
        } else {
            rockSize = Size.SMALL;
            speed /= 4;

            /// Prepare the bitmap
            // Load .png file in res/drawable
            // TODO: this asteroid figure subject to change
            bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
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
                hitbox.left -= bitmapWidth / 2;
                hitbox.top -= bitmapHeight / 2;
                hitbox.right += bitmapWidth / 2;
                hitbox.bottom += bitmapHeight / 2;
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
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        //Log.d("in asteroid", "bitmap get width=" + bitmap.getWidth());
        matrix.postTranslate(hitbox.left - (bitmapWidth * 0.5f), hitbox.top - (bitmapHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }


    // enum size used to denote three size types of asteroid rock
    enum Size {
        SMALL, MEDIUM, LARGE
    }
}
