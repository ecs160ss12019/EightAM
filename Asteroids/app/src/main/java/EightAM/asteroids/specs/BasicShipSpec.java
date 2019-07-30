package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BasicShipSpec extends BaseShipSpec {
    public static int hitPoints = 1;
    public static float maxSpeed = 1f;
    public static float rotationSpeed = 0.005f;
    public static float acceleration = 0.0025f;
    public static float deceleration = 0.9995f;
    public static int reloadTime = 30;
    public static int invincibilityDuration = 1800;

    public static String tag = "ship";
    public static Point dimensions = new Point(16, 16);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation(0, 0);
    public static String paintName = "ship";

    public static String bitMapName = "ic_ship";
    public static int resID = R.drawable.ic_ship;
    public static float dbmRatio = 4f;


    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();

//    public BasicShipSpec() {
//        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
//        setBulletSpec(bulletSpec);
//    }

    public BasicShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, String paintName, String bitMapName,
            int bitMapResourceID, float dimensionBitMapRatio, int hitPoints, float maxSpeed,
            float rotationSpeed, float acceleration, float deceleration, int reloadTime,
            int invincibilityDuration, BaseBulletSpec bulletSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName, bitMapResourceID, dimensionBitMapRatio, hitPoints, maxSpeed,
                rotationSpeed, acceleration, deceleration, reloadTime, invincibilityDuration,
                bulletSpec);
    }

    public BasicShipSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName,
                bitMapName, resID, dbmRatio, hitPoints, maxSpeed, rotationSpeed, acceleration,
                deceleration,
                reloadTime, invincibilityDuration, bulletSpec);
    }
}
