package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class BigAlienWeaponSpec extends BaseWeaponSpec implements AudioSpec {
    public static String tag = "weapon_alien_big";
    public static BaseBulletSpec bulletSpec = new SlowLongBulletSpec();
    public static int reloadTime = 3200;

    // sound resIDs
    public int shoot = R.raw.ship_shoot2;

    public BigAlienWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public BigAlienWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
        setShootID(shoot);
    }

}
