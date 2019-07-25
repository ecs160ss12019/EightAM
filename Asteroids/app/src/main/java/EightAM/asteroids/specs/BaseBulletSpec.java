package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseBulletSpec extends BaseSpec {
    int damage = 1;
    float speed = 10;
    float maxDistance = 100;

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
