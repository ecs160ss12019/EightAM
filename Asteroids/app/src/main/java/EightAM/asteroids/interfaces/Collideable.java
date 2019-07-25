package EightAM.asteroids.interfaces;

import EightAM.asteroids.GameObject;

public interface Collideable {

    /**
     * Collision detection method takes in the hitbox of approaching object, using intersection
     * method to check of collision
     *
     * @param approachingObject the hitbox of approaching object,
     * @return true for collision, otherwise false
     */
    boolean detectCollisions(GameObject approachingObject);
}
