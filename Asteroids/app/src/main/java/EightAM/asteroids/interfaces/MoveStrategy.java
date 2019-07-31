package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;

public interface MoveStrategy {
    void move(GameObject gameObject, long deltaTime);
}
