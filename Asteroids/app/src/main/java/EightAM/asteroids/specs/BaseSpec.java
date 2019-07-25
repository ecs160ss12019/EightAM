package EightAM.asteroids.specs;

import android.graphics.Point;

import androidx.annotation.DrawableRes;

public abstract class BaseSpec {
    public String tag;
    public String bitMapName;
    @DrawableRes
    public int bitMapResourceID;
    public Point dimensions;
    public float dimensionBitMapRatio;
    public String paintName;

    public BaseSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        this.tag = tag;
        this.bitMapName = bitMapName;
        this.bitMapResourceID = resID;
        this.dimensions = dimensions;
        this.dimensionBitMapRatio = dbmRatio;
        this.paintName = paintName;
    }
}
