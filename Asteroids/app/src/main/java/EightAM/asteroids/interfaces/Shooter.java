package EightAM.asteroids.interfaces;

import android.graphics.Point;

import EightAM.asteroids.ObjectID;

public interface Shooter {
    void shoot();

    Point getShotOrigin();

    float getShotAngle();

    ObjectID getID();

    void linkShotListener(ShotListener listener);
}
