package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Log;
import android.util.Pair;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.KamikazeAlienSpec;

class KamikazeAlien extends AbstractAlien implements Shooter, AudioGenerator {

    ShotListener shotListener;

    Weapon weapon;

    float rotationSpeed;
    float acceleration;
    float deceleration;
    Pair<Float, Float> trackingError;
    float explodeRadius;

    float distanceFromPlayer = -1;

    KamikazeAlien(KamikazeAlienSpec spec) {
        super(spec);
        this.weapon = BaseWeaponFactory.getInstance().createWeapon(
                ((BaseAlienSpec) spec).weaponSpec);
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
        this.trackingError = spec.trackingError;
        this.explodeRadius = spec.explodeRadius;
    }

    KamikazeAlien(KamikazeAlien alien) {
        super(alien);
        this.weapon = (Weapon) alien.weapon.makeCopy();
        this.rotationSpeed = alien.rotationSpeed;
        this.acceleration = alien.acceleration;
        this.deceleration = alien.deceleration;
        this.trackingError = alien.trackingError;
        this.explodeRadius = alien.explodeRadius;
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        weapon.update(timeInMillisecond);
        float angleFromPlayer = getAngleFromPlayer();
        if (Math.abs(angleFromPlayer) > GameRandom.randomFloat(trackingError.first,
                trackingError.second)) {
            if (angleFromPlayer >= 0) {
                rotation.angVel = rotationSpeed;
            } else {
                rotation.angVel = -rotationSpeed;
            }
        } else {
            rotation.angVel = 0;
        }
        vel.accelerate(acceleration, rotation.theta, deceleration);
        if (distanceFromPlayer != -1 && distanceFromPlayer < explodeRadius) {
            this.destruct(null);
        }
    }

    float getAngleFromPlayer() {
        return (float) Math.atan2(lastKnownPlayerPos.y - hitbox.centerY(),
                lastKnownPlayerPos.x - hitbox.centerX()) - rotation.theta;
    }

    // Custom destruct

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        shoot();
        super.destruct(destroyedObject);
    }


    // AIModule Methods

    @Override
    public void processGameState(GameState state) {
        super.processGameState(state);
        distanceFromPlayer = (float) Math.hypot(Math.abs(lastKnownPlayerPos.x - hitbox.centerX()),
                Math.abs(lastKnownPlayerPos.y - hitbox.centerY()));
    }


    // Shooter methods

    @Override
    public void shoot() {
        Log.d("Kamikaze", "tried to fire");
        if (weapon.canFire()) {
            Log.d("Kamikaze", "fired");
            shotListener.onShotFired(this);
        }
    }

    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }

    @Override
    public Point getShotOrigin() {
        return new Point((int) hitbox.centerX(), (int) hitbox.centerY());
    }

    @Override
    public float getShotAngle() {
        return rotation.theta;
    }

    @Override
    public boolean canShoot() {
        return weapon.canFire();
    }

    @Override
    public void linkShotListener(ShotListener listener) {
        this.shotListener = listener;
    }

    // Copyable methods

    @Override
    public Object makeCopy() {
        return new KamikazeAlien(this);
    }

    // Drawable methods

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
        matrix.postRotate((float) Math.toDegrees(rotation.theta),
                this.hitbox.centerX(),
                this.hitbox.centerY());
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        super.registerAudioListener(listener);
        weapon.registerAudioListener(listener);
    }
}
