package EightAM.asteroids.specs;

import EightAM.asteroids.R;

public class LaserCannonSpec extends BaseWeaponSpec {
    public static String tag = "laser_cannon";
    public static BaseBulletSpec bulletSpec = new LaserCannonBulletSpec();
    public static int reloadTime = 4000;
    public static int _bulletsPerStream = 50;
    public static int _streamLength = 1000;
    public int bulletPerStream;
    public int streamLength;

    public int shoot = R.raw.laser_cannon;

    public LaserCannonSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime,
            int bulletPerStream, int streamLength) {
        super(tag, bulletSpec, reloadTime);
        this.bulletPerStream = bulletPerStream;
        this.streamLength = streamLength;
    }

    public LaserCannonSpec() {
        this(tag, bulletSpec, reloadTime, _bulletsPerStream, _streamLength);
        setShootID(shoot);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
