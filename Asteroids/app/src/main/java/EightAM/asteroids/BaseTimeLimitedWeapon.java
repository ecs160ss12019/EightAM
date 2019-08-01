package EightAM.asteroids;

import EightAM.asteroids.specs.BaseTimeLimitedWeaponSpec;

abstract class BaseTimeLimitedWeapon extends Weapon {
    Timer timeLimit;

    BaseTimeLimitedWeapon(BaseTimeLimitedWeaponSpec spec) {
        super(spec);
        this.timeLimit = new Timer(spec.timeLimit, 0);
    }

    BaseTimeLimitedWeapon(BaseTimeLimitedWeapon weapon) {
        super(weapon);
        this.timeLimit = new Timer(weapon.timeLimit);
    }

    @Override
    void update(long deltaTime) {
        super.update(deltaTime);
        timeLimit.update(deltaTime);
    }

    @Override
    boolean canFire() {
        return super.canFire() && !timeLimit.reachedTarget;
    }
}
