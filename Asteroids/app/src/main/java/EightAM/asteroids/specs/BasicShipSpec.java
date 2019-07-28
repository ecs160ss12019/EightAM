package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.R;

public class BasicShipSpec extends BaseShipSpec {
    public static String tag = "ship";
    public static String bitMapName = "ic_ship";
    public static int resID = R.drawable.ic_ship;
    public static Point dimensions = new Point(50, 50);
    public static float dbmRatio = 2f;
    public static String paintName = "ship";

    public BasicShipSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }
}
