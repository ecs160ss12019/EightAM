package EightAM.asteroids;

import static EightAM.asteroids.Constants.SAFE_DISTANCE;
import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;

import android.graphics.Point;


import androidx.collection.ArraySet;

import java.util.Collection;
import java.util.Random;

import EightAM.asteroids.specs.LargeAsteroidSpec;

public class AsteroidGenerator extends CollidableObjectGenerator {
    static AsteroidGenerator instance;
    int numOfAsteroids;

    private AsteroidGenerator() {
        numOfAsteroids = STARTING_ASTEROIDS;
    }

    static void init() {
        if (instance == null) instance = new AsteroidGenerator();
    }

    static AsteroidGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    private Point getRandomPosition(Point spaceSize, Point shipPos) {
        Random rand = new Random();
        int randX;
        int randY;

        do {
            randX = rand.nextInt(spaceSize.x) - shipPos.x;
            randY = rand.nextInt(spaceSize.y) - shipPos.y;
        } while (Math.hypot(randX - shipPos.x, randY - shipPos.y) < SAFE_DISTANCE);

        return new Point(randX, randY);
    }

    public Collection<GameObject> createBelt(Point spaceSize, Ship ship) {
        Point randPoint;
        Collection<GameObject> asteroidBelt= new ArraySet<>();
        for (int i = 0; i < numOfAsteroids; i++) {
            GameObject asteroid = BaseFactory.getInstance().create(new LargeAsteroidSpec());
            randPoint = getRandomPosition(spaceSize, ship.getObjPos());
            asteroid.hitbox.offset(randPoint.x, randPoint.y);
            asteroidBelt.add(asteroid);
        }
        return asteroidBelt;
    }

    public Collection<GameObject> breakUpAsteroid(Point spaceSize, Asteroid parentAsteroid) {
        Collection<GameObject> asteroidBelt= new ArraySet<>();
        if (parentAsteroid.breaksInto != null) {
            for (int i = 0; i < 2; i++) {
                GameObject asteroid = BaseFactory.getInstance().create(parentAsteroid.breaksInto);
                asteroid.hitbox.offsetTo(parentAsteroid.getObjPos().x, parentAsteroid.getObjPos().y);
                asteroidBelt.add(asteroid);
            }
        }
        return asteroidBelt;
    }
}
