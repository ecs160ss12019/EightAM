package EightAM.asteroids;

public abstract class CollidableObjectGenerator {
    public void addToMap(GameObject object, GameModel model){
        model.collideables.add(object.getID());
        model.objectMap.put(object.getID(), object);
    }
}
