package EightAM.asteroids.interfaces;

import java.util.Collection;

import EightAM.asteroids.GameObject;

public interface DeathEvent extends Destructable {
    Collection<GameObject> createOnDeath();
}
