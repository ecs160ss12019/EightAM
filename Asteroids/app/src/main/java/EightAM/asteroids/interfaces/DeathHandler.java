package EightAM.asteroids.interfaces;

public interface DeathHandler extends DestructListener {
    void processDeathEvent(DeathEvent event);
}
