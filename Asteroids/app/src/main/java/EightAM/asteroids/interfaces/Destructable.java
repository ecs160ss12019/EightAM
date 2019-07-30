package EightAM.asteroids.interfaces;

import EightAM.asteroids.DestroyedObject;

public interface Destructable extends Identifiable {
    void destruct(DestroyedObject destroyedObject);

    void registerDestructListener(DestructListener listener);
}
