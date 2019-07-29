package EightAM.asteroids.specs;

import android.graphics.Point;

public class ParticleSpec extends BaseParticleSpec {
    public static String tag = "particle";
    public static String bitMapName = null;
    public static int resID = 0;
    public static Point dimensions = new Point(10,10);
    public static float dbmRatio = 3f;
    public static String paintName = "particle";
    public static int speed = 10;
    public static int duration = 100;

    public ParticleSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeed(speed);
        setDuration(duration);
    }

}
