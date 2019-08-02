package EightAM.asteroids;

import android.graphics.Canvas;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.specs.BaseBulletSpec;

public class Bullet extends GameObject implements Collision {
    int damage;
    int maxDistance;
    // set by generator
    ObjectID owner;

    int distanceTraveled = 0;

    Bullet(BaseBulletSpec spec) {
        super(spec);
        // owner and ID are set by BulletGenerator
        this.damage = spec.damage;
        this.maxDistance = spec.maxDistance;
    }

    Bullet(Bullet bullet) {
        super(bullet);
        this.damage = bullet.damage;
        this.maxDistance = bullet.maxDistance;
    }


    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        distanceTraveled(timeInMillisecond);
        if (reachedMaxRange()) destruct(null);
    }

    // COLLISION INTERFACE
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return hitbox.intersect(approachingObject.hitbox);
    }

    public void onCollide(GameObject approachingObject) {
        destruct(null);
    }

    @Override
    public boolean canCollide() {
        return true;
    }


    /**
     * Calculates how far the bullet has traveled.
     * (to be used in a super class to determine when the bullet should die.)
     *
     * @param timeInMillisecond current time of the game in ms
     */
    private void distanceTraveled(long timeInMillisecond) {
        distanceTraveled++;
    }

    /**
     * Determines if the bullet should continue to persist.
     *
     * @return true if the bullet has exceeded its maximum range
     */
    protected boolean reachedMaxRange() {
        return distanceTraveled >= maxDistance;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.hitbox, paint);
    }

    public void setOwner(ObjectID owner) {
        this.owner = owner;
        this.id = ObjectID.getNewID(owner.getFaction());
    }

    @Override
    public Object makeCopy() {
        return new Bullet(this);
    }

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        eventHandler.onDestruct(this);
    }

}
