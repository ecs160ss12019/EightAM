package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_ACCELERATION;
import static EightAM.asteroids.Constants.SHIP_ANGULARVELOCITY;
import static EightAM.asteroids.Constants.SHIP_BITMAP_HITBOX_SCALE;
import static EightAM.asteroids.Constants.SHIP_DECELERATION;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

class Ship extends GameObject {

    // ---------------Member variables-------------

    float shipWidth;
    float shipHeight;

    boolean teleporting = false;

    // ---------------Member methods---------------

    //    static long lastLogMessage = 0;

    /**
     * Constructor constructs a static ship by setting up its size and hitbox.
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    Ship(GameModel gameModel, int screenX, int screenY, Context context) {

        this.refGameModel = gameModel;
        bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_ship);

        shipHeight = bitmap.getHeight() * SHIP_BITMAP_HITBOX_SCALE;
        shipWidth = bitmap.getWidth() * SHIP_BITMAP_HITBOX_SCALE;

        this.vel = new Velocity(0, 0);
        this.orientation = 0;

        // create ship in the middle of screen
        float left = ((float) screenX / 2) - (shipWidth / 2);
        float right = ((float) screenX / 2) + (shipWidth / 2);
        float top = ((float) screenY / 2) - (shipHeight / 2);
        float bottom = ((float) screenY / 2) + (shipHeight / 2);
        this.hitbox = new RectF(left, top, right, bottom);

    }

    /**
     * Constructor helper sets the position of ship hitbox which would be called when game start.
     */
    @Override
    void setHitBox(float x, float y) {
        this.hitbox.offsetTo(x, y);
    }

    @Override
    void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        //        lastLogMessage += timeInMillisecond;
        //        if (lastLogMessage > 5000) {
        //            Log.d("Ship", "x, y, angle: " + this.hitbox.centerX() + " " + this.hitbox.centerY() + " " + this.orientation);
        //            lastLogMessage = 0;
        //        }
        rotate();
        move(spaceWidth, spaceHeight, timeInMillisecond);
    }


    /**
     * Changes ship values with respect to user input
     */
    void input(boolean accelerate, boolean left, boolean right) {
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
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - shipWidth * SHIP_BITMAP_HITBOX_SCALE, hitbox.top - shipHeight * SHIP_BITMAP_HITBOX_SCALE);
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(this.bitmap, matrix, paint);
    }
}
