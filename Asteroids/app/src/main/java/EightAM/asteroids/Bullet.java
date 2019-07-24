package EightAM.asteroids;

import static EightAM.asteroids.Constants.BULLET_MAX_RANGE;
import static EightAM.asteroids.Constants.BULLET_SPEED;

import android.graphics.Canvas;
import android.graphics.RectF;

class Bullet extends GameObject {

    ObjectID owner;
    private float distanceTraveled;
    private float shooterAngle;

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
        this.shooterAngle = shooter.getAngle();
        hitbox = new RectF(shooter.getPosX() - 3, shooter.getPosY() -3, shooter.getPosX() + 3, shooter.getPosY() + 3);
        this.objectID = ObjectID.BULLET;
        this.owner = shooter.getID();
        distanceTraveled = 0;

        if (this.owner == ObjectID.SHIP) {
            this.vel = new Velocity(BULLET_SPEED, shooter.getAngle());
        } else {
            this.vel = new Velocity(BULLET_SPEED / 2, shooter.getAngle());
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
        return distanceTraveled > BULLET_MAX_RANGE;
    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.hitbox, paint);

        // some math stuff
        float dX = (float) Math.cos(shooterAngle) * 100;
        float dY = (float) Math.sin(shooterAngle) * 100;

        float endX = hitbox.centerX() - dX;
        float endY = hitbox.centerY() - dY;

        canvas.drawLine(hitbox.centerX(), hitbox.centerY(), endX, endY, paint);
        //canvas.drawBitmap(bitmap, matrix, paint);
    }

}
