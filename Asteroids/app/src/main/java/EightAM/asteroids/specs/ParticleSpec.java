package EightAM.asteroids.specs;

import android.graphics.Point;

public class ParticleSpec extends BaseParticleSpec {
    public static String tag = "particle";
    public static String bitMapName = null;
    public static int resID = 0;
    public static Point dimensions = new Point(50,50);
    public static float dbmRatio = 3f;
    public static String paintName = "particle";
    public static float Speed = .2f;
    public static int duration = 10;

    public ParticleSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeed(Speed);
        setDuration(duration);
    }

}
