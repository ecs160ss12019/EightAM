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
        float randSpin = (float) Math.random() * (spec.spinSpeedRange.second - spec.spinSpeedRange.first) + spec.spinSpeedRange.first;
        if (Math.random() > 0.5) {
            randSpin = -randSpin;
        }
        ret.angularVel = randSpin;
        float randSpeed = (float) Math.random() * (spec.speedRange.second - spec.speedRange.first) + spec.speedRange.first;
        float randDirection = (float) (Math.random() * Math.PI * 2);
        ret.vel.accelerate(randSpeed, randDirection);
        return ret;
    }
}
