package EightAM.asteroids;

// float random = min + r.nextFloat() * (max - min);
//int randomNum = rand.nextInt((max - min) + 1) + min;

import static EightAM.asteroids.Constants.ALIEN_TARGET_ACCURACY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

import EightAM.asteroids.interfaces.Collideable;
import EightAM.asteroids.interfaces.Shootable;

abstract class Alien extends GameObject implements Shootable, Collideable {
    // ---------------Member statics --------------
    static final int MAXSPEED = 3;
    static Bitmap bitmap;
    int distanceTraveled, maxRange, turnDelay;
    int shotDelayCounter = 0;
    int shotDelay = 30;
    float shotOrientation = 0;
    boolean canShoot = false;

    // ---------------Member methods --------------

    protected Alien(int xTotalPix, int yTotalPix) {
        this.setMoveBehavior();
        this.setTimer();
        this.setShotDelay();

        // might use later
        this.objectID = ObjectID.ALIEN;
    }

    /**
     * Spawns alien either on left or right of the screen
     *
     * @param xTotalPix total pixels of screen (width)
     * @param yTotalPix total pixels of the screen (height)
     */
    protected void spawn(int xTotalPix, int yTotalPix) {
        int randX, randY;

        // set maxRange to be the width of the screen
        maxRange = xTotalPix;

        // spawn alien w/ random speed & direction
        // on either side of the screen
        Random rand = new Random();
        // randX will either be 0 or xTotalPix
        randX = rand.nextInt(((xTotalPix - 1) + 1) + 1) * rand.nextInt(2);
        randY = rand.nextInt(((yTotalPix - 1) + 1) + 1);

        this.setHitBox(randX, randY);
        this.paint = new Paint();
    }

    @Override
    protected void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        move(spaceWidth, spaceHeight, timeInMillisecond);
        distanceTraveled(timeInMillisecond);
        // decrement timers
        this.turnDelay--;
        this.shotDelay--;
        if (this.turnDelay <= 0) {
            this.turn();
            this.setTimer();
        }

        if (this.shotDelay <= 0) {
            this.canShoot = true;
            this.setShotDelay();
        }

    }

    protected void shoot(float targetX, float targetY) {
        this.canShoot = false;
        Random randX = new Random();
        Random randY = new Random();

        // TODO: change shooting behavior based on alien type
        float minX = targetX - ALIEN_TARGET_ACCURACY;
        float maxX = targetX + ALIEN_TARGET_ACCURACY;
        float minY = targetY - ALIEN_TARGET_ACCURACY;
        float maxY = targetY + ALIEN_TARGET_ACCURACY;

        float finalX = minX + randX.nextFloat() * (maxX - minX);
        float finalY = minY + randY.nextFloat() * (maxY - minY);

        float delX = finalX - getPosX();
        float delY = finalY - getPosY();

        this.shotOrientation = (float) Math.atan2(delY, delX);

    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);

        hitboxWidth = bitmap.getWidth() * 0.5f;
        hitboxHeight = bitmap.getHeight() * 0.5f;

        hitbox.left -= hitboxWidth / 2;
        hitbox.top -= hitboxHeight / 2;
        hitbox.right += hitboxWidth / 2;
        hitbox.bottom += hitboxHeight / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    /**
     * Collision detection method takes in the hitbox of approaching object, using intersection
     * method to check of collision
     *
     * @param approachingObject the hitbox of approaching object,
     * @return true for collision, otherwise false
     */
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return hitbox.intersect(approachingObject.hitbox);
    }
    /**
     * Determines if the alien should continue to persist.
     *
     * @return true if the alien has exceeded its maximum range
     */
    protected boolean reachedMaxRange() {
        return distanceTraveled > maxRange;
    }

    /**
     * Calculates how far the alien has traveled.
     * (to be used in a super class to determine when the alien should die.)
     *
     * @param timeInMillisecond current time of the game in ms
     */
    private void distanceTraveled(long timeInMillisecond) {
        // TODO: should probably base this off spawn location
        distanceTraveled += timeInMillisecond * this.vel.magnitude();
    }

    /**
     * Make the alien change its movement in the y direction.
     */
    protected void turn() {
        float newY = -1 * this.vel.y;
        this.vel.setY(newY);
    }

    protected abstract void setTimer();

    protected abstract void setShotDelay();

    protected abstract void setMoveBehavior();

    // getter functions
    public float getPosX() { return hitbox.centerX(); }

    public float getPosY() { return hitbox.centerY(); }

    public float getAngle() { return shotOrientation; }

}
