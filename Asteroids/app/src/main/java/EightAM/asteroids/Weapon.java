package EightAM.asteroids;

import java.util.Collection;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;

abstract class Weapon {
    private BaseBulletSpec bulletSpec;
    private int reloadTime;
    private int timeTillNext;
    private boolean infiniteAmmo;
    private int ammo;

    int timeNextShot() {
        return timeTillNext;
    }

    void update(long deltaTime) {
        timeTillNext -= deltaTime;
        if (timeTillNext < 0) timeTillNext = 0;
    }

    boolean canFire() {
        return timeTillNext == 0;
    }

    abstract Collection<Bullet> fire(Shooter shooter);
}
