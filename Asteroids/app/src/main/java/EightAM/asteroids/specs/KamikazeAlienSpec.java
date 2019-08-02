package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class KamikazeAlienSpec extends BaseAlienSpec {
    public static float maxSpeed = 0.50f;
    public static float initialAngle = (float) Math.PI * 0.25f;

    public static float _rotationSpeed = 0.005f;
    public static float _acceleration = 0.0035f;
    public static float _deceleration = 0.9985f;
    public static Pair<Float, Float> _trackingError = new Pair<>((float) Math.PI / 16f, 0f);
    public static float _explodeRadius = 100f;

    public static String tag = "alien_suicide";
    public static Point dimensions = new Point(65, 65);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation(initialAngle, 0);

    public static int paintColor = Color.RED;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public static int resID = R.drawable.ic_bomb_ship; // placeholder for Irene's suicide asset
    public static float dbmRatio = 2f;

    public static int pointValue = 50;
    public static int hitPoints = 1;
    public static BaseWeaponSpec weaponSpec = new BombWeaponSpec();

    public float rotationSpeed;
    public float acceleration;
    public float deceleration;
    public Pair<Float, Float> trackingError;
    public float explodeRadius;

    public static int explosion = R.raw.alien_explosion;

    public KamikazeAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int pointValue, int hitPoints,
            BaseWeaponSpec weaponSpec, float rotationSpeed, float acceleration, float deceleration,
            Pair<Float, Float> trackingError, float explodeRadius) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, pointValue, hitPoints, weaponSpec);
        this.rotationSpeed = rotationSpeed;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.trackingError = trackingError;
        this.explodeRadius = explodeRadius;
    }

    public KamikazeAlienSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID,
                dbmRatio, pointValue, hitPoints, weaponSpec, _rotationSpeed, _acceleration,
                _deceleration, _trackingError, _explodeRadius);
        setExplosionID(explosion);
    }
}
