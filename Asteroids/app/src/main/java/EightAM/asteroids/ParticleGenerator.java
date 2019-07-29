package EightAM.asteroids;

import java.util.Map;

import android.graphics.Point;
import android.util.Log;

import EightAM.asteroids.specs.ParticleSpec;

import static EightAM.asteroids.Constants.EFFECT_NUM;
import static EightAM.asteroids.Constants.EFFECT_RADIUS;

public class ParticleGenerator {
    static ParticleGenerator instance;
    int numOfParticles;

    private ParticleGenerator() {
        this.numOfParticles = EFFECT_NUM;
    }

    static void init() {
        if (instance == null) instance = new ParticleGenerator();
    }

    static ParticleGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    public void createParticles(Map<ObjectID, GameObject> objectMap, Point spaceSize, Point objectPos) {
        Point randPoint;
        Log.d("map size", "size before:" + objectMap.size());
        ParticleSpec spec = new ParticleSpec();
        for (int i = 0; i < numOfParticles; i++) {
            GameObject particle = BaseFactory.getInstance().create(spec);
            float angle = (float) (Math.random() * Math.PI * 2);
            int x = (int) Math.cos(angle) * EFFECT_RADIUS + objectPos.x;
            int y = (int) Math.sin(angle) * EFFECT_RADIUS + objectPos.y;
            randPoint = getRandomPosition(spaceSize, objectPos);
            particle.hitbox.offsetTo(randPoint.x, randPoint.y);
            float randSpeed = (float) Math.random() * (spec.speedRange.second - spec.speedRange.first) + spec.speedRange.first;
            particle.vel.resetVelocity(randSpeed, angle, spec.speedRange.second);
            objectMap.put(particle.getID(), particle);
        }
        Log.d("map size", "size after:" + objectMap.size());
    }

    private Point getRandomPosition(Point size, Point pos) {
        double angle = Math.random() * Math.PI * 2;
        int x = (int) Math.cos(angle) * EFFECT_RADIUS + pos.x;
        int y = (int) Math.sin(angle) * EFFECT_RADIUS + pos.y;
        return new Point(x, y);
    }
}
