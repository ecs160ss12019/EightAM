package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseShipSpec extends BaseBitmapSpec {
    public int hitPoints;
    public float maxSpeed;
    public float rotationSpeed;
    public float acceleration;
    public float deceleration;
    public int reloadTime;
    public int invincibilityDuration;
    public BaseBulletSpec bulletSpec;

    public BaseShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, String paintName, String bitMapName,
            int bitMapResourceID, float dimensionBitMapRatio, int hitPoints, float maxSpeed,
            float rotationSpeed, float acceleration, float deceleration, int reloadTime,
            int invincibilityDuration, BaseBulletSpec bulletSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName,
                bitMapResourceID, dimensionBitMapRatio);
        this.hitPoints = hitPoints;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.reloadTime = reloadTime;
        this.invincibilityDuration = invincibilityDuration;
        this.bulletSpec = bulletSpec;
    }
}
