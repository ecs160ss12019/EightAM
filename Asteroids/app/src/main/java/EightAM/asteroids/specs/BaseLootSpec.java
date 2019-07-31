package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseLootSpec extends BaseBitmapSpec {
    public float randomAcceleration;
    public long durationMS;

    public BaseLootSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, float randomAcceleration, long durationMS) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio);
        this.randomAcceleration = randomAcceleration;
        this.durationMS = durationMS;
    }
}
