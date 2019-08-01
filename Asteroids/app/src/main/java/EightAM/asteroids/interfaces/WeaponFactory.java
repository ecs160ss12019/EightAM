package EightAM.asteroids.interfaces;

import EightAM.asteroids.Weapon;
import EightAM.asteroids.specs.BaseWeaponSpec;

public interface WeaponFactory {
    Weapon createWeapon(BaseWeaponSpec spec);
}
