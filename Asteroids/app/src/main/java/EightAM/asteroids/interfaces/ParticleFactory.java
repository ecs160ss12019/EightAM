package EightAM.asteroids.interfaces;

import EightAM.asteroids.Particle;
import EightAM.asteroids.specs.ParticleSpec;

public interface ParticleFactory {
    Particle createParticle(ParticleSpec spec);
}
