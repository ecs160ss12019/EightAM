package EightAM.asteroids.specs;

import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public abstract class BaseParticleSpec extends BaseObjectSpec {
    public Pair<Float, Float> speedRange;
    public long duration;

    public BaseParticleSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation,
            String paintName, Pair<Float, Float> speedRange, long duration) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, paintName);
        this.speedRange = speedRange;
        this.duration = duration;
    }

}
