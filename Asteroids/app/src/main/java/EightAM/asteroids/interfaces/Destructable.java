package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameModel;
import EightAM.asteroids.ObjectID;

public interface Destructable {
    void destruct(GameModel model);

    void linkDestructListener(DestructListener listener);

    ObjectID getID();
}
