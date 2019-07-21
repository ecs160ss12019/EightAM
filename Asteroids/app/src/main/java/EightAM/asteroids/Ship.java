package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_ACCELERATION;
import static EightAM.asteroids.Constants.SHIP_ANGULARVELOCITY;
import static EightAM.asteroids.Constants.SHIP_BITMAP_HITBOX_SCALE;
import static EightAM.asteroids.Constants.SHIP_DECELERATION;
import static EightAM.asteroids.Constants.SHIP_INVINCIBILITY_DURATION;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

class Ship extends GameObject implements Shooter {

    // ---------------Member variables-------------

    boolean teleporting = false;
    static Bitmap bitmap;
    boolean invincible = true;
    int invincibilityDuration = SHIP_INVINCIBILITY_DURATION;

    /**
     * Constructor constructs a static playerShip by setting up its size and hitbox.
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    Ship(GameModel gameModel, int screenX, int screenY, Context context) {
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_ship);
        this.model = gameModel;

        objectID = ObjectID.SHIP;

        hitboxHeight = bitmap.getHeight() * SHIP_BITMAP_HITBOX_SCALE;
        hitboxWidth = bitmap.getWidth() * SHIP_BITMAP_HITBOX_SCALE;

        this.vel = new Velocity(0, 0);
        this.orientation = 0;

        // create playerShip in the middle of screen
        float left = ((float) screenX / 2) - (hitboxWidth / 2);
        float right = ((float) screenX / 2) + (hitboxWidth / 2);
        float top = ((float) screenY / 2) - (hitboxHeight / 2);
        float bottom = ((float) screenY / 2) + (hitboxHeight / 2);
        this.hitbox = new RectF(left, top, right, bottom);

    }

    /**
     * Constructor helper sets the position of playerShip hitbox which would be called when game start.
     */
    @Override
    void setHitBox(float x, float y) {
        this.hitbox.offsetTo(x, y);
    }

    @Override
    void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        if (invincibilityDuration > 0) invincibilityDuration -= timeInMillisecond;
        if (invincibilityDuration <= 0) invincible = false;
        rotate();
        move(spaceWidth, spaceHeight, timeInMillisecond);
    }

    @Override
    boolean detectCollisions(GameObject approachingObject) {
        if (invincible) return false;
        return super.detectCollisions(approachingObject);
    }

    /**
     * Changes playerShip values with respect to user input
     */
    void input(boolean accelerate, boolean left, boolean right, boolean down, boolean shoot) {
        //        if (lastLogMessage > 5000) {
        //            Log.d("Ship", "up, left, right: " + accelerate + " " + left + " " + right);
        //        }
        if (accelerate) {
            this.vel.accelerate(SHIP_ACCELERATION, orientation);
        } else {
            this.vel.decelerate(SHIP_DECELERATION);  // velocity decay
        }

        if (left) {
            this.angularVel = -SHIP_ANGULARVELOCITY;
        } else if (right) {
            this.angularVel = SHIP_ANGULARVELOCITY;
        } else {
            this.angularVel = 0;
        }

        if (down) {
            teleporting = true;
        }
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - hitboxWidth * SHIP_BITMAP_HITBOX_SCALE, hitbox.top - hitboxHeight * SHIP_BITMAP_HITBOX_SCALE);
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public float getPosX(){
        return hitbox.centerX();
    }

    public float getPosY() { return hitbox.centerY(); }

    public float getAngle() { return orientation; }

    public ObjectID getID() { return objectID; }
}
