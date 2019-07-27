package EightAM.asteroids.interfaces;

import android.graphics.PointF;

import EightAM.asteroids.ObjectID;

public interface Shooter {
    void shoot();

    PointF getShotOrigin();

    float getShotAngle();

    ObjectID getID();

    void linkShotListener(ShotListener listener);
}
