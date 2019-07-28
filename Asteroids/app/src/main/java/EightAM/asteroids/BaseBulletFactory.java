package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.BulletFactory;
import EightAM.asteroids.specs.BaseBulletSpec;

public class BaseBulletFactory implements BulletFactory {
    static BaseBulletFactory instance;
    private Map<BaseBulletSpec, Bullet> prototypes;

    private BaseBulletFactory() { prototypes = new HashMap<>(); }

    static void init() { if (instance == null) instance = new BaseBulletFactory(); }

    static BaseBulletFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Bullet createBullet(BaseBulletSpec spec) {
        Bullet ret;
        if (prototypes.containsKey(spec)) {
            // we have made this kind of bullet before
            // so clone this previously known bullet
            ret = new Bullet(prototypes.get(spec));
        } else {
            // this is a new kind of bullet, so store in prototypes
            Bullet bullet = new Bullet(spec);
            prototypes.put(spec, bullet);
            ret = new Bullet(spec);
        }
        return ret;
    }
}
