package EightAM.asteroids.interfaces;

import EightAM.asteroids.Particle;
import EightAM.asteroids.specs.BaseParticleSpec;

public interface ParticleFactory {
    Particle createParticle(BaseParticleSpec spec);
}
