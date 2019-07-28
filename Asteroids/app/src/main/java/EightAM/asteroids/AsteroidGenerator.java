package EightAM.asteroids;

import android.graphics.Point;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import EightAM.asteroids.specs.LargeAsteroidSpec;


import EightAM.asteroids.GameRandom;
import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;
import static EightAM.asteroids.Constants.SAFE_DISTANCE;

public class AsteroidGenerator extends GameObjectGenerator{
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


    private GameObject setVelocity(Asteroid asteroid){
        float randSpeed = GameRandom.randomFloat(asteroid.speedRange.first, asteroid.speedRange.second);
        float randAngle = GameRandom.randomFloat(Float.MIN_VALUE, (float)Math.PI * 2);
        asteroid.vel = new Velocity(randSpeed, randAngle, asteroid.speedRange.second);
        asteroid.angularVel = GameRandom.randomFloat(asteroid.spinRange.first, asteroid.spinRange.second);
        return asteroid;
    }

    public void createBelt(Set<ObjectID> asteroids, Map<ObjectID, GameObject> objectMap, Point spaceSize, Ship ship) {
        Point randPoint;

        for (int i = 0; i < numOfAsteroids; i++){
            GameObject asteroid = BaseFactory.getInstance().create(new LargeAsteroidSpec());
            randPoint = getRandomPosition(spaceSize, ship.getObjPos());
            asteroid.hitbox.offset(randPoint.x, randPoint.y);
            asteroid = setVelocity((Asteroid) asteroid);
            addToMap(asteroid, asteroids, objectMap);
        }

    }

    public void breakUpAsteroid(Asteroid parentAsteroid, Set<ObjectID> asteroids, Map<ObjectID, GameObject> objectMap) {
        if (parentAsteroid.breaksInto == null) return;

        for (int i = 0; i < 2; i++){
            GameObject asteroid = BaseFactory.getInstance().create(parentAsteroid.breaksInto);
            asteroid.hitbox.offset(parentAsteroid.getObjPos().x, parentAsteroid.getObjPos().y);
            asteroid = setVelocity((Asteroid) asteroid);
            addToMap(asteroid, asteroids, objectMap);
        }
    }
}
