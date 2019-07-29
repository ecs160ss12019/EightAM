package EightAM.asteroids.interfaces;

import EightAM.asteroids.ObjectID;

public interface Destructable {
    void destruct();

    void registerDestructListener(DestructListener listener);

    ObjectID getID();
}
