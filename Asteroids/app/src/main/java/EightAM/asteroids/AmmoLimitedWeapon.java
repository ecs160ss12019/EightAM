package EightAM.asteroids;

import EightAM.asteroids.interfaces.LimitedWeapon;
import EightAM.asteroids.specs.BaseAmmoLimitedWeaponSpec;

abstract class AmmoLimitedWeapon extends Weapon implements LimitedWeapon {
    Timer ammoCount;

    AmmoLimitedWeapon(BaseAmmoLimitedWeaponSpec spec) {
        super(spec);
        this.ammoCount = new Timer(spec.ammoCount, 0);
    }

    AmmoLimitedWeapon(AmmoLimitedWeapon weapon) {
        super(weapon);
        this.ammoCount = new Timer(weapon.ammoCount);
    }

    @Override
    boolean canFire() {
        return super.canFire() && !ammoCount.reachedTarget;
    }

    @Override
    public boolean expired() {
        return ammoCount.reachedTarget;
    }

    @Override
    public int amountLeft() {
        return (int) ammoCount.remaining();
    }

    @Override
    public int amountTotal() {
        return (int) ammoCount.total();
    }
}
