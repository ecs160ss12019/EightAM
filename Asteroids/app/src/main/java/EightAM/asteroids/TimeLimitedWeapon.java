package EightAM.asteroids;

import EightAM.asteroids.interfaces.LimitedWeapon;
import EightAM.asteroids.specs.BaseTimeLimitedWeaponSpec;

abstract class TimeLimitedWeapon extends Weapon implements LimitedWeapon {
    Timer timeLimit;

    TimeLimitedWeapon(BaseTimeLimitedWeaponSpec spec) {
        super(spec);
        this.timeLimit = new Timer(spec.timeLimit, 0);
    }

    TimeLimitedWeapon(TimeLimitedWeapon weapon) {
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

    @Override
    public boolean expired() {
        return timeLimit.reachedTarget;
    }

    @Override
    public int amountLeft() {
        return (int) timeLimit.remaining();
    }

    @Override
    public int amountTotal() {
        return (int) timeLimit.total();
    }
}
