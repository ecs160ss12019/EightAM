package EightAM.asteroids;

public class Rotation {
    float theta;
    float angVel;

    public Rotation(float theta, float angVel) {
        this.theta = theta;
        this.angVel = angVel;
    }

    Rotation(Rotation rotation) {
        this.theta = rotation.theta;
        this.angVel = rotation.angVel;
    }

    void rotate(long deltaTime) {

        theta += angVel * deltaTime;
        if (theta > Math.PI) {
            theta -= 2 * Math.PI;
        }
        if (theta < -Math.PI) {
            theta += 2 * Math.PI;
        }
    }
}
