package EightAM.asteroids.specs;

public abstract class BaseWeaponSpec {
    public BaseBulletSpec bulletSpec;
    public int reloadTime;
    public String tag;

    public BaseWeaponSpec(String tag, BaseBulletSpec bulletSpec, int reloadTime) {
        this.tag = tag;
        this.bulletSpec = bulletSpec;
        this.reloadTime = reloadTime;
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseWeaponSpec)) return false;
        return tag.equals(((BaseWeaponSpec) obj).tag);
    }
}
