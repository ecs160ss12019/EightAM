package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseParticleSpec extends BaseSpec {
    public static int speed;
    public static int duration;

    public BaseParticleSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    void setDuration(int duration) { this.duration = duration; }
}
