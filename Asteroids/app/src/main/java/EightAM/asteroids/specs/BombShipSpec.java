package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BombShipSpec extends BaseShipSpec {
    public static int hitPoints = 1;
    public static float maxSpeed = 0.75f;
    public static float rotationSpeed = 0.005f;
    public static float acceleration = 0.0035f;
    public static float deceleration = 0.9985f;
    public static int invincibilityDuration = 3000;

    public static String tag = "ship_bomb";
    public static Point dimensions = new Point(32, 32);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation((float) Math.PI * 3f / 2, 0);

    public static int resID = R.drawable.ic_bomb_ship;
    public static float dbmRatio = 2f;

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;
    public static BaseWeaponSpec weaponSpec = new TeleportShipWeaponSpec();
    public static BaseWeaponSpec _specialWeapon = new SpiritBombSpec();
    public static int _bombChargeTime = 2500;

    public BaseWeaponSpec spiritBomb;
    public int bombChargeTime;

    public BombShipSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int hitPoints, float maxSpeed, float rotationSpeed,
            float acceleration, float deceleration, int invincibilityDuration,
            BaseWeaponSpec weaponSpec, BaseWeaponSpec spiritBomb, int bombChargeTime) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, hitPoints, maxSpeed, rotationSpeed, acceleration,
                deceleration,
                invincibilityDuration, weaponSpec);
        this.spiritBomb = spiritBomb;
        this.bombChargeTime = bombChargeTime;
    }

    public BombShipSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                hitPoints, maxSpeed, rotationSpeed, acceleration, deceleration,
                invincibilityDuration, weaponSpec, _specialWeapon, _bombChargeTime);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
