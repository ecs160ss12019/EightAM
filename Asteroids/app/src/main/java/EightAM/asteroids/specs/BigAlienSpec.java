package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.R;

public class BigAlienSpec extends BaseAlienSpec {
    public static String tag = "big_alien";
    public static String bitMapName = "big_alien";
    public static int resID = R.drawable.ic_alien;
    public static Point dimensions = new Point(20, 20);
    public static float dbmRatio = 2f;
    public static String paintName = "alien";
    public static int pointValue = 10;
    public static int hitPoints = 1;
    public static float reloadTime = 30;

    public BigAlienSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
        setHitPoints(hitPoints);
        setPointValue(pointValue);
        setReloadTime(reloadTime);
    }

}
