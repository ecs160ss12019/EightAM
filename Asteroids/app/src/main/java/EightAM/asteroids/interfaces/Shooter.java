package EightAM.asteroids.interfaces;

import android.graphics.Point;

import EightAM.asteroids.ObjectID;
import EightAM.asteroids.specs.BaseBulletSpec;

public interface Shooter {
    void shoot();

    BaseBulletSpec getBulletSpec();

    Point getShotOrigin();

    float getShotAngle();

    ObjectID getID();

    void linkShotListener(ShotListener listener);
}
