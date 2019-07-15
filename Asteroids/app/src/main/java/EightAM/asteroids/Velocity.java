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
    float vel;

    protected Velocity(float x, float y) {
        velX = x;
        velY = y;
        vel = (float)Math.sqrt(Math.pow(this.velX, 2) + Math.pow(this.velY, 2));
    }

    protected float totalVelocity() {
        vel = (float)Math.sqrt(Math.pow(this.velX, 2) + Math.pow(this.velY, 2));
        return vel;
    }
}
