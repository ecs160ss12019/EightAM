package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class SmallAlienWeaponSpec extends BaseWeaponSpec implements AudioSpec {
    public static String tag = "weapon_alien_small";
    public static BaseBulletSpec bulletSpec = new SmallAlienBulletSpec();
    public static int reloadTime = 1200;

    // sound resIDs
    public int shoot = R.raw.ship_shoot1;

    public SmallAlienWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public SmallAlienWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
        setShootID(shoot);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
