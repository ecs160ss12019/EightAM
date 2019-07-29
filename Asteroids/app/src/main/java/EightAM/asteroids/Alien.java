package EightAM.asteroids;

// float random = min + r.nextFloat() * (max - min);
//int randomNum = rand.nextInt((max - min) + 1) + min;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Random;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;

public abstract class Alien extends GameObject implements Destructable, Collision {
    // --------------- Member variables --------------
    Bitmap bitmap;
    int distanceTraveled;
    int maxRange;
    int shotDelayCounter = 0;
    float shotAngle = 0;
    boolean canShoot = false;
    DestructListener destructListener;

    // ---------------Member methods --------------

    @Override
    protected void update(Point spaceSize, long timeInMillisecond) {
        move(spaceSize, timeInMillisecond);
        distanceTraveled(timeInMillisecond);
        // set timers
    }

//    protected void shoot(float targetX, float targetY) {
//        this.canShoot = false;
//        Random randX = new Random();
//        Random randY = new Random();
//
//        // TODO: change shooting behavior based on alien type
//        float minX = targetX - ALIEN_TARGET_ACCURACY;
//        float maxX = targetX + ALIEN_TARGET_ACCURACY;
//        float minY = targetY - ALIEN_TARGET_ACCURACY;
//        float maxY = targetY + ALIEN_TARGET_ACCURACY;
//
//        float finalX = minX + randX.nextFloat() * (maxX - minX);
//        float finalY = minY + randY.nextFloat() * (maxY - minY);
//
//        float delX = finalX - getPosX();
//        float delY = finalY - getPosY();
//
//        this.shotAngle = (float) Math.atan2(delY, delX);
//
//    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
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

    public float getShotAngle() {
        return shotAngle;
    }

    // ------------ BEGIN COLLISION IMPLEMENTION ------------ //
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

    @Override
    public void onCollide(GameObject approachingObject){ destruct(); }

    @Override
    public boolean canCollide() {
        return true;
    }
    // ------------ END COLLISION IMPLEMENTION ------------ //

    // ------------ BEGIN DESTRUCTABLE IMPLEMENTION ------------ //
    public void destruct() {
        ((GameModel) destructListener).activeAliens--;
        destructListener.onDestruct(this);
    }

    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }

    // should be implemented by derived alien classes
    public abstract ObjectID getID();
    // ------------- END DESTRUCTABLE IMPLEMENTION ------------ //
}
