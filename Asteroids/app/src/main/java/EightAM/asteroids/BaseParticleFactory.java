package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.ParticleFactory;
import EightAM.asteroids.specs.BaseParticleSpec;

public class BaseParticleFactory implements ParticleFactory {
    static BaseParticleFactory instance;
    private Map<BaseParticleSpec, Particle> prototypes;

    private BaseParticleFactory() {
        prototypes = new HashMap<>();
    }

    static void init() { if (instance == null) instance = new BaseParticleFactory(); }
    static BaseParticleFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Particle createParticle(BaseParticleSpec spec) {
        Particle ret;

        if (prototypes.containsKey(spec)) {
            ret = new Particle(prototypes.get(spec));
        } else {
            Particle particle = new Particle(spec);
            prototypes.put(spec, particle);
            ret = new Particle(spec);
        }

        //ret.vel.accelerate(0.1f,90);

        return ret;
    }
}
