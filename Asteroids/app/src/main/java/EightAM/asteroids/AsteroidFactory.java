package EightAM.asteroids;

import android.content.Context;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class AsteroidFactory extends Factory {

    public AsteroidFactory(GameModel gameModel) {
        model = gameModel;
        objectsToDelete = new ArrayDeque<Integer>();
    }
    
    public GameObject create(Context context) {
        return (new Asteroid(model, model.spaceWidth, model.spaceHeight, model.ship, context));
    }
}
