package EightAM.asteroids.interfaces;

import java.util.Set;

import EightAM.asteroids.AbstractShip;
import EightAM.asteroids.GameObject;
import EightAM.asteroids.ObjectID;

public interface GameState {
    AbstractShip getPlayerShip();

    Set<ObjectID> getCollideableIDs();

    GameObject getGameObject(ObjectID id);
}
