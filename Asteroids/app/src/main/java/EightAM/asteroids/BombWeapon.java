package EightAM.asteroids;

import java.util.ArrayList;
import java.util.Collection;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BombWeaponSpec;

class BombWeapon extends AmmoLimitedWeapon {
    int shotCount;

    BombWeapon(BombWeaponSpec spec) {
        super(spec);
        shotCount = spec.shotCount;
    }

    BombWeapon(BombWeapon weapon) {
        super(weapon);
        shotCount = weapon.shotCount;
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        super.fire(shooter);
        Collection<GameObject> bullets = new ArrayList<>();
        for (int i = 0; i < shotCount; i++) {
            float dTheta = GameRandom.randomFloat((float) Math.PI * 2f, 0);
            Bullet bullet = BulletGenerator.createBullet(shooter);
            bullet.rotation.theta = shooter.getShotAngle() + dTheta;
            bullet.vel.resetVelocity(bullet.vel.maxSpeed, shooter.getShotAngle() + dTheta,
                    bullet.vel.maxSpeed);
            bullets.add(bullet);
        }
        ammoCount.update(shotCount);
        reloadTimer.reset();
        return bullets;
    }

    @Override
    public Object makeCopy() {
        return new BombWeapon(this);
    }
}
