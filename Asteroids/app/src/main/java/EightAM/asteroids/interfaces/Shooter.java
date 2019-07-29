package EightAM.asteroids.interfaces;

import android.graphics.Point;

import EightAM.asteroids.specs.BaseBulletSpec;

public interface Shooter extends Identifiable {
    void shoot(/*Weapon weapon (to be implemented...)*/);

    BaseBulletSpec getBulletSpec();

    Point getShotOrigin();

    float getShotAngle();

    void linkShotListener(ShotListener listener);
}
