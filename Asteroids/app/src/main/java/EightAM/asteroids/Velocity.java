package EightAM.asteroids;

/**
 * The Velocity class holds the magnitude and direction of velocity.
 *
 * Getter functions return components of the velocity (with respects to x and y).
 */
public class Velocity {
    float magnitude;
    float angle;

    /**
     * Creates an instance of Velocity.
     * Meant to be used within a GameObject.
     *
     * @param speed - Magnitude of velocity
     * @param angle - Direction of velocity
     */

    protected Velocity(float speed, float angle) {
        this.magnitude = speed;
        this.angle = angle;
    }

    /**
     * Update Velocity
     * Increment Speed (Magnitude of velocity)
     * Update Velocity by assignment. //<- subject to change, If you guys think its easier to increment angle
     */
    protected void updateVelocity(float changeInSpeed, float changeInAngle) {
        this.magnitude *= changeInSpeed;
        this.angle += changeInAngle % (2 * Math.PI);
    }

    /**
     * @return X component of Velocity
     */
    protected float velX() {
        return this.magnitude * (float) Math.cos(angle);
    }

    /**
     * @return Y component of velocity
     */
    protected float velY() {
        return this.magnitude * (float) Math.sin(angle);
    }
}
