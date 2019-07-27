package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_BITMAP_HITBOX_SCALE;
import static EightAM.asteroids.Constants.SHIP_RESTART_DURATION;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseShipSpec;

public class Ship extends GameObject implements Shooter, Controllable, Collision, Invulnerable {

    // ---------------Member variables-------------

    static Bitmap bitmap;
    boolean teleporting = false;
    boolean invincible;
    int invincibilityDuration;
    int shotDelayCounter = 0;
    int shotDelay;
    float rotationSpeed;
    float acceleration;
    float deceleration;
    ShotListener shotListener;

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
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y, spec.dimensions.x, spec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, spec.maxSpeed);

        //Ship specific
        this.id = ObjectID.getNewID(Faction.Player);
        this.invincibilityDuration = spec.invincibilityDuration;
        this.invincible = true;
        this.shotDelay = spec.reloadTime;
        this.rotationSpeed = spec.rotationSpeed;
        this.acceleration = spec.acceleration;
        this.deceleration = spec.deceleration;
    }

    Ship(Ship ship) {
        //General
        this.paint = ship.paint;
        bitmap = bitmap;
        this.hitbox = ship.hitbox;
        this.orientation = ship.orientation;
        this.vel = ship.vel;

        //Ship specific
        this.id = ship.id;
        this.invincibilityDuration = ship.invincibilityDuration;
        this.invincible = true;
        this.shotDelay = ship.shotDelay;
        this.rotationSpeed = ship.rotationSpeed;
        this.acceleration = ship.acceleration;
        this.deceleration = ship.deceleration;
    }

    /**
     * Constructor helper sets the position of currPlayerShip hitbox which would be called when game start.
     */
    @Override
    void setHitBox(float x, float y) {
        this.hitbox.offsetTo(x, y);
    }

    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        if (invincibilityDuration > 0) invincibilityDuration--;
        if (invincibilityDuration <= 0) invincible = false;
        rotate();
        move(spaceSize, timeInMillisecond);
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
        if (invincible) return false;
        return hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public boolean canCollide() {
        return !invincible;
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
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - hitboxWidth * SHIP_BITMAP_HITBOX_SCALE, hitbox.top - hitboxHeight * SHIP_BITMAP_HITBOX_SCALE);
        canvas.drawRect(this.hitbox, paint);
        if (!invincible) {
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if ((invincibilityDuration < (invincibilityDuration - SHIP_RESTART_DURATION)) && invincibilityDuration % 2 == 0) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    public float getPosX() { return hitbox.centerX();}

    public float getPosY() { return hitbox.centerY(); }

    public float getAngle() { return orientation; }

    @Override
    public void shoot() { }

    @Override
    public PointF getShotOrigin() {
        return new PointF(hitbox.centerX(), hitbox.centerY());
    }

    @Override
    public float getShotAngle() {
        return orientation;
    }

    @Override
    public ObjectID getID() { return this.id;}

    @Override
    public void linkShotListener(ShotListener listener) {
        this.shotListener = listener;
    }

    @Override
    public boolean isInvulnerable() {
        return invincible;
    }
}
