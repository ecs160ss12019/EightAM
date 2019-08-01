package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class SmallAlienBulletSpec extends BaseBulletSpec {
    public static float speed = 1f;
    public static int maxDistance = 1200;
    public static int damage = 1;
    public static ObjectID owner = null;
    public static String tag = "bullet_alien_small";
    public static Point dimensions = new Point(7, 7);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(speed, 0, speed);
    public static Rotation initialRotation = new Rotation(0, 0);

    public static int paintColor = Color.WHITE;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public SmallAlienBulletSpec(String tag, Point dimensions, Point initialPosition,
                                Velocity initialVelocity, Rotation initialRotation, int damage, float speed,
                                int maxDistance, ObjectID owner) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
    }

    public SmallAlienBulletSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage, speed,
                maxDistance, owner);
    }
}
