package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_ACCELERATION;
import static EightAM.asteroids.Constants.SHIP_ANGULARVELOCITY;
import static EightAM.asteroids.Constants.SHIP_BITMAP_HITBOX_SCALE;
import static EightAM.asteroids.Constants.SHIP_DECELERATION;
import static EightAM.asteroids.Constants.SHIP_INVINCIBILITY_DURATION;
import static EightAM.asteroids.Constants.SHIP_RESTART_DURATION;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Collideable;
import EightAM.asteroids.interfaces.Controllable;
import EightAM.asteroids.interfaces.Invulnerable;
import EightAM.asteroids.interfaces.Shootable;

public class Ship extends GameObject implements Shootable, Controllable, Collideable, Invulnerable {

    // ---------------Member variables-------------

    static Bitmap bitmap;
    boolean teleporting = false;
    boolean invincible;
    int invincibilityDuration;
    int shotDelayCounter = 0;
    int shotDelay = 30;

    /**
     * Constructor constructs a static currPlayerShip by setting up its size and hitbox.
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    Ship(GameModel gameModel, int screenX, int screenY, Context context) {
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_ship);
        this.model = gameModel;

        hitboxHeight = bitmap.getHeight() * SHIP_BITMAP_HITBOX_SCALE;
        hitboxWidth = bitmap.getWidth() * SHIP_BITMAP_HITBOX_SCALE;

        this.vel = new Velocity(0, 0);
        this.orientation = 3f / 2 * (float) Math.PI;

        // create currPlayerShip in the middle of screen
        float left = ((float) screenX / 2) - (hitboxWidth / 2);
        float right = ((float) screenX / 2) + (hitboxWidth / 2);
        float top = ((float) screenY / 2) - (hitboxHeight / 2);
        float bottom = ((float) screenY / 2) + (hitboxHeight / 2);
        this.hitbox = new RectF(left, top, right, bottom);


        this.invincibilityDuration = SHIP_INVINCIBILITY_DURATION;
        invincible = true;
        this.paint = new Paint();

    }

    /**
     * Constructor helper sets the position of currPlayerShip hitbox which would be called when game start.
     */
    @Override
    void setHitBox(float x, float y) {
        this.hitbox.offsetTo(x, y);
    }

    @Override
    void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        if (invincibilityDuration > 0) invincibilityDuration--;
        if (invincibilityDuration <= 0) invincible = false;
        rotate();
        move(spaceWidth, spaceHeight, timeInMillisecond);
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

    /**
     * Changes currPlayerShip values with respect to user input
     */
    @Override
    public void input(InputControl.Input i) {
        if (i.UP) {
            this.vel.accelerate(SHIP_ACCELERATION, orientation);
        } else {
            this.vel.decelerate(SHIP_DECELERATION);  // velocity decay
        }

        if (i.LEFT) {
            this.angularVel = -SHIP_ANGULARVELOCITY;
        } else if (i.RIGHT) {
            this.angularVel = SHIP_ANGULARVELOCITY;
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
        } else if ((invincibilityDuration < (SHIP_INVINCIBILITY_DURATION - SHIP_RESTART_DURATION)) && invincibilityDuration % 2 == 0) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }


    }

    public float getPosX() { return hitbox.centerX();}

    public float getPosY() { return hitbox.centerY(); }

    public float getAngle() { return orientation; }

    @Override
    public ObjectID getID() { return this.id;}

    @Override
    public boolean isInvulnerable() {
        return invincible;
    }
}
