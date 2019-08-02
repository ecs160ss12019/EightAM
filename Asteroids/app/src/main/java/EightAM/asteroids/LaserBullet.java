package EightAM.asteroids;

import android.graphics.Canvas;

import EightAM.asteroids.specs.LaserBulletSpec;

class LaserBullet extends Bullet {
    int bulletTrail;

    LaserBullet(LaserBulletSpec spec) {
        super(spec);
        this.bulletTrail = spec.bulletTrail;
    }

    LaserBullet(LaserBullet bullet) {
        super(bullet);
        this.bulletTrail = bullet.bulletTrail;
    }

    @Override
    public void draw(Canvas canvas) {
        int trail = (bulletTrail < distanceTraveled) ? bulletTrail : distanceTraveled;
        if (trail > 0) {
            canvas.drawLine(hitbox.centerX(), hitbox.centerY(),
                    (float) (hitbox.centerX() + trail * Math.cos(vel.getAngle())),
                    (float) (hitbox.centerY() + trail * Math.sin(vel.getAngle())), paint);
        }
    }
}
