package EightAM.asteroids.specs;

public abstract class BaseAmmoLimitedWeaponSpec extends BaseWeaponSpec {
    public int ammoCount;

    public BaseAmmoLimitedWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime,
            int ammoCount) {
        super(tag, bulletSpec, reloadTime);
        this.ammoCount = ammoCount;
    }
}
