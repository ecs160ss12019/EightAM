package EightAM.asteroids;

import android.graphics.Point;

import java.util.Map;
import java.util.Random;

import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BigAlienSpec;
import EightAM.asteroids.specs.SmallAlienSpec;

import static EightAM.asteroids.Constants.BIGALIEN_SPAWN_PROB;
import static EightAM.asteroids.Constants.SMALLALIEN_SPAWN_PROB;

public class AlienGenerator extends CollidableObjectGenerator {
    static AlienGenerator instance;

    private AlienGenerator() {}

    static void init() { if (instance == null) instance = new AlienGenerator(); }

    static AlienGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    public void createAlien(Map<ObjectID, GameObject> objectMap, Point spaceSize) {
        Point spawnPos = getRandomPosition(spaceSize);
        BaseAlienSpec spec = getAlienSpec();
        GameObject alien = BaseFactory.getInstance().create(spec);
        objectMap.put(alien.getID(), alien);
    }

    /**
     * Generates what kind of Alien Spec to use based on probability.
     * @return some Alien Spec
     */
    private BaseAlienSpec getAlienSpec() {
        Random rand = new Random();
        float f = rand.nextFloat();

        // prob comparisons
        // should be listed smallest -> largest
        if (f < SMALLALIEN_SPAWN_PROB) { return new SmallAlienSpec(); }
        if (f < BIGALIEN_SPAWN_PROB) { return new BigAlienSpec(); }

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

        randX = rand.nextInt(((spaceSize.x - 1) + 1) + 1) * rand.nextInt(2);
        randY = rand.nextInt(((spaceSize.y - 1) + 1) + 1);

        return new Point(randX, randY);
    }
}
