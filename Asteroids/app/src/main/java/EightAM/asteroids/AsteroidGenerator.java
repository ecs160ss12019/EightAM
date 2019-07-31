package EightAM.asteroids;

import static EightAM.asteroids.Constants.SAFE_DISTANCE;

import android.graphics.Point;

import androidx.collection.ArraySet;

import java.util.Collection;
import java.util.Random;

import EightAM.asteroids.specs.LargeAsteroidSpec;

public class AsteroidGenerator extends CollidableObjectGenerator {
    private AsteroidGenerator() {
    }

    /**
     * Helper Function to init a asteroid position away from the player
     * Such that an asteroid does not unfairly spawn on top of a player
     *
     * @param spaceSize - Bounds to spawn the asteroid
     * @param shipPos   - Position of ship to check
     * @return a Valid random point
     */
    private static Point getRandomPosition(Point spaceSize, Point shipPos) {
        Random rand = new Random();
        int randX;
        int randY;

        do {
            randX = rand.nextInt(spaceSize.x);
            randY = rand.nextInt(spaceSize.y);
        } while (Math.hypot(Math.abs(randX - shipPos.x), Math.abs(randY - shipPos.y))
                < SAFE_DISTANCE);

        return new Point(randX, randY);
    }

    /**
     * Creates an asteroid belt and setting a random position for individual asteroids
     *
     * @param spaceSize - Bounds to spawn the asteroid
     * @param ship      - reference to player ship to retrieve position
     * @return a collection of asteroids
     */
    public static Collection<GameObject> createBelt(Point spaceSize, Ship ship,
            int numOfAsteroids) {
        Point randPoint;
        Collection<GameObject> asteroidBelt = new ArraySet<>();
        for (int i = 0; i < numOfAsteroids; i++) {
            GameObject asteroid = BaseFactory.getInstance().create(new LargeAsteroidSpec());
            randPoint = getRandomPosition(spaceSize, ship.getObjPos());
            asteroid.hitbox.offset(randPoint.x, randPoint.y);
            asteroidBelt.add(asteroid);
        }
        return asteroidBelt;
    }

    /**
     * Spawns asteroids in the place of its parent, i.e. upon breaking up
     *
     * @param parentAsteroid - reference to parent asteroid to retrieve position
     * @return a collection of baby asteroids
     */
    public static Collection<GameObject> breakUpAsteroid(Asteroid parentAsteroid) {
        Collection<GameObject> babyAsteroids = new ArraySet<>();
        if (parentAsteroid.breaksInto != null) {
            for (int i = 0; i < parentAsteroid.breakCount; i++) {
                GameObject asteroid = BaseFactory.getInstance().create(parentAsteroid.breaksInto);
                asteroid.hitbox.offsetTo(parentAsteroid.getObjPos().x,
                        parentAsteroid.getObjPos().y);
                babyAsteroids.add(asteroid);
            }
        }
        return babyAsteroids;
    }
}
