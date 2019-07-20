package EightAM.asteroids;



public class AsteroidFactory implements Factory {
    int spaceWidth;
    int spaceHeight;
    int numOfAsteroids;

    AsteroidFactory(int screenWidth, int screenHeight, int initialAsteroids) {
        spaceHeight = screenHeight;
        spaceWidth = screenWidth;
        numOfAsteroids = initialAsteroids;
    }
    
    GameObject create(Context context, GameObject object) {
        return (new Asteroid(this, spaceWidth, spaceHeight, shipPosX, shipPosY, context));
    }

    void createBelt(Context context, ArrayList <GameObject> objects){
        for (int i = 0; i < numOfAsteroids; i++) {
            asteroidBelt.add(new Asteroid(this, spaceWidth, spaceHeight, shipPosX, shipPosY, context));
        }
    }

    void destroy(GameObject object){

    }

    void respawn(GameObject object){

    }
}
