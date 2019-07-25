package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;

public class SmallAsteroidSpec extends BaseAsteroidSpec {
    public static String tag = "asteroid_small";
    public static String bitMapName = "asteroid_small";
    public static int resID = R.drawable.asteroid_small;
    public static Point dimensions = new Point(10, 10);
    public static float dbmRatio = 2f;
    public static String paintName = "asteroid";
    public static Pair<Float, Float> speed = new Pair<>(0.5f, 1.0f);
    public static Pair<Float, Float> spin = new Pair<>(0f, 0.01f);
    public static BaseAsteroidSpec breaksInto = null;
    public static int pointValue = 15;

    public SmallAsteroidSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeedRange(speed);
        setSpinSpeedRange(spin);
        setBreaksInto(breaksInto);
        setPointValue(pointValue);
        setHitPoints(hitPoints);
    }
}
