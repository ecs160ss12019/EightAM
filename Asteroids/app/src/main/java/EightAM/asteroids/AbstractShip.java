package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.LimitedWeapon;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.interfaces.SpecialPower;
import EightAM.asteroids.specs.BaseShipSpec;

public abstract class AbstractShip extends GameObject implements Shooter, Controllable, Collision,
        Invulnerable, AudioGenerator, SpecialPower {
    Bitmap bitmap;
    boolean isInvincible;
    int hitPoints;
    float rotationSpeed;
    float acceleration;
    float deceleration;
    ShotListener shotListener;
    float dbmRatio;

    Weapon weapon;
    Weapon primaryWeapon;
    Weapon secondaryWeapon;

    Timer invDurationTimer;

    int explosionID;
    AudioListener audioListener;

    AbstractShip(BaseShipSpec spec) {
        super(spec);

        //General
        bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;

        //TeleportShip specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.isInvincible = true;
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
        this.secondaryWeapon = BaseWeaponFactory.getInstance().createWeapon(spec.weaponSpec);
        this.invDurationTimer = new Timer(spec.invincibilityDuration, 0);
        this.explosionID = spec.explosion;
    }

    AbstractShip(AbstractShip ship) {
        super(ship);

        //General
        this.bitmap = ship.bitmap;
        this.dbmRatio = ship.dbmRatio;

        //TeleportShip specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.isInvincible = true;
        this.rotationSpeed = ship.rotationSpeed;
        this.acceleration = ship.acceleration;
        this.deceleration = ship.deceleration;
        if (ship.primaryWeapon != null) {
            this.primaryWeapon = (Weapon) ship.primaryWeapon.makeCopy();
        }
        // secondary weapon should always be non null
        this.secondaryWeapon = (Weapon) ship.secondaryWeapon.makeCopy();
        this.invDurationTimer = new Timer(ship.invDurationTimer.target);
        this.explosionID = ship.explosionID;
    }


    /**
     * Changes currPlayerShip values with respect to user input
     */
    @Override
    public void input(InputControl.Input i) {
        if (i.UP) {
            this.vel.accelerate(acceleration, rotation.theta, deceleration);
        } else {
            this.vel.decelerate(deceleration);  // velocity decay
        }

        if (i.LEFT) {
            this.rotation.angVel = -rotationSpeed;
        } else if (i.RIGHT) {
            this.rotation.angVel = rotationSpeed;
        } else {
            this.rotation.angVel = 0;
        }

        if (i.SHOOT) {
            shoot();
        }
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        if (!invDurationTimer.update(timeInMillisecond)) {
            isInvincible = true;
        }
        if (isInvincible && invDurationTimer.reachedTarget) {
            isInvincible = false;
            paint.setAlpha(255);
        }
        updateWeapon(timeInMillisecond);

    }

    /**
     * Decrements counters for limited weapons. If special weapon has ran out, then
     * the basic weapon is used.
     */
    void updateWeapon(long deltaTime) {
        if (weapon == null || weapon instanceof LimitedWeapon && ((LimitedWeapon) weapon).expired()
                || weapon == secondaryWeapon && primaryWeapon != null) {
            weapon = primaryWeapon;
        }
        if (primaryWeapon == null || primaryWeapon instanceof LimitedWeapon
                && ((LimitedWeapon) primaryWeapon).expired()) {
            weapon = secondaryWeapon;
        }
        weapon.update(deltaTime);
    }

    // Collision Methods

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        if (isInvincible) return false;
        return hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject gameObject) {
        boolean destroyThis = false;
        if (gameObject instanceof Bullet) {
            hitPoints -= ((Bullet) gameObject).damage;
            if (hitPoints <= 0) {
                destroyThis = true;
            }
        } else {
            destroyThis = true;
        }
        if (destroyThis) {
            destruct(new DestroyedObject(0, id, gameObject.id, this));
        }
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    // Shooter Methods

    @Override
    public boolean canShoot() {
        return weapon.canFire();
    }

    @Override
    public void shoot() {
        shotListener.onShotFired(this);
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
    public void linkShotListener(ShotListener listener) {
        this.shotListener = listener;
    }

    // Invulnerable Methods

    @Override
    public boolean isInvulnerable() {
        return isInvincible;
    }

    // AudioGenerator Methods

    @Override
    public void registerAudioListener(AudioListener listener) {
        this.audioListener = listener;
        if (weapon != null) {
            weapon.registerAudioListener(listener);
        }
        if (primaryWeapon != null) {
            primaryWeapon.registerAudioListener(listener);
        }
        if (secondaryWeapon != null) {
            secondaryWeapon.registerAudioListener(listener);
        }
    }

    //Drawable Methods

    @Override
    public void draw(Canvas canvas) {
        if (isInvincible) {
            drawInvincible();
        }

        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
        matrix.postRotate((float) Math.toDegrees(rotation.theta),
                this.hitbox.centerX(),
                this.hitbox.centerY());
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    void drawInvincible() {
        if (!(invDurationTimer.curr % 10 <= 5)) {
            paint.setAlpha(0);
        } else {
            paint.setAlpha(255);
        }
    }

    // Destruct Methods
    @Override
    public void destruct(DestroyedObject destroyedObject) {
        audioListener.sendSoundCommand(explosionID);
        super.destruct(destroyedObject);
    }
}
