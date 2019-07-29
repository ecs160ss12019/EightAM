package EightAM.asteroids.interfaces;

import java.util.Set;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.ObjectID;
import EightAM.asteroids.Ship;

public interface GameState {
    Ship getPlayerShip();

    Set<ObjectID> getCollideableIDs();

    Set<ObjectID> getAsteroidIDs();

    Set<ObjectID> getAlienIDs();

    Set<ObjectID> getBulletIDs();

    GameObject getGameObject(ObjectID id);
}
