package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.ParticleFactory;
import EightAM.asteroids.specs.ParticleSpec;

public class BasicParticleFactory implements ParticleFactory {
    static BasicParticleFactory instance;
    private Map<ParticleSpec, Particle> prototypes;

    private BasicParticleFactory() {
        prototypes = new HashMap<>();
    }

    static void init() {
        if (instance == null) instance = new BasicParticleFactory();
    }

    @Override
    public Particle createParticle(ParticleSpec spec) {
        Particle ret;
        //TODO: Create another constructor in Particle class and modify
        //      Existing one.
        if (prototypes.containsKey(spec)) {
            ret = new Particle(prototypes.get(spec));
        } else {
            Particle particle = new Particle(spec);
            prototypes.put(spec, particle);
            ret = new Particle(spec);
        }

        // Position of collision will be set by generator
        return ret;
    }

}