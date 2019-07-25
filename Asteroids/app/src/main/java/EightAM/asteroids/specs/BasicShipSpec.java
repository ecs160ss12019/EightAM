package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.R;

public class BasicShipSpec extends BaseSpec {
    public static String tag = "ship";
    public static String bitMapName = "ic_ship";
    public static int resID = R.drawable.ic_ship;
    public static Point dimensions = new Point(16, 16);
    public static float dbmRatio = 2f;
    public static String paintName = "default";
    public float maxSpeed = 2f;
    public float rotationSpeed = 0.01f;
    public float acceleration = 0.005f;
    public float deceleration = 0.9995f;
    public float reloadTime = 30f;

    public BasicShipSpec() {
        super(tag, bitMapName, resID, dimensions, dbmRatio, paintName);
    }
}
