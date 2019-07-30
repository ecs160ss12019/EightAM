package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BasicParticleSpec extends BaseParticleSpec {
    public static Pair<Float, Float> speedRange = new Pair<>(.1f, .2f);
    public static long duration = 2000;

    public static String tag = "particle_basic";
    public static Point dimensions = new Point(10, 10);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, speedRange.second);
    public static Rotation initialRotation = new Rotation(0, 0);

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public static String bitMapName = null;
    public static int resID = 0;
    public static float dbmRatio = 3f;

    public BasicParticleSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, Pair<Float, Float> speedRange,
            long duration) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, speedRange,
                duration);
    }

    public BasicParticleSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, speedRange,
                duration);
    }

}
