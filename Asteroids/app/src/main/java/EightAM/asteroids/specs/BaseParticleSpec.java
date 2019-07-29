package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

public abstract class BaseParticleSpec extends BaseSpec {
    public Pair<Float, Float> speedRange;
    public long duration;

    public BaseParticleSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    public void setSpeedRange(Pair<Float, Float> speedRange) {
        this.speedRange = speedRange;
    }

    void setDuration(long duration) {
        this.duration = duration;
    }

}
