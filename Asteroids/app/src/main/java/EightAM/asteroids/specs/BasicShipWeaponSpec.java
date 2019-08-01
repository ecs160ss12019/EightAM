package EightAM.asteroids.specs;

public class BasicShipWeaponSpec extends BaseWeaponSpec {
    public static String tag = "weapon_ship_basic";
    public static int reloadTime = 280;
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();

    public BasicShipWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public BasicShipWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
    }
}
