package EightAM.asteroids;

import java.util.Map;
import java.util.Set;

public abstract class CollidableObjectGenerator {
    public void addToMap(GameObject object, GameModel model){
        model.collidables.add(object.getID());
        model.objectMap.put(object.getID(), object);
    }
}
