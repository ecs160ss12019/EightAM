package EightAM.asteroids.interfaces;

public interface Destructable extends Identifiable {
    void destruct();

    void registerDestructListener(DestructListener listener);
}
