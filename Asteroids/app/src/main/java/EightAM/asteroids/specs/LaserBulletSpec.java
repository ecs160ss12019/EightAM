package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class LaserBulletSpec extends BaseBulletSpec {
    public static float speed = 5f;
    public static int maxDistance = 250;
    public static int _bulletTrail = 500;
    public static int damage = 5;
    public static ObjectID owner = null;
    public static String tag = "bullet_laser";
    public static Point dimensions = new Point(10, 10);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(speed, 0, speed);
    public static Rotation initialRotation = new Rotation(0, 0);

    public static int paintColor = Color.RED;
    public static Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;

    public int bulletTrail;

    public LaserBulletSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int damage, float speed,
            int maxDistance, ObjectID owner, int bulletTrail) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
        this.bulletTrail = bulletTrail;
    }

    public LaserBulletSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage, speed,
                maxDistance, owner, _bulletTrail);
    }
}
