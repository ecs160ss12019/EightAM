package EightAM.asteroids.interfaces;

import java.util.Collection;

import EightAM.asteroids.DestroyedObject;
import EightAM.asteroids.GameObject;
import EightAM.asteroids.Messages;
import EightAM.asteroids.ObjectID;

public interface EventHandler extends DestructListener {
    void createObjects(Collection<GameObject> objects);

    void destroyObjects(Collection<Destructable> objects, ObjectID killerID);

    void teleportObjects(Collection<ObjectID> tpList);

    void processScore(DestroyedObject scoreable);

    void sendMessage(Messages.Message message);
}
