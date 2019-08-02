package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class BombWeaponSpec extends BaseAmmoLimitedWeaponSpec {
    public static String tag = "weapon_shotgun";
    public static BaseBulletSpec bulletSpec = new ShrapnelBulletSpec();
    public static int reloadTime = 1000;
    public static int ammoCount = 500;
    public static int _shotCount = 25;
    public int shotCount;

    public static int shoot = R.raw.bomb_explosion;

    public BombWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, int ammoCount,
            int shotCount) {
        super(tag, bulletSpec, reloadTime, ammoCount);
        this.shotCount = shotCount;
    }

    public BombWeaponSpec() {
        this(tag, bulletSpec, reloadTime, ammoCount, _shotCount);
        setShootID(shoot);
    }
}
