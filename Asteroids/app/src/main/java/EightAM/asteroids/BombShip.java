package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.specs.BombShipSpec;

class BombShip extends AbstractShip {
    Timer bombDelay;
    boolean bombCharging = false;

    Weapon spiritBomb;

    BombShip(BombShipSpec spec) {
        super(spec);
        this.bombDelay = new Timer(spec.bombChargeTime);
        this.spiritBomb = BaseWeaponFactory.getInstance().createWeapon(spec.spiritBomb);
    }

    BombShip(BombShip ship) {
        super(ship);
        this.bombDelay = new Timer(ship.bombDelay.target);
        this.spiritBomb = (Weapon) ship.spiritBomb.makeCopy();
    }

    @Override
    public void input(InputControl.Input i) {
        super.input(i);

        if (i.DOWN) {
            bombCharging = true;
        }
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        BombAbility(timeInMillisecond);
    }

    void BombAbility(long deltaTime) {
        spiritBomb.update(deltaTime);
        if (bombCharging && spiritBomb.canFire() && bombDelay.update(deltaTime)) {
            shoot();
            bombDelay.reset();
            bombCharging = false;
            paint.setColorFilter(null);
        } else if (bombCharging && !spiritBomb.canFire()) {
            bombCharging = false;
        }
    }

    void drawChargingCannon() {
        if (bombCharging && spiritBomb.canFire() && !bombDelay.reachedTarget) {
            Random r = new Random();
            paint.setColorFilter(
                    new PorterDuffColorFilter(Color.rgb(r.nextInt(256), 0, r.nextInt(256)),
                            PorterDuff.Mode.SRC_IN));
        }
    }

    @Override
    public boolean canShoot() {
        if (bombCharging && spiritBomb.canFire() && bombDelay.reachedTarget) return true;
        return super.canShoot();
    }

    @Override
    public Weapon getWeapon() {
        if (bombCharging && spiritBomb.canFire() && bombDelay.reachedTarget) return spiritBomb;
        return super.getWeapon();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChargingCannon();
    }

    @Override
    public Object makeCopy() {
        return new BombShip(this);
    }

    @Override
    public float getPercentageCharged() {
        return 1 - (float) spiritBomb.reloadTimer.remaining() / spiritBomb.reloadTimer.total();
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        super.registerAudioListener(listener);
        spiritBomb.registerAudioListener(listener);
    }
}
