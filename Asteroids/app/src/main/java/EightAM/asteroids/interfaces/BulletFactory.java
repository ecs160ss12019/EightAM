package EightAM.asteroids.interfaces;

import EightAM.asteroids.Bullet;
import EightAM.asteroids.specs.BaseBulletSpec;

public interface BulletFactory {
    Bullet createBullet(BaseBulletSpec spec);
}
