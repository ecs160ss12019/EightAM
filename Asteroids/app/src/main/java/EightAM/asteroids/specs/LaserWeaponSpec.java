package EightAM.asteroids.specs;

public class LaserWeaponSpec extends BaseTimeLimitedWeaponSpec {
    public static String tag = "weapon_laser";
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();
    public static int reloadTime = 25;
    public static int timeLimit = 20000;

    public LaserWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, long timeLimit) {
        super(tag, bulletSpec, reloadTime, timeLimit);
    }

    public LaserWeaponSpec() {
        this(tag, bulletSpec, reloadTime, timeLimit);
    }
}
