package EightAM.asteroids.interfaces;

import EightAM.asteroids.AbstractAlien;
import EightAM.asteroids.specs.BaseAlienSpec;

public interface AlienFactory {
    AbstractAlien createAlien(BaseAlienSpec spec);
}
