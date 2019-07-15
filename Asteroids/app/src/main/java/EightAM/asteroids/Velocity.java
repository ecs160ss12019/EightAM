package EightAM.asteroids;

/**
 * The Velocity class holds components of velocity (x and y components)
 * and has a method to calculate total velocity.
 *
 * Its intention is to simplify the calculation of total velocity
 * for instances of GameObject.
 */
public class Velocity {
    float speed;
    float angle;

    /**
     * Creates an instance of Velocity.
     * Meant to be used within a GameObject.
     * @param speed - Magnitude of velocity
     * @param angle - Direction of velocity
     */

    protected Velocity(float speed, float angle) {
        this.speed = speed;
        this.angle = angle;
    }

    /**
     * Update Velocity
     * Increment Speed (Magnitude of velocity)
     * Update Velocity by assignment. //<- subject to change, If you guys think its easier to increment angle
     *
     * @param changeInSpeed
     * @param changeInAngle
     */
    protected void updateVelocity(float changeInSpeed, float changeInAngle) {
        this.speed += changeInSpeed;
        this.angle += changeInAngle;
    }

    /**
     * @return X component of Velocity
     */
    protected float velX(){
        return this.speed * (float) Math.cos(angle);
    }

    /**
     * @return Y component of Velocity
     */
    protected float velY(){
        return this.speed * (float) Math.sin(angle);
    }
}
