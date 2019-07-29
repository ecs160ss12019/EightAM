package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_RESTART_DURATION;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Collection;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.DeathEvent;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseBulletSpec;
import EightAM.asteroids.specs.BaseShipSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable,
        DeathEvent {

    // ---------------Member variables-------------

    Bitmap bitmap;
    boolean teleporting = false;
    boolean isInvincible;
    int invincibilityDuration;
    int shotDelayCounter = 0;
    int shotDelay;
    float rotationSpeed;
    float acceleration;
    float deceleration;
    ShotListener shotListener;
    float bitmapHitboxRatio;
    DestructListener destructListener;
    BaseBulletSpec bulletSpec;

    /*
     * How Dimensions were previously set:
     *
     * hitboxHeight = bitmap.getHeight() * SHIP_BITMAP_HITBOX_SCALE;
     * hitboxWidth = bitmap.getWidth() * SHIP_BITMAP_HITBOX_SCALE;
     *
     * float left = ((float) screenX / 2) - (hitboxWidth / 2);
     * float right = ((float) screenX / 2) + (hitboxWidth / 2);
     * float top = ((float) screenY / 2) - (hitboxHeight / 2);
     * float bottom = ((float) screenY / 2) + (hitboxHeight / 2);
     * this.hitbox = new RectF(left, top, right, bottom);
     */
    Ship(BaseShipSpec spec) {
        //General
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        bitmap = BitmapStore.getInstance().getBitmap(spec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x,
                spec.initialPosition.y,
                spec.dimensions.x + spec.initialPosition.x,
                spec.dimensions.y + spec.initialPosition.y);
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

    /**
     * Collision detection method takes in the hitbox of approaching object, using intersection
     * method to check of collision
     *
     * @param approachingObject the hitbox of approaching object,
     * @return true for collision, otherwise false
     */
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        if (isInvincible) return false;
        return hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject gameObject) {

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
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - hitboxWidth / this.bitmapHitboxRatio,
                hitbox.top - hitboxHeight / this.bitmapHitboxRatio);
        // debug purpose
        this.paint.setColor(Color.GREEN);
        canvas.drawRect(this.hitbox, paint);
        if (!isInvincible) {
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if ((invincibilityDuration < (invincibilityDuration - SHIP_RESTART_DURATION))
                && invincibilityDuration % 2 == 0) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    public float getPosX() {
        return hitbox.centerX();
    }

    public float getPosY() {
        return hitbox.centerY();
    }

    public float getAngle() {
        return orientation;
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
    public Collection<GameObject> createOnDeath() {
        return null;
    }
}
