package EightAM.asteroids;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.LaserWeaponSpec;

class LaserWeapon extends BaseTimeLimitedWeapon {
    LaserWeapon(LaserWeaponSpec spec) {
        super(spec);
    }

    LaserWeapon(LaserWeapon weapon) {
        super(weapon);
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        reloadTimer.reset();
        return Collections.singleton(BulletGenerator.createBullet(shooter));
    }

    @Override
    public Object makeCopy() {
        return new LaserWeapon(this);
    }
}
