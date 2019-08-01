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

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.LimitedWeapon;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseShipSpec;
import EightAM.asteroids.specs.LaserWeaponSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable,
        EventGenerator, Destructable {

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
    private DestructListener destructListener;
    private EventHandler eventHandler;

    private Weapon weapon;
    private Weapon primaryWeapon;
    private Weapon secondaryWeapon;

    private Timer invDurationTimer;

    private Timer teleportCooldownTimer;
    private Timer teleportDelayTimer;
    private boolean teleporting = false;

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
        this.primaryWeapon = BaseWeaponFactory.getInstance().createWeapon(new LaserWeaponSpec());
        this.secondaryWeapon = BaseWeaponFactory.getInstance().createWeapon(spec.weaponSpec);
        this.teleportCooldownTimer = new Timer(spec.teleportCooldown, 0);
        this.teleportDelayTimer = new Timer(spec.teleportDelay, 0);

        this.invDurationTimer = new Timer(invincibilityDuration, 0);
    }

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
        this.primaryWeapon = (Weapon) ship.primaryWeapon.makeCopy();
        this.secondaryWeapon = (Weapon) ship.secondaryWeapon.makeCopy();
        this.teleportCooldownTimer = new Timer(ship.teleportCooldownTimer);
        this.teleportDelayTimer = new Timer(ship.teleportDelayTimer);

        this.invDurationTimer = new Timer(invincibilityDuration, 0);
    }


    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        if (isInvincible && invDurationTimer.update(timeInMillisecond)) {
            isInvincible = false;
            paint.setAlpha(255);
        }
        updateWeapon(timeInMillisecond);
        teleportAbility(timeInMillisecond);

    }

    void updateWeapon(long deltaTime) {
        if (weapon == null) {
            weapon = primaryWeapon;
        }
        if (primaryWeapon instanceof LimitedWeapon && ((LimitedWeapon) primaryWeapon).expired()) {
            weapon = secondaryWeapon;
        }
        weapon.update(deltaTime);
    }

    void teleportAbility(long deltaTime) {
        teleportCooldownTimer.update(deltaTime);
        if (teleportCooldownTimer.reachedTarget) {
            //Log.d(this.getClass().getCanonicalName(), "Teleport Ability available");
        }
        if (teleporting && teleportCooldownTimer.reachedTarget
                && teleportDelayTimer.update(deltaTime)) {
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
        } else if (gameObject instanceof Asteroid || gameObject instanceof Alien) {
            destroyThis = true;
        }
        if (destroyThis) {
            destruct(new DestroyedObject(0, id, gameObject.id, this));
        }
    }

    @Override
    public boolean canCollide() {
        return !isInvincible;
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
//            shotDelayCounter = shotDelay;
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
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }
}
