package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.WeaponFactory;
import EightAM.asteroids.specs.BaseAmmoLimitedWeaponSpec;
import EightAM.asteroids.specs.BaseTimeLimitedWeaponSpec;
import EightAM.asteroids.specs.BaseWeaponSpec;
import EightAM.asteroids.specs.LaserWeaponSpec;
import EightAM.asteroids.specs.ShotgunWeaponSpec;

class BaseWeaponFactory implements WeaponFactory {
    static BaseWeaponFactory instance;
    Map<BaseWeaponSpec, Weapon> prototypes;

    BaseWeaponFactory() {
        prototypes = new HashMap<>();
    }

    static BaseWeaponFactory getInstance() {
        if (instance == null) instance = new BaseWeaponFactory();
        return instance;
    }

    @Override
    public Weapon createWeapon(BaseWeaponSpec spec) {
        Weapon weapon = prototypes.get(spec);
        if (weapon == null) {
            if (spec instanceof BaseAmmoLimitedWeaponSpec) {
                if (spec instanceof ShotgunWeaponSpec) {
                    weapon = new ShotgunWeapon((ShotgunWeaponSpec) spec);
                }
            } else if (spec instanceof BaseTimeLimitedWeaponSpec) {
                if (spec instanceof LaserWeaponSpec) {
                    weapon = new LaserWeapon((LaserWeaponSpec) spec);
                }
            } else {
                weapon = new BasicWeapon(spec);
            }
        }
        return (Weapon) weapon.makeCopy();
    }
}
