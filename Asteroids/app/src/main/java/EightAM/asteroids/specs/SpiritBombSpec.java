package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class SpiritBombSpec extends BaseWeaponSpec {
    public static String tag = "spirit_bomb";
    public static int reloadTime = 8000;
    public static BaseBulletSpec bulletSpec = new SlowLongBulletSpec();
    public static int _numBullets = 40;
    public int numBullets;

    public int shoot = R.raw.bomb_explosion;

    public SpiritBombSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime, int numBullets) {
        super(tag, bulletSpec, reloadTime);
        this.numBullets = numBullets;
    }

    public SpiritBombSpec() {
        this(tag, bulletSpec, reloadTime, _numBullets);
        setShootID(shoot);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
