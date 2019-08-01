package EightAM.asteroids;

/**
 * The Velocity class holds its x and y components.
 */
public class Velocity {
    float x;
    float y;
    float maxSpeed;

    /**
     * Creates an instance of Velocity.
     * Meant to be used within a GameObject.
     *
     * @param speed - Magnitude of velocity
     * @param angle - Direction of velocity
     * @param maxSpeed - Speed limit of the object
     */

    public Velocity(float speed, float angle, float maxSpeed) {
        this.resetVelocity(speed, angle, maxSpeed);
    }

    Velocity(Velocity velocity) {
        this.maxSpeed = velocity.maxSpeed;
        this.x = velocity.x;
        this.y = velocity.y;
        if (magnitude() > maxSpeed) {
            x = maxSpeed * (float) Math.cos(Math.atan2(y, x));
            y = maxSpeed * (float) Math.sin(Math.atan2(y, x));
        }
    }

    /**
     * Updates the velocity by adding to its components over time
     * Caps the magnitude at maxSpeed
     */
    protected void accelerate(float magnitude, float orientation, float decelerateVal) {
        x += (float) Math.cos(orientation) * magnitude;
        y += (float) Math.sin(orientation) * magnitude;

        //Code below made ship accelerate smoothly
        while (magnitude() > maxSpeed) {
            decelerate(decelerateVal);
        }
    }


    protected void decelerate(float magnitude) {
        x *= magnitude;
        y *= magnitude;
    }

    protected float magnitude() {
        return (float) Math.hypot(x, y);
    }

    // setter functions

    protected void setX(float newX) {
        x = newX;
    }

    protected void setY(float newY) {
        y = newY;
    }

    float getAngle(){
        return (float)Math.atan2(y,x);
    }

    void resetVelocity(float speed, float angle, float maxSpeed) {
        this.maxSpeed = maxSpeed;
        if (speed > maxSpeed) speed = maxSpeed;
        x = speed * (float) Math.cos(angle);
        y = speed * (float) Math.sin(angle);
    }
}
