package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class BasicShipWeaponSpec extends BaseWeaponSpec implements AudioSpec {
    public static String tag = "weapon_ship_basic";
    public static int reloadTime = 280;
    public static BaseBulletSpec bulletSpec = new BasicBulletSpec();

    // sound resIDs
    public static int shoot = R.raw.ship_shoot;

    public BasicShipWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        super(tag, bulletSpec, reloadTime);
    }

    public BasicShipWeaponSpec() {
        this(tag, bulletSpec, reloadTime);
        setShootID(shoot);
    }
}
