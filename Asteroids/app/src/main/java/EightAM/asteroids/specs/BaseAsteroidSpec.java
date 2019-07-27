package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

public abstract class BaseAsteroidSpec extends BaseSpec {
    public Pair<Float, Float> speedRange;
    public Pair<Float, Float> spinSpeedRange;
    public BaseAsteroidSpec breaksInto;
    public int breakCount;
    public int pointValue = 10;
    public int hitPoints = 1;

    public BaseAsteroidSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    void setBreakCount(int breakCount) { this.breakCount = breakCount;}

    void setBreaksInto(BaseAsteroidSpec breaksInto) { this.breaksInto = breaksInto;}

    void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    void setSpeedRange(Pair<Float, Float> speedRange) {
        this.speedRange = speedRange;
    }

    void setSpinSpeedRange(Pair<Float, Float> spinSpeedRange) {
        this.spinSpeedRange = spinSpeedRange;
    }

    void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
