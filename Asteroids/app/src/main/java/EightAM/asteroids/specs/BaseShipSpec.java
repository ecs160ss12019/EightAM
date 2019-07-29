package EightAM.asteroids.specs;

import android.graphics.Point;

public abstract class BaseShipSpec extends BaseSpec {
    public float maxSpeed = 1f;
    public float rotationSpeed = 0.01f;
    public float acceleration = 0.005f;
    public float deceleration = 0.9995f;
    public int reloadTime = 30;
    public int invincibilityDuration = 1800;
    public BaseBulletSpec bulletSpec;

    public BaseShipSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setInvincibilityDuration(int invincibilityDuration) {
        this.invincibilityDuration = invincibilityDuration;
    }

    public void setBulletSpec(BaseBulletSpec bulletSpec) {
        this.bulletSpec = bulletSpec;
    }
}
