package EightAM.asteroids;

import java.util.Collection;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Copyable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseWeaponSpec;

public abstract class Weapon implements Copyable, AudioGenerator {
    String name;
    BaseBulletSpec bulletSpec;
    Timer reloadTimer;

    int shootID;
    AudioListener audioListener;

    public Weapon(BaseWeaponSpec weaponSpec) {
        this.name = weaponSpec.tag;
        this.bulletSpec = weaponSpec.bulletSpec;
//        Log.d(weaponSpec.getClass().getCanonicalName(), Integer.toString(weaponSpec.reloadTime));
        this.reloadTimer = new Timer(weaponSpec.reloadTime, 0);
        this.shootID = weaponSpec.shootID;
    }

    public Weapon(Weapon weapon) {
        this.name = weapon.name;
        this.bulletSpec = weapon.bulletSpec;
//        Log.d(weapon.getClass().getCanonicalName(), weapon.reloadTimer.toString());
        this.reloadTimer = new Timer(weapon.reloadTimer);
        this.shootID = weapon.shootID;
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

    @Override
    public void registerAudioListener(AudioListener listener) {
        this.audioListener = listener;
    }

    Collection<GameObject> fire(Shooter shooter) {
        audioListener.sendSoundCommand(shootID);
        return null;
    }
}
