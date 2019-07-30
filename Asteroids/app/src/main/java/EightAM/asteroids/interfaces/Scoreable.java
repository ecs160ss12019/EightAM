package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;
import EightAM.asteroids.ObjectID;

// implemented by game objects which increment score
public interface Scoreable extends Identifiable {
    int score();

    ObjectID getKiller();

    GameObject getDestroyedObject();
}
