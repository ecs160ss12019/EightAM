package EightAM.asteroids.interfaces;

import java.util.Collection;

import EightAM.asteroids.GameObject;

public interface EventHandler extends DestructListener {
    void createObjects(Collection<GameObject> objects);

    void destroyObjects(Collection<Destructable> objects);
}
