package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

public class ParticleSpec extends BaseParticleSpec {
    public static String tag = "particle";
    public static String bitMapName = null;
    public static int resID = 0;
    public static Point dimensions = new Point(5,5);
    public static float dbmRatio = 3f;
    public static String paintName = "particle";
    public static float Speed = .1f;
    public static long duration = 1000;
    public static Pair<Float, Float> speedRange = new Pair<>(.1f, .2f);

    public ParticleSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeedRange(speedRange);
        setDuration(duration);
    }

}
