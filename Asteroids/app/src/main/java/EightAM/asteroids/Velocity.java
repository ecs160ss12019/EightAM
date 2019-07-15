package EightAM.asteroids;

/**
 * The Velocity class holds components of velocity (x and y components)
 * and has a method to calculate total velocity.
 *
 * Its intention is to simplify the calculation of total velocity
 * for instances of GameObject.
 */
public class Velocity {
    float velX;
    float velY;

    /**
     * Creates an instance of Velocity.
     * Meant to be used within a GameObject.
     * @param x component of velocity
     * @param y component of velocity
     */
    protected Velocity(float x, float y) {
        velX = x;
        velY = y;
    }

    /**
     * Calculates the total velocity of the object with which this instance
     * of velocity is associated.
     * @return total velocity of the object
     */
    protected float totalVelocity() {
        return (float)Math.sqrt(Math.pow(this.velX, 2) + Math.pow(this.velY, 2));
    }
}
