package EightAM.asteroids;

import java.util.Collection;
import java.util.Map;
import android.graphics.Point;

import androidx.collection.ArraySet;

import EightAM.asteroids.specs.ParticleSpec;
import static EightAM.asteroids.Constants.EFFECT_NUM;

public class ParticleGenerator {
    static ParticleGenerator instance;
    int numOfParticles;

    private ParticleGenerator() {
        this.numOfParticles = EFFECT_NUM;
    }

    static void init() {
        if (instance == null)
            instance = new ParticleGenerator();
    }

    static ParticleGenerator getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    public Collection<GameObject> createParticles(Point objectPos) {
        ParticleSpec spec = new ParticleSpec();
        Collection<GameObject> particlesEffect= new ArraySet<>();
        for (int i = 0; i < numOfParticles; i++) {
            GameObject particle = BaseFactory.getInstance().create(spec);
            // Get random position
            float randAngle = (float) (Math.random() * Math.PI * 2);
            float randSpeed = (float) Math.random() * (spec.speedRange.second
                    - spec.speedRange.first) + spec.speedRange.first;
            particle.vel.resetVelocity(randSpeed, randAngle, spec.speedRange.second);
            // Reset hit box position
            particle.hitbox.offsetTo(objectPos.x, objectPos.y);
            particlesEffect.add(particle);
        }
        return particlesEffect;
    }
}
