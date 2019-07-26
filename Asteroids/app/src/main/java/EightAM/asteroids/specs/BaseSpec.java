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
    public Point initialPosition = new Point(0, 0);
    public float initialOrientation = 3f/2 * (float) Math.PI;

    public BaseSpec(String tag, String bitMapName, int resID, Point dimensions, float dbmRatio, String paintName) {
        this.tag = tag;
        this.bitMapName = bitMapName;
        this.bitMapResourceID = resID;
        this.dimensions = dimensions;
        this.dimensionBitMapRatio = dbmRatio;
        this.paintName = paintName;
    }

    void setInitialOrientation(float initialOrientation) {
        this.initialOrientation = initialOrientation;
    }

    void setInitialPosition(Point initialPosition) {
        this.initialPosition = initialPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseSpec)) return false;
        return this.tag.equals(((BaseSpec) obj).tag);
    }

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }
}
