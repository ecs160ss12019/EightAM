package EightAM.asteroids.specs;

import android.graphics.Point;

import androidx.annotation.DrawableRes;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseBitmapSpec extends BaseObjectSpec {
    @DrawableRes
    public int bitMapResourceID;
    public float dimensionBitMapRatio;

    public BaseBitmapSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation);
        this.bitMapResourceID = bitMapResourceID;
        this.dimensionBitMapRatio = dimensionBitMapRatio;
    }
}
