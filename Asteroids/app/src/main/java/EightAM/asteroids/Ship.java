package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

import java.util.Collections;
import java.util.Random;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseShipSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable,
        EventGenerator, Destructable {

    // ---------------Member variables-------------
    Bitmap bitmap;
    private boolean isInvincible;
    private int hitPoints;
    private int invincibilityDuration;
    private int teleportDelay;
    private int teleportCooldown;
    private int shotDelayCounter = 0;
    private int shotDelay;
    private float rotationSpeed;
    private float acceleration;
    private float deceleration;
    private ShotListener shotListener;
    private float dbmRatio;
    private DestructListener destructListener;
    private BaseBulletSpec bulletSpec;
    private EventHandler eventHandler;

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
        this.shotDelay = spec.reloadTime;
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
        this.bulletSpec = spec.bulletSpec;
        this.teleportDelay = spec.teleportDelay;
        this.teleportCooldown = spec.teleportCooldown;
        this.teleportCooldownTimer = new Timer(teleportCooldown, 0);
        this.teleportDelayTimer = new Timer(teleportDelay, 0);

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
        this.shotDelay = ship.shotDelay;
        this.rotationSpeed = ship.rotationSpeed;
        this.acceleration = ship.acceleration;
        this.deceleration = ship.deceleration;
        this.bulletSpec = ship.bulletSpec;
        this.teleportDelay = ship.teleportDelay;
        this.teleportCooldown = ship.teleportCooldown;
        this.teleportCooldownTimer = new Timer(teleportCooldown, 0);
        this.teleportDelayTimer = new Timer(teleportDelay, 0);

        this.invDurationTimer = new Timer(invincibilityDuration, 0);
    }


    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        if (isInvincible && invDurationTimer.update(timeInMillisecond)) {
            isInvincible = false;
            paint.setAlpha(255);
        }

        teleportAbility(timeInMillisecond);

        if (shotDelayCounter > 0) shotDelayCounter -= timeInMillisecond;
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
        } else {
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
            shotDelayCounter = shotDelay;
        }

        if (i.DOWN) {
            teleporting = true;
        }
    }

    public boolean canShoot() {
        return shotDelayCounter == 0;
    }

    void drawInvincible(Canvas canvas, Matrix matrix) {
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
            drawInvincible(canvas, matrix);
        }
        canvas.drawBitmap(bitmap, matrix, paint);

    }

    @Override
    GameObject makeCopy() {
        return new Ship(this);
    }

    @Override
    public void shoot() {
        shotListener.onShotFired(this);
    }

    @Override
    public BaseBulletSpec getBulletSpec() {
        return bulletSpec;
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
        destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
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
