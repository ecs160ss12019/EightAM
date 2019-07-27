package EightAM.asteroids.specs;

import android.graphics.Point;

public class ParticleSpec extends BaseSpec {
    public static String tag = "particle";
    public static String bitMapName = null;
    public static int resID = 0;
    public static Point dimensions = new Point(5,5);
    public static float dbmRatio = 0;
    public static String paintName = "particle";
    public static int speed = 10;
    public static int maxDistance = 5;
    public static int duration = 10;

    public ParticleSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setSpeed(speed);
        setMaxDistance(maxDistance);
        setDuration(duration);
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }

    void setDuration(int duration) { this.duration = duration; }
}
