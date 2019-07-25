package EightAM.asteroids.interfaces;

import EightAM.asteroids.Alien;
import EightAM.asteroids.specs.BaseAlienSpec;

public interface AlienFactory {
    Alien createAlien(BaseAlienSpec spec);
}
