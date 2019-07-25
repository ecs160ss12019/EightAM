package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.specs.BaseSpec;

public interface Factory {
    GameObject create(BaseSpec spec);
}
