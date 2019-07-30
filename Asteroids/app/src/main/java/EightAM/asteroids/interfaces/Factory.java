package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.specs.BaseObjectSpec;

public interface Factory {
    GameObject create(BaseObjectSpec spec);
}
