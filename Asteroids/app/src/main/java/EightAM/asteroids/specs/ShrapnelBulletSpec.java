package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class ShrapnelBulletSpec extends BaseBulletSpec {
    public static float speed = .5f;
    public static int maxDistance = 600;
    public static int damage = 1;
    public static ObjectID owner = null;
    public static String tag = "bullet_shrapnel";
    public static Point dimensions = new Point(10, 10);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(speed, 0, speed);
    public static Rotation initialRotation = new Rotation(0, 0);

    public static int paintColor = Color.rgb(255, 59, 160);
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public ShrapnelBulletSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int damage, int maxDistance,
            ObjectID owner) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
    }

    public ShrapnelBulletSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, damage,
                maxDistance, owner);
    }
}
