package EightAM.asteroids;

import java.util.ArrayList;
import java.util.Collection;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.SpiritBombSpec;

class SpiritBomb extends Weapon {
    int shotCount;

    public SpiritBomb(SpiritBombSpec spec) {
        super(spec);
        shotCount = spec.numBullets;
    }

    public SpiritBomb(SpiritBomb weapon) {
        super(weapon);
        shotCount = weapon.shotCount;
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        super.fire(shooter);
        Collection<GameObject> bullets = new ArrayList<>();
        float stepSize = (float) Math.PI * 2 / shotCount;
        for (int i = 0; i < shotCount; i++) {
            Bullet bullet = BulletGenerator.createBullet(shooter);
            bullet.rotation.theta = shooter.getShotAngle() + i * stepSize;
            bullet.vel.resetVelocity(bullet.vel.maxSpeed, bullet.rotation.theta,
                    bullet.vel.maxSpeed);
            bullets.add(bullet);
        }
        reloadTimer.reset();
        return bullets;
    }

    @Override
    public Object makeCopy() {
        return new SpiritBomb(this);
    }
}
