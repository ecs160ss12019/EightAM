package EightAM.asteroids;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.ShotgunWeaponSpec;

class ShotgunWeapon extends AmmoLimitedWeapon {
    int shotCount;
    Pair<Float, Float> spread;

    ShotgunWeapon(ShotgunWeaponSpec spec) {
        super(spec);
        this.shotCount = spec.shotCount;
        this.spread = spec.spread;
    }

    ShotgunWeapon(ShotgunWeapon weapon) {
        super(weapon);
        this.shotCount = weapon.shotCount;
        this.spread = weapon.spread;
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        super.fire(shooter);
        Collection<GameObject> bullets = new ArrayList<>();
        for (int i = 0; i < shotCount; i++) {
            float dTheta = GameRandom.randomFloat(spread.first, spread.second);
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
        return new ShotgunWeapon(this);
    }
}
