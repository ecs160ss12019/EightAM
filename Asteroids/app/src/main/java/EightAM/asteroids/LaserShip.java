package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.specs.LaserShipSpec;

class LaserShip extends AbstractShip {
    Timer cannonDelay;
    boolean cannonFiring = false;

    Weapon laserCannon;

    LaserShip(LaserShipSpec spec) {
        super(spec);
        this.cannonDelay = new Timer(spec.laserCannonChargeTime);
        this.laserCannon = BaseWeaponFactory.getInstance().createWeapon(spec.laserCannon);
    }

    LaserShip(LaserShip ship) {
        super(ship);
        this.cannonDelay = new Timer(ship.cannonDelay.target);
        this.laserCannon = (Weapon) ship.laserCannon.makeCopy();
    }

    @Override
    public void input(InputControl.Input i) {
        super.input(i);

        if (i.DOWN) {
            cannonFiring = true;
        }
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        laserCannonAbility(timeInMillisecond);
    }

    void laserCannonAbility(long deltaTime) {
        laserCannon.update(deltaTime);
        if (cannonFiring && laserCannon.canFire() && cannonDelay.update(deltaTime)) {
            shoot();
            cannonDelay.reset();
            cannonFiring = false;
            paint.setColorFilter(null);
        } else if (cannonFiring && !laserCannon.canFire()) {
            cannonFiring = false;
        }
    }

    void drawChargingCannon() {
        if (cannonFiring && laserCannon.canFire() && !cannonDelay.reachedTarget) {
            Random r = new Random();
            paint.setColorFilter(
                    new PorterDuffColorFilter(Color.rgb(0, r.nextInt(256), r.nextInt(256)),
                            PorterDuff.Mode.SRC_IN));
        }
    }

    @Override
    public boolean canShoot() {
        if (cannonFiring && laserCannon.canFire() && cannonDelay.reachedTarget) return true;
        return super.canShoot();
    }

    @Override
    public Weapon getWeapon() {
        if (cannonFiring && laserCannon.canFire() && cannonDelay.reachedTarget) return laserCannon;
        return super.getWeapon();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChargingCannon();
    }

    @Override
    public Object makeCopy() {
        return new LaserShip(this);
    }

    @Override
    public float getPercentageCharged() {
        return 1 - (float) laserCannon.reloadTimer.remaining() / laserCannon.reloadTimer.total();
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        super.registerAudioListener(listener);
        laserCannon.registerAudioListener(listener);
    }
}
