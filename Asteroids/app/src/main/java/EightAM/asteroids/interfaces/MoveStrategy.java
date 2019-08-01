package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;

/**
 * Move Strategy is implemented as a strategy pattern. Changes the behavior of
 * movement of the game objects.
 * This is used in the spawning of each object.
 */
public interface MoveStrategy {
    void move(GameObject gameObject, long deltaTime);
}
