package EightAM.asteroids;

import EightAM.asteroids.interfaces.Scoreable;

public final class DestroyedObject implements Scoreable {

    ObjectID id;
    private int score;
    private ObjectID killer;
    private GameObject destroyed;

    public DestroyedObject(int score, ObjectID id, ObjectID killer, GameObject destroyed) {
        this.score = score;
        this.id = id;
        this.killer = killer;
        this.destroyed = destroyed;
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public ObjectID getKiller() {
        return killer;
    }

    @Override
    public GameObject getDestroyedObject() {
        return destroyed;
    }

    @Override
    public ObjectID getID() {
        return id;
    }
}
