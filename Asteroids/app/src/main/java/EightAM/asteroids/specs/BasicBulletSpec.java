package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class BasicBulletSpec extends BaseBulletSpec {
    public static float speed = 1.5f;
    public static float maxDistance = 400;
    public static float _bulletTrail = 100;
    public static int damage = 1;
    public static ObjectID owner = null;
    public static String tag = "basic_bullet";
    public static Point dimensions = new Point(6, 6);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(speed, 0, speed);
    public static Rotation initialRotation = new Rotation(0, 0);
    public static String paintName = "bullet";

    float bulletTrail;

    public BasicBulletSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, int damage, float speed, float maxDistance,
            ObjectID owner, float bulletTrail) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName, damage,
                maxDistance, owner);
        this.bulletTrail = bulletTrail;
    }

    public BasicBulletSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName, damage,
                speed, maxDistance, owner, _bulletTrail);
    }
}
