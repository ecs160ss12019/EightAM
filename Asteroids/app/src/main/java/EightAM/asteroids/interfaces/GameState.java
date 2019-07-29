package EightAM.asteroids.interfaces;

import java.util.Set;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Ship;

public interface GameState {
    Ship getPlayerShip();

    Set<ObjectID> getCollideableIDs();

    GameObject getGameObject(ObjectID id);
}
