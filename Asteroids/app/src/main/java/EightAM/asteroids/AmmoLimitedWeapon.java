package EightAM.asteroids;

import EightAM.asteroids.specs.BaseAmmoLimitedWeaponSpec;

abstract class AmmoLimitedWeapon extends Weapon {
    Timer ammoCount;

    AmmoLimitedWeapon(BaseAmmoLimitedWeaponSpec spec) {
        super(spec);
        this.ammoCount = new Timer(0, spec.ammoCount);
    }

    AmmoLimitedWeapon(AmmoLimitedWeapon weapon) {
        super(weapon);
        this.ammoCount = new Timer(weapon.ammoCount);
    }

    @Override
    boolean canFire() {
        return super.canFire() && !ammoCount.reachedTarget;
    }
}
