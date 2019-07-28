package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.AsteroidFactory;
import EightAM.asteroids.specs.BaseAsteroidSpec;

class BaseAsteroidFactory implements AsteroidFactory {
    static BaseAsteroidFactory instance;
    private Map<BaseAsteroidSpec, Asteroid> prototypes;

    private BaseAsteroidFactory() {
        prototypes = new HashMap<>();
    }

    static void init() {
        if (instance == null) instance = new BaseAsteroidFactory();
    }

    static BaseAsteroidFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    private void setUniqueAttributes(Asteroid ret) {
        float randSpeed = GameRandom.randomFloat(ret.speedRange.first, ret.speedRange.second);
        float randAngle = GameRandom.randomFloat(Float.MIN_VALUE, (float) Math.PI * 2);
        float randSpin = GameRandom.randomFloat(ret.spinRange.first, ret.spinRange.second);
        float rand = GameRandom.randomFloat(0, 1);
        if (rand > 0.5) {
            randSpin = -randSpin;
        }
        ret.vel.accelerate(randSpeed, randAngle);
        ret.angularVel = randSpin;
    }

    @Override
    public Asteroid createAsteroid(BaseAsteroidSpec spec) {
        Asteroid ret;
        if (prototypes.containsKey(spec)) {
            ret = new Asteroid(prototypes.get(spec));
        } else {
            Asteroid asteroid = new Asteroid(spec);
            prototypes.put(spec, asteroid);
            ret = new Asteroid(asteroid);
        }
        setUniqueAttributes(ret);
        return ret;
    }
}
