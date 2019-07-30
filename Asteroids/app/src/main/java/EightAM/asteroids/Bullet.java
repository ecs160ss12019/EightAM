package EightAM.asteroids;

import static EightAM.asteroids.Constants.BULLET_MAX_RANGE;

import android.graphics.Canvas;
import android.graphics.Point;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseBulletSpec;

public class Bullet extends GameObject implements Collision, Destructable {
    int damage;
    float maxDistance;
    // set by generator
    private ObjectID owner;

    private float distanceTraveled;
    private DestructListener destructListener;

    Bullet(BaseBulletSpec spec) {
        super(spec);
        this.damage = spec.damage;
        this.maxDistance = spec.maxDistance;
    }

    Bullet(Bullet bullet) {
        super(bullet);
        this.damage = bullet.damage;
        this.maxDistance = bullet.maxDistance;
    }


    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        super.update(spaceSize, timeInMillisecond);
        distanceTraveled(timeInMillisecond);
        if (reachedMaxRange()) destruct(null);
    }

    // COLLISION INTERFACE
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return hitbox.intersect(approachingObject.hitbox);
    }

    public void onCollide(GameObject approachingObject) {
        // TODO more stuff here
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
        distanceTraveled += timeInMillisecond * this.vel.magnitude();
    }

    /**
     * Determines if the bullet should continue to persist.
     *
     * @return true if the bullet has exceeded its maximum range
     */
    protected boolean reachedMaxRange() {
        return distanceTraveled > maxDistance;
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
    GameObject makeCopy() {
        return new Bullet(this);
    }

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }
}
