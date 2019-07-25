package EightAM.asteroids.interfaces;

import EightAM.asteroids.Asteroid;
import EightAM.asteroids.specs.BaseAsteroidSpec;

public interface AsteroidFactory {
    Asteroid createAsteroid(BaseAsteroidSpec spec);
}
