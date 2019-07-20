package EightAM.asteroids;

import android.content.Context;
import java.util.ArrayList;

public class AsteroidFactory implements Factory {
    int spaceWidth;
    int spaceHeight;
    int numOfAsteroids;

    public AsteroidFactory(int screenWidth, int screenHeight, int initialAsteroids) {
        spaceHeight = screenHeight;
        spaceWidth = screenWidth;
        numOfAsteroids = initialAsteroids;
    }
    
    public GameObject create(Context context, GameObject object) {
        return (new Asteroid(this, spaceWidth, spaceHeight, shipPosX, shipPosY, context));
    }

    public void createBelt(Context context, ArrayList<GameObject> objects){
        for (int i = 0; i < numOfAsteroids; i++) {
            objects.add(new Asteroid(this, spaceWidth, spaceHeight, shipPosX, shipPosY, context));
        }
    }

    public void destroy(GameObject object){

    }

    public void respawn(GameObject object){

    }
}
