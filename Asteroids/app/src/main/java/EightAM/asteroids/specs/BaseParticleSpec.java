package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseParticleSpec extends BaseSpec {
    public float speed;
    public int duration;

    public BaseParticleSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }
}
