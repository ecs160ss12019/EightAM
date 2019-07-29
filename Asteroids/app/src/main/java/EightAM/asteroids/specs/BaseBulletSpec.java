package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.ObjectID;


public abstract class BaseBulletSpec extends BaseSpec {
    public int damage = 1;
    public float speed = 0.75f;
    public float maxDistance = 100;
    public ObjectID owner;

    public BaseBulletSpec(String tag, String bitMapName, int resID, Point dimensions,
            float dbmRatio, String paintName) {
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

    public void setOwner(ObjectID owner) {
        this.owner = owner;
    }
}
