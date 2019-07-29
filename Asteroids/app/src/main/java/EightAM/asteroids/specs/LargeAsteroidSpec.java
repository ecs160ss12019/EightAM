package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;

public class LargeAsteroidSpec extends BaseAsteroidSpec {
    public static String tag = "asteroid_large";
    public static String bitMapName = "asteroid_large";
    public static int resID = R.drawable.asteroid_large;

    public static Point dimensions = new Point(40, 40);
    public static float dbmRatio = 5f;

    public static String paintName = "asteroid";
    public static Pair<Float, Float> speed = new Pair<>(0.01f, 0.1f);
    public static Pair<Float, Float> spin = new Pair<>(0f, 0.002f);
    public static BaseAsteroidSpec breaksInto = new MediumAsteroidSpec();
    public static int pointValue = 10;
    public static int hitPoints = 1;

    public LargeAsteroidSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeedRange(speed);
        setSpinSpeedRange(spin);
        setBreaksInto(breaksInto);
        setPointValue(pointValue);
        setHitPoints(hitPoints);
    }
}
