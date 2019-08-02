package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class LaserWeaponSpec extends BaseTimeLimitedWeaponSpec implements AudioSpec {
    public static String tag = "weapon_laser";
    public static BaseBulletSpec bulletSpec = new LaserBulletSpec();
    public static int reloadTime = 20;
    public static int timeLimit = 10000;

    // sound resIDs
    public int shoot = R.raw.retro_blaster_fire;

    public LaserWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, long timeLimit) {
        super(tag, bulletSpec, reloadTime, timeLimit);
    }

    public LaserWeaponSpec() {
        this(tag, bulletSpec, reloadTime, timeLimit);
        setShootID(shoot);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
