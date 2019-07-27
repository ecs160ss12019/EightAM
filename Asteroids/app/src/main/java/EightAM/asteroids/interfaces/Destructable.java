package EightAM.asteroids.interfaces;

import EightAM.asteroids.ObjectID;

public interface Destructable {
    void destruct();

    void linkDestructListener(DestructListener listener);

    ObjectID getID();
}
