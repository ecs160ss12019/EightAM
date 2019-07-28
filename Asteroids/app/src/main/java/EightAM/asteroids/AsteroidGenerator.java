package EightAM.asteroids;

import android.graphics.Point;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import EightAM.asteroids.specs.LargeAsteroidSpec;

import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;
import static EightAM.asteroids.Constants.SAFE_DISTANCE;

public class AsteroidGenerator {
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

    public void createBelt(Set<ObjectID> asteroids, Map<ObjectID, GameObject> objectMap, Point spaceSize, Point shipPos) {
        Point randPoint;

        for (int i = 0; i < numOfAsteroids; i++){
            GameObject asteroid = BaseFactory.getInstance().create(new LargeAsteroidSpec());
            randPoint = getRandomPosition(spaceSize, shipPos);
            asteroid.hitbox.offset(randPoint.x, randPoint.y);

            asteroids.add(asteroid.getID());
            objectMap.put(asteroid.getID(), asteroid);
        }
    }

    public void breakUpAsteroid(Asteroid parentAsteroid, Set<ObjectID> asteroids, Map<ObjectID, GameObject> objectMap) {
        for (int i = 0; i < 2; i++){
            GameObject asteroid = BaseFactory.getInstance().create(parentAsteroid.breaksInto);
            asteroid.hitbox.offset(parentAsteroid.hitbox.centerX(), parentAsteroid.hitbox.centerY());
            asteroids.add(asteroid.getID());
            objectMap.put(asteroid.getID(), asteroid);
        }
    }

    private Point getRandomPosition(Point spaceSize, Point shipPos){
        Random rand = new Random();
        int randX;
        int randY;

        do{
            randX = rand.nextInt(spaceSize.x) - shipPos.x;
            randY = rand.nextInt(spaceSize.y) - shipPos.y;
        } while (Math.hypot(randX - shipPos.x, randY - shipPos.y) < SAFE_DISTANCE);

        return new Point(randX, randY);
    }
}
