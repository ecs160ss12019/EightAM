package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.BulletFactory;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BasicBulletSpec;
import EightAM.asteroids.specs.LaserBulletSpec;
import EightAM.asteroids.specs.SlowLongBulletSpec;
import EightAM.asteroids.specs.SmallAlienBulletSpec;

/**
 * The Bullet Factory creates instances of the bullets.
 * Like all generators, the instance of a bullet type is
 * cached in the HashMap.
 * It's shooterID is set within the generator.
 * The specification of the bullet is provided by the shooter
 * as well.
 */
public class BaseBulletFactory implements BulletFactory {
    static BaseBulletFactory instance;
    private Map<BaseBulletSpec, Bullet> prototypes;

    private BaseBulletFactory() {
        prototypes = new HashMap<>();
    }

    static void init() {
        if (instance == null) instance = new BaseBulletFactory();
    }

    static BaseBulletFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Bullet createBullet(BaseBulletSpec spec) {
        Bullet bullet = prototypes.get(spec);
        if (bullet == null) {
            if (spec instanceof BasicBulletSpec || spec instanceof SlowLongBulletSpec ||
                    spec instanceof SmallAlienBulletSpec) {
                bullet = new Bullet(spec);
            } else if (spec instanceof LaserBulletSpec) {
                bullet = new LaserBullet((LaserBulletSpec) spec);
            }
        }
        return (Bullet) bullet.makeCopy();
    }
}
