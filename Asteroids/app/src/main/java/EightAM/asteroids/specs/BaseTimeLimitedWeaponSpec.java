package EightAM.asteroids.specs;

public abstract class BaseTimeLimitedWeaponSpec extends BaseWeaponSpec {
    public long timeLimit;

    public BaseTimeLimitedWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime,
            long timeLimit) {
        super(tag, bulletSpec, reloadTime);
        this.timeLimit = timeLimit;
    }
}
