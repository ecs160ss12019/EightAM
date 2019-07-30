package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.annotation.ColorInt;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class SlowLongBulletSpec extends BaseBulletSpec {
    public static float speed = .5f;
    public static float maxDistance = 2000;
    public static float _bulletTrail = 100;
    public static int damage = 1;
    public static ObjectID owner = null;
    public static String tag = "bullet_slow_long";
    public static Point dimensions = new Point(20, 20);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(speed, 0, speed);
    public static Rotation initialRotation = new Rotation(0, 0);
    @ColorInt
    public static int paintColor = Color.MAGENTA;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public SlowLongBulletSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int damage, float maxDistance,
            ObjectID owner) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
    }

    public SlowLongBulletSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
    }
}
