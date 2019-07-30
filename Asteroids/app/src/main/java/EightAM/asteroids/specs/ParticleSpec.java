package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class ParticleSpec extends BaseParticleSpec {
    public static Pair<Float, Float> speedRange = new Pair<>(.1f, .2f);
    public static long duration = 2000;

    public static String tag = "particle";
    public static Point dimensions = new Point(10, 10);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, speedRange.second);
    public static Rotation initialRotation = new Rotation(0, 0);
    public static String paintName = "particle";

    public static String bitMapName = null;
    public static int resID = 0;
    public static float dbmRatio = 3f;

    public ParticleSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, Pair<Float, Float> speedRange, long duration) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                speedRange,
                duration);
    }

    public ParticleSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                speedRange, duration);
    }

}
