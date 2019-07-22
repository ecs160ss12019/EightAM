package EightAM.asteroids;

import android.content.Context;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class AsteroidFactory extends Factory {


    public AsteroidFactory(GameModel gameModel) {
        model = gameModel;
        objectsToDelete = new ArrayDeque<Integer>();
    }

    public Asteroid createNew() {
        return (new Asteroid(model, model.spaceWidth, model.spaceHeight, model.playerShip, model.context));
    }

    public void createAsteroidBelt(int arraySize){
        for (int i = 0; i < arraySize; i++) {
            model.asteroidBelt.add(createNew());
        }
    }

    public void removeAtIndex(int asteroidIndex) {
        model.asteroidBelt.remove(asteroidIndex);
        //TODO: push asteroid explosion event
    }

    public void addToBelt(Context context, ArrayList<GameObject> asteroidBelt, Asteroid asteroid) {
        //asteroidBelt.add(new Asteroid(asteroid, context));
    }
}
