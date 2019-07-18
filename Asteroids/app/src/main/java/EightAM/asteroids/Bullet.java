package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class Bullet extends GameObject {

    ObjectID owner;
    private int bulletSpeed;
    private float maxRange; // a cap on how far bullet can travel
    private float distanceTraveled;

    /**
     * Constructs projectile, i.e. shoots projectile in the orientation/angle
     * of the shooter.
     *
     * Alien shots are slower than the player's. This is to give the player
     * time to react.
     *
     * @param shooter - denotes if fired from player (true) or alien (false)
     * @param x       - horizontal position of shooter
     * @param y       - vertical position of shooter
     * @param angle   - angle/ orientation of the shooter
     */
    protected Bullet(ObjectID shooter, float x, float y, float angle) {
        hitbox = new RectF(x, y, x, y);
        this.objectID = ObjectID.BULLET;
        this.owner = shooter;
        distanceTraveled = 0;
        if (this.owner == ObjectID.SHIP) {
            this.vel = new Velocity(bulletSpeed, angle);
        } else {
            this.vel = new Velocity(bulletSpeed / 2, angle);
        }
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

    }

}
