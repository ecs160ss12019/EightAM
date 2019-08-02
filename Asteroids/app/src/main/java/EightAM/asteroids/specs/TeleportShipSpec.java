package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class TeleportShipSpec extends BaseShipSpec implements AudioSpec {
    public static int hitPoints = 1;
    public static float maxSpeed = 1f;
    public static float rotationSpeed = 0.007f;
    public static float acceleration = 0.005f;
    public static float deceleration = 0.9995f;
    public static int invincibilityDuration = 3000;
    public static int _teleportDelay = 100;
    public static int _teleportCooldown = 3000;

    public static String tag = "ship_teleport";
    public static Point dimensions = new Point(32, 32);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation((float) Math.PI * 3f / 2, 0);
    //public static Rotation initialRotation = new Rotation(0, 0);

    public static int resID = R.drawable.ic_basic_ship;
    public static float dbmRatio = 2f;

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;
    public static BaseWeaponSpec weaponSpec = new TeleportShipWeaponSpec();
    public final int teleportDelay;
    public final int teleportCooldown;

    // sound resIDs
    public int teleport = R.raw.ship_teleport;

    public TeleportShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int hitPoints, float maxSpeed, float rotationSpeed,
            float acceleration, float deceleration, int invincibilityDuration,
            int teleportDelay, int teleportCooldown, BaseWeaponSpec weaponSpec) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, hitPoints, maxSpeed, rotationSpeed, acceleration,
                deceleration, invincibilityDuration, weaponSpec);
        this.teleportDelay = teleportDelay;
        this.teleportCooldown = teleportCooldown;
    }

    public TeleportShipSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                hitPoints, maxSpeed, rotationSpeed, acceleration, deceleration,
                invincibilityDuration, _teleportDelay, _teleportCooldown, weaponSpec);
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public Collection<Integer> getResIDs() {
        return Collections.unmodifiableList(Arrays.asList(explosion, teleport));
    }
}
