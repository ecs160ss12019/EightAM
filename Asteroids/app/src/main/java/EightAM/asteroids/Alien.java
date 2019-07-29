package EightAM.asteroids;

// float random = min + r.nextFloat() * (max - min);
//int randomNum = rand.nextInt((max - min) + 1) + min;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;

import java.util.Random;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;

public abstract class Alien extends GameObject implements Destructable, Collision {
    // --------------- Member variables --------------
    Bitmap bitmap;
    int distanceTraveled;
    int maxRange;
    float shotAngle = 0;
    DestructListener destructListener;
    private boolean debug = true;

    // movement
    Pair<Integer, Integer> turnDelayRange;
    int turnDelay;

    // shooting
    Pair<Integer, Integer> shotDelayRange;
    int shotDelay;
    boolean canShoot = false;

    // ---------------Member methods --------------
    /**
     * Sets move behavior, turn timer, and shot delay.
     */
    protected void setUp() {
        setTurnDelay();
        setShotDelay();
    }

    // ------------ BEGIN MOVEMENT METHODS ------------ //

    /**
     * Set random max and min timer for Alien to change directions.
     * max and min are in frames.
     */
    protected void setTurnDelay() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.turnDelay = rand.nextInt((turnDelayRange.second - turnDelayRange.first) + 1)
                + turnDelayRange.first;

    }

    /**
     * Updates the turn timer and turns the alien when necessary.
     */
    protected void updateTurnTimer() {
        this.turnDelay--;
        if (debug) { Log.d("alien", Integer.toString(this.turnDelay)); }
        if (this.turnDelay <= 0) {
            this.turn();
            this.setTurnDelay();
        }
    }
    // ------------ END MOVEMENT METHODS ------------ //

    // ------------ BEGIN SHOOTING METHODS ------------ //
    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay() {
        Random rand = new Random();
        this.shotDelay = rand.nextInt((shotDelayRange.second - shotDelayRange.first) + 1)
                + shotDelayRange.first;
    }

    // ------------ END SHOOTING METHODS ------------ //
    @Override
    protected void update(Point spaceSize, long timeInMillisecond) {
        move(spaceSize, timeInMillisecond);
        updateDistance(timeInMillisecond);
        // timer stuff
        updateTurnTimer();
        //this.shotDelay--;
        // TODO: implement shooting
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
    private void updateDistance(long timeInMillisecond) {
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
    public ObjectID getID() { return this.id; }
    // ------------- END DESTRUCTABLE IMPLEMENTION ------------ //
}
