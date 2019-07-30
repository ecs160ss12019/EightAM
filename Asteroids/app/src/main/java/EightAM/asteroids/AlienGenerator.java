package EightAM.asteroids;

import static EightAM.asteroids.Constants.BIGALIEN_SPAWN_PROB;
import static EightAM.asteroids.Constants.SMALLALIEN_SPAWN_PROB;

import android.graphics.Point;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BigAlienSpec;
import EightAM.asteroids.specs.SmallAlienSpec;

public class AlienGenerator extends CollidableObjectGenerator {
    static AlienGenerator instance;
    private boolean debug = true;

    private AlienGenerator() {}

    static void init() { if (instance == null) instance = new AlienGenerator(); }

    static AlienGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    /**
     * Makes an alien and puts it into model's objectMap.
     * @param spaceSize the space size
     */
    public Collection<GameObject> createAlien(Point spaceSize) {
        BaseAlienSpec spec = getAlienSpec();

        return Collections.singleton(prepareAlien(spec, spaceSize));
    }

    private GameObject prepareAlien(BaseAlienSpec spec, Point spaceSize) {
        // change initial position in alien spec
        Point origin = getRandomPosition(spaceSize);
        spec.initialPosition = origin;
        if (debug) {
            Log.d("space size x", Integer.toString(spaceSize.x));
            Log.d("space size y", Integer.toString(spaceSize.y));
            Log.d("alien spawn pos x", Integer.toString(origin.x));
            Log.d("alien spawn pos y", Integer.toString(origin.y));
        }

        GameObject alien = BaseFactory.getInstance().create(spec);
        alien.hitbox.offsetTo(origin.x, origin.y);
        alien.vel.resetVelocity(alien.vel.maxSpeed, 0, alien.vel.maxSpeed);
        if (origin.x == spaceSize.x) alien.vel.x *= -1;

        return alien;
    }

    /**
     * Generates what kind of Alien Spec to use based on probability.
     * Also determines spawn position.
     * @return some Alien Spec
     */
    private BaseAlienSpec getAlienSpec() {
        Random rand = new Random();
        float f = rand.nextFloat();

        // prob comparisons
        // should be listed smallest -> largest
        if (f < SMALLALIEN_SPAWN_PROB) {
            if (debug) { Log.d("debug", "attempt to make small alien"); }
            return new SmallAlienSpec();
        }
        if (f < BIGALIEN_SPAWN_PROB) {
            if (debug) { Log.d("debug", "attempt to make big alien"); }
            return new BigAlienSpec();
        }

        return new BigAlienSpec(); // default alien to generate
    }

    /**
     * Generates a Point that has a random y position and an x that is either
     * on the left or on the right.
     * @param spaceSize a Point representing the size of space/canvas
     * @return a Point
     */
    private Point getRandomPosition(Point spaceSize) {
        Random rand = new Random();
        int randX, randY;

        randX = spaceSize.x * rand.nextInt(2);
        randY = rand.nextInt(((spaceSize.y - 1) + 1) + 1);

        return new Point(randX, randY);
    }


}
