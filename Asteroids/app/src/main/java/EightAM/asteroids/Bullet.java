package EightAM.asteroids;

import android.graphics.RectF;
import java.lang.Math;

class Bullet extends GameObject {

    private int bulletSpeed;
    ObjectID owner;
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
     * @param x - horizontal position of shooter
     * @param y - vertical position of shooter
     * @param orientation - angle/ orientation of the shooter
     */
    protected Bullet(ObjectID shooter, float x, float y, double orientation){
        hitbox = new RectF(x, y, x, y);
        this.objectID = ObjectID.PROJECTILE;
        this.owner = shooter;

        if (this.owner == ObjectID.SHIP){
            this.velX = bulletSpeed * (float)Math.cos(orientation);
            this.velY = bulletSpeed * (float)Math.sin(orientation);
        }
        else {
            this.velX = (bulletSpeed / 2) * (float)Math.cos(orientation);
            this.velY = (bulletSpeed / 2) * (float)Math.sin(orientation);
        }
    }

    /**
     * Calculates how far the bullet has traveled.
     * (to be used in a super class to determine when the bullet should die.)
     * @param timeInMillisecond current time of the game in ms
     * @return distance traveled
     */
    protected float calculateDistanceTraveled(long timeInMillisecond) {
        float velocity = (float)Math.sqrt(Math.pow(this.velX, 2) + Math.pow(this.velY, 2));
        return timeInMillisecond * velocity;

    }

    protected void setHitBox() {
        hitbox = new RectF(this.posX, this.posY, this.posX, this.posY);
    }
}
