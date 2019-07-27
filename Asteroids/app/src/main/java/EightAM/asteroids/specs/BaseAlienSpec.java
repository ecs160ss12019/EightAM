package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseAlienSpec extends BaseSpec {
    public int pointValue;
    public int hitPoints;
    public float reloadTime;
    public float maxSpeed;

    public BaseAlienSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }


    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }
}

