package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Collections;
import java.util.Random;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.LimitedWeapon;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseShipSpec;
import EightAM.asteroids.specs.TeleportShipSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable,
        EventGenerator, Destructable, AudioGenerator {

    // ---------------Member variables-------------
    Bitmap bitmap;
    private boolean isInvincible;
    private int hitPoints;
    private int invincibilityDuration;
    private float rotationSpeed;
    private float acceleration;
    private float deceleration;
    private ShotListener shotListener;
    private float dbmRatio;

    Weapon weapon;
    Weapon primaryWeapon;
    Weapon secondaryWeapon;

    Timer invDurationTimer;

    private Timer teleportCooldownTimer;
    private Timer teleportDelayTimer;
    private boolean teleporting = false;

    // sound ids
    public int explosionID;
    public int teleportID;
    private AudioListener audioListener;

    /**
     * Main constructor that loads in the spec. Only ever used once
     * per spec.
     */
    Ship(BaseShipSpec spec) {
        super(spec);
        //General
        bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;

        //Ship specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.invincibilityDuration = spec.invincibilityDuration;
        this.isInvincible = true;
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
//        this.primaryWeapon = BaseWeaponFactory.getInstance().createWeapon(new LaserWeaponSpec());
        this.secondaryWeapon = BaseWeaponFactory.getInstance().createWeapon(spec.weaponSpec);
        this.teleportCooldownTimer = new Timer(spec.teleportCooldown, 0);
        this.teleportDelayTimer = new Timer(spec.teleportDelay, 0);

        this.invDurationTimer = new Timer(invincibilityDuration, 0);

        this.explosionID = spec.explosion;
        this.teleportID = ((TeleportShipSpec) spec).teleport;
    }

    /**
     * The copy constructor, copies attributes from a Ship prototype.
     */
    Ship(Ship ship) {
        super(ship);
        //General
        this.bitmap = ship.bitmap;
        this.dbmRatio = ship.dbmRatio;

        //Ship specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.invincibilityDuration = ship.invincibilityDuration;
        this.isInvincible = true;
        this.rotationSpeed = ship.rotationSpeed;
        this.acceleration = ship.acceleration;
        this.deceleration = ship.deceleration;
        if (ship.primaryWeapon != null) {
            this.primaryWeapon = (Weapon) ship.primaryWeapon.makeCopy();
        }
        // secondary weapon should always be non null
        this.secondaryWeapon = (Weapon) ship.secondaryWeapon.makeCopy();
        this.teleportCooldownTimer = new Timer(ship.teleportCooldownTimer);
        this.teleportDelayTimer = new Timer(ship.teleportDelayTimer);

        this.invDurationTimer = new Timer(invincibilityDuration, 0);

        this.explosionID = ship.explosionID;
        this.teleportID = ship.teleportID;
    }


    /**
     * Updates the position of the ship based on speed (a function of time).
     * and decrements the object specific counters
     */
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
        teleportAbility(timeInMillisecond);

    }

    /**
     * Decrements counters for limited weapons. If special weapon has ran out, then
     * the basic weapon is used.
     */
    void updateWeapon(long deltaTime) {
        if (primaryWeapon != null && weapon != primaryWeapon) {
            weapon = primaryWeapon;
        }
        if ((primaryWeapon == null || primaryWeapon instanceof LimitedWeapon
                && ((LimitedWeapon) primaryWeapon).expired()) && weapon != secondaryWeapon) {
            weapon = secondaryWeapon;
        }
        weapon.update(deltaTime);
    }

    /**
     * Teleport enables the HyperSpace ability of the Ship.
     * Though, a delay and cooldown timers are introduced to prevent
     * abuse.
     */
    void teleportAbility(long deltaTime) {
        teleportCooldownTimer.update(deltaTime);
        if (teleportCooldownTimer.reachedTarget) {
            //Log.d(this.getClass().getCanonicalName(), "Teleport Ability available");
        }
        if (teleporting && teleportCooldownTimer.reachedTarget
                && teleportDelayTimer.update(deltaTime)) {
            audioListener.sendSoundCommand(teleportID);
            eventHandler.teleportObjects(Collections.singleton(getID()));
            teleportCooldownTimer.reset();
            teleportDelayTimer.reset();
            teleporting = false;
            paint.setColorFilter(null);
        } else if (teleporting && !teleportCooldownTimer.reachedTarget) {
            //Log.d(this.getClass().getCanonicalName(), "Teleport Ability not yet available");
            teleporting = false;
        }
    }

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

        if (i.DOWN) {
            teleporting = true;
        }
    }

    @Override
    public boolean canShoot() {
        return weapon.canFire();
    }

    void drawInvincible() {
        if (!(invDurationTimer.curr % 10 <= 5)) {
            paint.setAlpha(0);
        } else {
            paint.setAlpha(255);
        }
    }

    void drawTeleporting() {
        Random r = new Random();
        paint.setColorFilter(
                new PorterDuffColorFilter(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)),
                        PorterDuff.Mode.SRC_IN));
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
        matrix.postRotate((float) Math.toDegrees(rotation.theta),
                this.hitbox.centerX(),
                this.hitbox.centerY());

        if (teleporting && teleportCooldownTimer.reachedTarget
                && !teleportDelayTimer.reachedTarget) {
            drawTeleporting();
        }
        if (isInvincible) {
            drawInvincible();
        }
        canvas.drawBitmap(bitmap, matrix, paint);

    }

    @Override
    public Object makeCopy() {
        return new Ship(this);
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
    public void destruct(DestroyedObject destroyedObject) {
        eventHandler.processScore(destroyedObject);
        audioListener.sendSoundCommand(explosionID);
        super.destruct(destroyedObject);
    }

    @Override
    public ObjectID getID() {
        return this.id;
    }

    @Override
    public void linkShotListener(ShotListener listener) {
        this.shotListener = listener;
    }

    @Override
    public boolean isInvulnerable() {
        return isInvincible;
    }

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
}
