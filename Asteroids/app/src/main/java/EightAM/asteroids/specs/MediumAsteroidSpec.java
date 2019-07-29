package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;

public class MediumAsteroidSpec extends BaseAsteroidSpec {
    public static String tag = "asteroid_medium";
    public static String bitMapName = "asteroid_medium";
    public static int resID = R.drawable.asteroid_medium;
    public static Point dimensions = new Point(15, 15);
    public static float dbmRatio = 2f;
    public static String paintName = "asteroid";
    public static Pair<Float, Float> speed = new Pair<>(0.02f, 0.2f);
    public static Pair<Float, Float> spin = new Pair<>(0f, 0.01f);
    public static BaseAsteroidSpec breaksInto = new SmallAsteroidSpec();
    public static int pointValue = 15;
    public static int hitPoints = 1;

    public MediumAsteroidSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeedRange(speed);
        setSpinSpeedRange(spin);
        setBreaksInto(breaksInto);
        setPointValue(pointValue);
        setHitPoints(hitPoints);
    }
}
