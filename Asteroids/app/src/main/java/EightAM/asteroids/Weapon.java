package EightAM.asteroids;

import android.util.Log;

import java.util.Collection;

import EightAM.asteroids.interfaces.Copyable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseWeaponSpec;

public abstract class Weapon implements Copyable {
    BaseBulletSpec bulletSpec;
    Timer reloadTimer;

    public Weapon(BaseWeaponSpec weaponSpec) {
        this.bulletSpec = weaponSpec.bulletSpec;
        Log.d(weaponSpec.getClass().getCanonicalName(), Integer.toString(weaponSpec.reloadTime));
        this.reloadTimer = new Timer(weaponSpec.reloadTime, 0);
    }

    public Weapon(Weapon weapon) {
        this.bulletSpec = weapon.bulletSpec;
        Log.d(weapon.getClass().getCanonicalName(), weapon.reloadTimer.toString());
        this.reloadTimer = new Timer(weapon.reloadTimer);
    }

    long timeTillNextShot() {
        return reloadTimer.remaining();
    }

    void update(long deltaTime) {
        reloadTimer.update(deltaTime);
    }

    boolean canFire() {
        return reloadTimer.reachedTarget;
    }

    abstract Collection<GameObject> fire(Shooter shooter);
}
