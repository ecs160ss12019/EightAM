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
        Particle particle;
        particle = prototypes.get(spec);
        if (particle == null) {
            particle = new Particle(spec);
            prototypes.put(spec, particle);
        }
        return (Particle) particle.makeCopy();
    }
}
