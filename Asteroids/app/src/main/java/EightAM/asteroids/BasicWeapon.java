package EightAM.asteroids;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseWeaponSpec;

class BasicWeapon extends Weapon {
    BasicWeapon(BaseWeaponSpec spec) {
        super(spec);
    }

    BasicWeapon(BasicWeapon weapon) {
        super(weapon);
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        super.fire(shooter);
        reloadTimer.reset();
        return Collections.singleton(BulletGenerator.createBullet(shooter));
    }

    @Override
    public Object makeCopy() {
        return new BasicWeapon(this);
    }
}
