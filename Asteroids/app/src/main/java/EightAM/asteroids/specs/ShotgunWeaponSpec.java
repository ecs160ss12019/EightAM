package EightAM.asteroids.specs;

import android.util.Pair;

public class ShotgunWeaponSpec extends BaseAmmoLimitedWeaponSpec {
    public static String tag = "weapon_shotgun";
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();
    public static int reloadTime = 500;
    public static int ammoCount = 500;
    public int shotCount = 10;
    public Pair<Float, Float> spread = new Pair<>((float) Math.PI / 6, (float) -Math.PI / 6);

    public ShotgunWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, int ammoCount) {
        super(tag, bulletSpec, reloadTime, ammoCount);
    }

    public ShotgunWeaponSpec() {
        this(tag, bulletSpec, reloadTime, ammoCount);
    }
}
