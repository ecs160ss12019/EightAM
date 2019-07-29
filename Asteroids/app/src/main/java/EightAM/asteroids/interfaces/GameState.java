package EightAM.asteroids.interfaces;

import java.util.Set;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Ship;

public interface GameState {
    Ship getPlayerShip();

    GameObject getGameObject(ObjectID id);
}
