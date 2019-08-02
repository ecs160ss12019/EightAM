package EightAM.asteroids.specs;

import java.util.Collection;
import java.util.Collections;

public abstract class BaseWeaponSpec implements AudioSpec {
    public BaseBulletSpec bulletSpec;
    public int reloadTime;
    public String tag;

    public int shootID;

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

    public void setShootID(int shootID) {
        this.shootID = shootID;
    }

    @Override
    public Collection<Integer> getResIDs() {
        return Collections.singleton(shootID);
    }
}
