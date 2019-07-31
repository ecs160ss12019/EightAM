package EightAM.asteroids.interfaces;

import java.util.Collection;

import EightAM.asteroids.DestroyedObject;
import EightAM.asteroids.GameObject;
import EightAM.asteroids.Messages;

public interface EventHandler extends DestructListener {
    void createObjects(Collection<GameObject> objects);

    void destroyObjects(Collection<Destructable> objects);

    void processScore(DestroyedObject scoreable);

    void sendMessage(Messages.Message message);
}
