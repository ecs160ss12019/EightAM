package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class RandomLootSpec extends BaseLootSpec {
    public static float randomAcceleration = 0.0002f;
    public static long durationMS = 10000;
    public static float maxSpeed = 0.8f;

    public static String tag = "powerup_random";
    public static Point dimensions = new Point(100, 100);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation((float) Math.PI * 3f / 2, 0);

    public static int resID = R.drawable.ic_lootbox;
    public static float dbmRatio = .9f;

    public static int paintColor = Color.WHITE;
    public static Paint.Style paintStyle = Paint.Style.FILL;
    public static RandomPowerupSpec _randomPowerupSpec = new RandomPowerupSpec();

    public RandomPowerupSpec randomPowerupSpec;

    public RandomLootSpec(String tag, Point dimensions,
            Point initialPosition, Velocity initialVelocity,
            Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, float randomAcceleration, long durationMS,
            RandomPowerupSpec randomPowerupSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, randomAcceleration, durationMS);
        this.randomPowerupSpec = randomPowerupSpec;
    }

    public RandomLootSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                randomAcceleration, durationMS, _randomPowerupSpec);
    }
}
