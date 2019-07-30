package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class SmallAlienSpec extends BaseAlienSpec {
    public static int pointValue = 100;
    public static int hitPoints = 1;
    public static int _reloadTime = 30;
    public static float maxSpeed = 3f;
    public static float initialAngle = (float) Math.PI * 0.25f;

    public static String tag = "big_alien";
    public static Point dimensions = new Point(20, 20);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation(initialAngle, 0);
    public static String paintName = "alien";

    public static String bitMapName = "big_alien";
    public static int resID = R.drawable.ic_small_alien;
    public static float dbmRatio = 2f;

    public static Pair<Integer, Integer> _turnDelayRange = new Pair<>(3000, 4000);
    public static Pair<Integer, Integer> _shotDelayRange = new Pair<>(2000, 3000);

    public int reloadTime;
    public Pair<Integer, Integer> turnDelayRange;
    public Pair<Integer, Integer> shotDelayRange;

    public SmallAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, String paintName,
            String bitMapName, int bitMapResourceID, float dimensionBitMapRatio, int pointValue,
            int hitPoints, int reloadTime,
            Pair<Integer, Integer> turnDelayRange,
            Pair<Integer, Integer> shotDelayRange) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName,
                bitMapResourceID, dimensionBitMapRatio, pointValue, hitPoints);
        this.reloadTime = reloadTime;
        this.turnDelayRange = turnDelayRange;
        this.shotDelayRange = shotDelayRange;
    }

    public SmallAlienSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName, resID, dbmRatio, pointValue, hitPoints, _reloadTime, _turnDelayRange,
                _shotDelayRange);
    }
}
