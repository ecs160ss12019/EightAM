package EightAM.asteroids;

/**
 * The Velocity class holds the magnitude and direction of velocity.
 *
 * Getter functions return components of the velocity (with respects to x and y).
 */
public class Velocity {
    float x;
    float y;

    /**
     * Creates an instance of Velocity.
     * Meant to be used within a GameObject.
     *
     * @param speed - Magnitude of velocity
     * @param angle - Direction of velocity
     */

    protected Velocity(float speed, float angle) {
        x = speed * (float) Math.cos(angle);
        y = speed * (float) Math.sin(angle);
    }

    /**
     * Update Velocity
     * Increment Speed (Magnitude of velocity)
     * Update Velocity by assignment. //<- subject to change, If you guys think its easier to increment angle
     */
    protected void accelerate(float magnitude, float orientation) {
        x += (float) Math.cos(orientation) * magnitude;
        y += (float) Math.sin(orientation) * magnitude;
    }

    protected void decelerate(float magnitude) {
        x *= magnitude;
        y *= magnitude;
    }

    protected float magnitude(){
        return (float) Math.hypot(x, y);
    }
}
