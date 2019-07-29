package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_RESTART_DURATION;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseShipSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable,
        EventGenerator {

    // ---------------Member variables-------------

    Bitmap bitmap;
    private boolean teleporting = false;
    boolean isInvincible;
    int invincibilityDuration;
    private int shotDelayCounter = 0;
    private int shotDelay;
    private float rotationSpeed;
    private float acceleration;
    private float deceleration;
    private ShotListener shotListener;
    private float bitmapHitboxRatio;
    private DestructListener destructListener;
    private BaseBulletSpec bulletSpec;
    private EventHandler eventHandler;

    Ship(BaseShipSpec spec) {
        //General
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        bitmap = BitmapStore.getInstance().getBitmap(spec.bitMapName);

        this.hitbox = new RectF(spec.initialPosition.x - spec.dimensions.x,
                spec.initialPosition.y - spec.dimensions.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);

        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, spec.maxSpeed);
        this.bitmapHitboxRatio = spec.dimensionBitMapRatio;

        //Ship specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.invincibilityDuration = spec.invincibilityDuration;
        this.isInvincible = true;
        this.shotDelay = spec.reloadTime;
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
        this.bulletSpec = spec.bulletSpec;
    }

    Ship(Ship ship) {
        //General
        this.paint = ship.paint;
        this.bitmap = ship.bitmap;
        this.bitmapHitboxRatio = ship.bitmapHitboxRatio;

        this.hitbox = new RectF(ship.hitbox);
        this.orientation = ship.orientation;
        this.vel = new Velocity(ship.vel);

        //Ship specific
        this.id = ship.id;
        this.invincibilityDuration = ship.invincibilityDuration;
        this.isInvincible = true;
        this.shotDelay = ship.shotDelay;
        this.rotationSpeed = ship.rotationSpeed;
        this.acceleration = ship.acceleration;
        this.deceleration = ship.deceleration;
        this.bulletSpec = ship.bulletSpec;
    }


    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        if (invincibilityDuration > 0) invincibilityDuration--;
        if (invincibilityDuration <= 0) isInvincible = false;
        super.update(spaceSize, timeInMillisecond);
        if (shotDelayCounter > 0) shotDelayCounter--;
    }

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        if (isInvincible) return false;
        return hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject gameObject) {
        destruct();
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
            this.vel.accelerate(acceleration, orientation);
        } else {
            this.vel.decelerate(deceleration);  // velocity decay
        }

        if (i.LEFT) {
            this.angularVel = -rotationSpeed;
        } else if (i.RIGHT) {
            this.angularVel = rotationSpeed;
        } else {
            this.angularVel = 0;
        }

        if (i.SHOOT) {
            shoot();
            shotDelayCounter = shotDelay;
        }

        if (i.DOWN) {
            teleporting = true;
        }
    }

    boolean canShoot() {
        return shotDelayCounter == 0;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() -(float)(bitmap.getWidth()/2),
                this.hitbox.centerY() - (float)(bitmap.getHeight()/2));
        matrix.postRotate((float) Math.toDegrees(orientation),
                this.hitbox.centerX(),
                this.hitbox.centerY());

        canvas.drawRect(this.hitbox, paint);
        if (!isInvincible) {
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if ((invincibilityDuration < (invincibilityDuration - SHIP_RESTART_DURATION))
                && invincibilityDuration % 2 == 0) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }

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
        return orientation;
    }

    @Override
    public void destruct() {
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
