package EightAM.asteroids.specs;

import android.graphics.Point;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseObjectSpec {
    public String tag;
    public Point dimensions;
    public Point initialPosition;
    public Velocity initialVelocity;
    public Rotation initialRotation;

    public BaseObjectSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation) {
        this.tag = tag;
        this.dimensions = dimensions;
        this.initialPosition = initialPosition;
        this.initialVelocity = initialVelocity;
        this.initialRotation = initialRotation;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseObjectSpec)) return false;
        return this.tag.equals(((BaseObjectSpec) obj).tag);
    }

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }
}
