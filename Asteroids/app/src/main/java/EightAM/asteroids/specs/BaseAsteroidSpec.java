package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseAsteroidSpec extends BaseBitmapSpec {
    public Pair<Float, Float> speedRange;
    public Pair<Float, Float> spinSpeedRange;
    public BaseAsteroidSpec breaksInto;
    public int breakCount;
    public int pointValue = 10;
    public int hitPoints = 1;

    public BaseAsteroidSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, String bitMapName, int bitMapResourceID, float dimensionBitMapRatio,
            Pair<Float, Float> speedRange,
            Pair<Float, Float> spinSpeedRange, BaseAsteroidSpec breaksInto, int breakCount,
            int pointValue, int hitPoints) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName,
                bitMapResourceID, dimensionBitMapRatio);
        this.speedRange = speedRange;
        this.spinSpeedRange = spinSpeedRange;
        this.breaksInto = breaksInto;
        this.breakCount = breakCount;
        this.pointValue = pointValue;
        this.hitPoints = hitPoints;
    }

}