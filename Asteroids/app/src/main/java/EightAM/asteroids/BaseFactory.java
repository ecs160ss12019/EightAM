package EightAM.asteroids;

import EightAM.asteroids.interfaces.Factory;
import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BaseAsteroidSpec;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseObjectSpec;
import EightAM.asteroids.specs.BaseParticleSpec;
import EightAM.asteroids.specs.BaseShipSpec;

class BaseFactory implements Factory {
    static BaseFactory instance;

    private BaseFactory() {}

    static void init() {
        if (instance == null) instance = new BaseFactory();
        BaseAsteroidFactory.init();
        BaseAlienFactory.init();
        BaseShipFactory.init();
    }

    static BaseFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    public GameObject create(BaseObjectSpec spec) {
        if (spec instanceof BaseAsteroidSpec) {
            return BaseAsteroidFactory.getInstance().createAsteroid((BaseAsteroidSpec) spec);
        }
        if (spec instanceof BaseAlienSpec) {
            return BaseAlienFactory.getInstance().createAlien((BaseAlienSpec) spec);
        }
        if (spec instanceof BaseShipSpec) {
            return BaseShipFactory.getInstance().createShip((BaseShipSpec) spec);
        }
        if (spec instanceof BaseBulletSpec) {
            return BaseBulletFactory.getInstance().createBullet((BaseBulletSpec) spec);
        }
        if (spec instanceof BaseParticleSpec) {
            return BaseParticleFactory.getInstance().createParticle((BaseParticleSpec) spec);
        }
        return null;
    }
}
