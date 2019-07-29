package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseShipSpec extends BaseSpec {
    public float maxSpeed = 1f;
    public float rotationSpeed = 0.01f;
    public float acceleration = 0.005f;
    public float deceleration = 0.9995f;
    public int reloadTime = 30;
    public int invincibilityDuration = 1800;
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();

    public BaseShipSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }
}
