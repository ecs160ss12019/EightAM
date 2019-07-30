package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class LargeAsteroidSpec extends BaseAsteroidSpec {
    public static Pair<Float, Float> speedRange = new Pair<>(0.01f, 0.1f);
    public static Pair<Float, Float> spinRange = new Pair<>(0f, 0.002f);
    public static BaseAsteroidSpec breaksInto = new MediumAsteroidSpec();
    public static int breakCount = 2;

    public static String tag = "asteroid_large";
    public static Point dimensions = new Point(100, 100);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, speedRange.second);
    public static Rotation initialRotation = new Rotation(0, 0);
    public static String paintName = "asteroid";

    public static String bitMapName = "asteroid_large";
    public static int resID = R.drawable.asteroid_large;
    public static float dbmRatio = 2f;

    public static int pointValue = 10;
    public static int hitPoints = 1;

    public LargeAsteroidSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, String bitMapName, int bitMapResourceID, float dimensionBitMapRatio,
            Pair<Float, Float> speedRange,
            Pair<Float, Float> spinSpeedRange, BaseAsteroidSpec breaksInto, int breakCount,
            int pointValue, int hitPoints) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName,
                bitMapResourceID, dimensionBitMapRatio, speedRange, spinSpeedRange, breaksInto,
                breakCount, pointValue, hitPoints);
    }

    public LargeAsteroidSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName, resID, dbmRatio,
                speedRange, spinRange, breaksInto, breakCount, pointValue, hitPoints);
//        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
//        setSpeedRange(speedRange);
//        setSpinSpeedRange(spinRange);
//        setBreaksInto(breaksInto);
//        setPointValue(pointValue);
//        setHitPoints(hitPoints);
    }
}
