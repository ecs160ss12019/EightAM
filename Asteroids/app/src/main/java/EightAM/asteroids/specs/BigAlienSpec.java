package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BigAlienSpec extends BaseAlienSpec {
    public static int pointValue = 50;
    public static int hitPoints = 1;
    public static int _reloadTime = 30;
    public static float maxSpeed = 0.1f;
    public static float initialAngle = (float) Math.PI * 0.25f;
    public static String tag = "alien_big";
    public static Point dimensions = new Point(30, 30);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation(initialAngle, 0);

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public static int resID = R.drawable.ic_alien;
    public static float dbmRatio = 5f;

    public static Pair<Integer, Integer> _turnDelayRange = new Pair<>(4000, 6000);
    public static Pair<Integer, Integer> _shotDelayRange = new Pair<>(3000, 5000);

    public int reloadTime;
    public Pair<Integer, Integer> turnDelayRange;
    public Pair<Integer, Integer> shotDelayRange;

    public BigAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio,
            int pointValue, int hitPoints, int reloadTime,
            Pair<Integer, Integer> turnDelayRange,
            Pair<Integer, Integer> shotDelayRange) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, pointValue, hitPoints);
        this.reloadTime = reloadTime;
        this.turnDelayRange = turnDelayRange;
        this.shotDelayRange = shotDelayRange;
    }

    public BigAlienSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                pointValue, hitPoints, _reloadTime, _turnDelayRange, _shotDelayRange);
    }
}
