package EightAM.asteroids;

import EightAM.asteroids.interfaces.Factory;
import EightAM.asteroids.specs.BaseSpec;

class BaseFactory implements Factory {
    static BaseFactory instance;

    private BaseFactory() {}

    static void init() {
        if (instance == null) instance = new BaseFactory();
        BaseAsteroidFactory.init();
        BaseAlienFactory.init();
    }

    static BaseFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public GameObject create(BaseSpec spec) {
        return null;
    }
}
