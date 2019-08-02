package EightAM.asteroids;

import java.util.ArrayList;
import java.util.Collection;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.LaserCannonSpec;

class LaserCannon extends Weapon {
    int bulletPerStream;
    int streamLength;

    public LaserCannon(LaserCannonSpec spec) {
        super(spec);
        this.bulletPerStream = spec.bulletPerStream;
        this.streamLength = spec.streamLength;
    }

    public LaserCannon(LaserCannon weapon) {
        super(weapon);
        this.bulletPerStream = weapon.bulletPerStream;
        this.streamLength = weapon.streamLength;
    }

    @Override
    Collection<GameObject> fire(Shooter shooter) {
        super.fire(shooter);
        float stepsPerBullet = (float) streamLength / (float) bulletPerStream;
        Collection<GameObject> list = new ArrayList<>();
        for (int i = 0; i < bulletPerStream; i++) {
            Bullet bullet = BulletGenerator.createBullet(shooter);
            bullet.hitbox.offset((float) Math.cos(shooter.getShotAngle()) * (i * stepsPerBullet),
                    (float) Math.sin(shooter.getShotAngle()) * i * stepsPerBullet);
            list.add(bullet);
        }
        reloadTimer.reset();
        return list;
    }

    @Override
    public Object makeCopy() {
        return new LaserCannon(this);
    }
}
