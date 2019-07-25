package EightAM.asteroids;

import EightAM.asteroids.interfaces.AlienFactory;
import EightAM.asteroids.specs.BaseAlienSpec;

class BaseAlienFactory implements AlienFactory {
    static BaseAlienFactory instance;

    private BaseAlienFactory() {}

    static void init() {
        if (instance == null) instance = new BaseAlienFactory();
    }

    static BaseAlienFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Alien createAlien(BaseAlienSpec spec) {
        return null;
    }
}
