package EightAM.asteroids.interfaces;

public interface EventGenerator extends Destructable {
    void registerEventHandler(EventHandler handler);
}
