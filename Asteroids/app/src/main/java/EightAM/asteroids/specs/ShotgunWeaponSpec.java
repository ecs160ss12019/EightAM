package EightAM.asteroids.specs;

import android.util.Pair;

import EightAM.asteroids.R;

public class ShotgunWeaponSpec extends BaseAmmoLimitedWeaponSpec {
    public static String tag = "weapon_shotgun";
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();
    public static int reloadTime = 500;
    public static int ammoCount = 150;
    public static int _shotCount = 10;
    public int shotCount;
    public Pair<Float, Float> spread = new Pair<>((float) Math.PI / 6, (float) -Math.PI / 6);

    public static int shoot = R.raw.ship_shoot2;

    public ShotgunWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, int ammoCount,
            int shotCount) {
        super(tag, bulletSpec, reloadTime, ammoCount);
        this.shotCount = shotCount;
    }

    public ShotgunWeaponSpec() {
        this(tag, bulletSpec, reloadTime, ammoCount, _shotCount);
        setShootID(shoot);
    }
}
