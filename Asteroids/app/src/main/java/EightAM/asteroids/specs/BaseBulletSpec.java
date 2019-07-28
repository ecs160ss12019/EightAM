package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseBulletSpec extends BaseSpec {
    public int damage = 1;
    public float speed = 10;
    public float maxDistance = 100;

    public BaseBulletSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    void setDamage(int damage) {
        this.damage = damage;
    }

    void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    void setSpeed(float speed) {
        this.speed = speed;
    }
}
