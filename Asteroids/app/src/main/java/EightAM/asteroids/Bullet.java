package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

class Bullet extends GameObject {

    ObjectID owner;
    private int bulletSpeed = 10;
    private float maxRange = 1000; // a cap on how far bullet can travel
    private float distanceTraveled;

    /**
     * Constructs projectile, i.e. shoots projectile in the orientation/angle
     * of the shooter.
     *
     * Alien shots are slower than the player's. This is to give the player
     * time to react.
     *
     * @param shooter - denotes if fired from player (true) or alien (false)
     */
    protected Bullet(Shooter shooter) {
        hitbox = new RectF(shooter.getPosX() - 3, shooter.getPosY() -3, shooter.getPosX() + 3, shooter.getPosY() + 3);
        this.objectID = ObjectID.BULLET;
        this.owner = shooter.getID();
        distanceTraveled = 0;
        if (this.owner == ObjectID.SHIP) {
            this.vel = new Velocity(bulletSpeed, shooter.getAngle());
        } else {
            this.vel = new Velocity(bulletSpeed / 2, shooter.getAngle());
        }
    }

    @Override
    void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        rotate();
        move(spaceWidth, spaceHeight, timeInMillisecond);
        distanceTraveled(timeInMillisecond);
    }

    /**
     * Calculates how far the bullet has traveled.
     * (to be used in a super class to determine when the bullet should die.)
     *
     * @param timeInMillisecond current time of the game in ms
     */
    private void distanceTraveled(long timeInMillisecond) {
        distanceTraveled += timeInMillisecond * this.vel.magnitude();
    }

    /**
     * Determines if the bullet should continue to persist.
     *
     * @return true if the bullet has exceeded its maximum range
     */
    protected boolean reachedMaxRange() {
        return distanceTraveled > maxRange;
    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        //Matrix matrix = new Matrix();
        //matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        //Log.d("in asteroid", "bitmap get width=" + bitmap.getWidth());
        //matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        //canvas.drawBitmap(bitmap, matrix, paint);
    }

}
