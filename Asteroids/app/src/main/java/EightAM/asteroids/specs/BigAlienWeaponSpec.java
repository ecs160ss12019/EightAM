package EightAM.asteroids.specs;

public class BigAlienWeaponSpec extends BaseWeaponSpec {
    public static String tag = "weapon_alien_big";
    public static BaseBulletSpec bulletSpec = new SlowLongBulletSpec();
    public static int reloadTime = 3200;

    public BigAlienWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public BigAlienWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
    }
}
