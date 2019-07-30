package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseAlienSpec extends BaseBitmapSpec {
    public int pointValue;
    public int hitPoints;

    public BaseAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, String bitMapName, int bitMapResourceID, float dimensionBitMapRatio,
            int pointValue, int hitPoints) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName,
                bitMapResourceID, dimensionBitMapRatio);
        this.pointValue = pointValue;
        this.hitPoints = hitPoints;
    }

}

