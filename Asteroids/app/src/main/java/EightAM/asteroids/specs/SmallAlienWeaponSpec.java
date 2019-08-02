package EightAM.asteroids.specs;

public class SmallAlienWeaponSpec extends BaseWeaponSpec {
    public static String tag = "weapon_alien_small";
    public static BaseBulletSpec bulletSpec = new SmallAlienBulletSpec();
    public static int reloadTime = 1200;

    public SmallAlienWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public SmallAlienWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
    }
}
