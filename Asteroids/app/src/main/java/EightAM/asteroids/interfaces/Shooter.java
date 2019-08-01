package EightAM.asteroids.interfaces;

import android.graphics.Point;

import EightAM.asteroids.Weapon;

public interface Shooter extends Identifiable {
    void shoot(/*Weapon weapon (to be implemented...)*/);

    Weapon getWeapon();

    Point getShotOrigin();

    float getShotAngle();

    boolean canShoot();

    void linkShotListener(ShotListener listener);
}
