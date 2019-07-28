package EightAM.asteroids;

import java.util.Map;
import java.util.Set;

public abstract class GameObjectGenerator {
    public void addToMap(GameObject object, Set<ObjectID> objectIDs , Map<ObjectID, GameObject> objectMap){
        objectIDs.add(object.getID());
        objectMap.put(object.getID(), object);
    }
}
