package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BasicShipSpec extends BaseShipSpec {
    public static int hitPoints = 1;
    public static float maxSpeed = 0.8f;
    public static float rotationSpeed = 0.005f;
    public static float acceleration = 0.0035f;
    public static float deceleration = 0.9985f;
    public static int reloadTime = 500;
    public static int invincibilityDuration = 3000;
    public static int teleportDelay = 500;
    public static int teleportCooldown = 5000;

    public static String tag = "ship_basic";
    public static Point dimensions = new Point(32, 32);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation((float) Math.PI * 3f / 2, 0);
    //public static Rotation initialRotation = new Rotation(0, 0);

    public static int resID = R.drawable.ic_basic_ship;
    public static float dbmRatio = 2f;

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public static BaseWeaponSpec weaponSpec = new BasicShipWeaponSpec();

    public BasicShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int hitPoints, float maxSpeed, float rotationSpeed,
            float acceleration, float deceleration, int reloadTime, int invincibilityDuration,
            int teleportDelay, int teleportCooldown, BaseWeaponSpec weaponSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, hitPoints, maxSpeed, rotationSpeed, acceleration,
                deceleration, reloadTime, invincibilityDuration, teleportDelay, teleportCooldown,
                weaponSpec);
    }

    public BasicShipSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                hitPoints, maxSpeed, rotationSpeed, acceleration, deceleration, reloadTime,
                invincibilityDuration, teleportDelay, teleportCooldown, weaponSpec);
    }
}
