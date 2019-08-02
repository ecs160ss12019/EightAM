package EightAM.asteroids.specs;

import android.graphics.Point;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseAlienSpec extends BaseBitmapSpec implements AudioSpec {
    public int pointValue;
    public int hitPoints;
    public BaseWeaponSpec weaponSpec;

    public int explosionID;

    public BaseAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int pointValue, int hitPoints, BaseWeaponSpec weaponSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio);
        this.pointValue = pointValue;
        this.hitPoints = hitPoints;
        this.weaponSpec = weaponSpec;
    }

    public void setExplosionID(int explosionID) {
        this.explosionID = explosionID;
    }

    @Override
    public Collection<Integer> getResIDs() {
        return Collections.singleton(explosionID);
    }
}

