package EightAM.asteroids.specs;

import android.graphics.Point;

public class BasicBulletSpec extends BaseBulletSpec {
    public static String tag = "basic_bullet";
    public static String bitMapName = null;
    public static int resID = 0;
    public static Point dimensions = new Point(6, 6);
    public static float dbmRatio = 0;
    public static String paintName = "bullet";
    public static int damage = 1;
    public static int speed = 10;
    public static int maxDistance = 400;
    public static int _bulletTrail = 100;

    int bulletTrail;

    public BasicBulletSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setDamage(damage);
        setMaxDistance(maxDistance);
        setSpeed(speed);
        setBulletTrail(_bulletTrail);
    }

    void setBulletTrail(int bulletTrail) {
        this.bulletTrail = bulletTrail;
    }
}
