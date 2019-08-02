package EightAM.asteroids.specs;

import android.graphics.Point;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseShipSpec extends BaseBitmapSpec implements AudioSpec {
    public int hitPoints;
    public float maxSpeed;
    public float rotationSpeed;
    public float acceleration;
    public float deceleration;
    public int invincibilityDuration;
    public BaseWeaponSpec weaponSpec;

    // sound ID
    public int explosion = R.raw.ship_explosion;

    public BaseShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int hitPoints, float maxSpeed, float rotationSpeed,
            float acceleration, float deceleration, int invincibilityDuration,
            BaseWeaponSpec weaponSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation,
                bitMapResourceID, dimensionBitMapRatio);
        this.hitPoints = hitPoints;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.invincibilityDuration = invincibilityDuration;
        this.weaponSpec = weaponSpec;
    }

    @Override
    public Collection<Integer> getResIDs() {
        return Collections.singleton(explosion);
    }
}
