package EightAM.asteroids.specs;

public class LaserWeaponSpec extends BaseTimeLimitedWeaponSpec {
    public static String tag = "weapon_laser";
    public static BaseBulletSpec bulletSpec = new LaserBulletSpec();
    public static int reloadTime = 20;
    public static int timeLimit = 10000;

    public LaserWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, long timeLimit) {
        super(tag, bulletSpec, reloadTime, timeLimit);
    }

    public LaserWeaponSpec() {
        this(tag, bulletSpec, reloadTime, timeLimit);
    }
}
