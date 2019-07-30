package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;


public abstract class BaseBulletSpec extends BaseObjectSpec {
    public int damage;
    public float maxDistance;
    public ObjectID owner;

    public BaseBulletSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, int damage, float maxDistance, ObjectID owner) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName);
        this.damage = damage;
        this.maxDistance = maxDistance;
        this.owner = owner;
    }
}
