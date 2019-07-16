package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

class Ship extends GameObject {

    // ---------------Member statics---------------

    // This is needed for setHitBox(), otherwise
    // error occurs due to the abstract property of
    // setHitBox() in class GameObject.
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;

    // ---------------Member variables-------------

    float shipWidth;
    float shipHeight;
    float angle;

    boolean teleporting = false;

    // ---------------Member methods---------------

    /**
     * Constructor constructs a static ship by setting up its size and hitbox.
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    protected Ship(GameModel gameModel, int screenX, int screenY, Context context) {

        this.refGameModel = gameModel;

        SCREEN_WIDTH = screenX;
        SCREEN_HEIGHT = screenY;

        shipHeight = screenY / 10;
        shipWidth = shipHeight / 2;

        this.vel = new Velocity(0, 0);
        this.angle = 0;

        // create ship in the middle of screen
        float left = (SCREEN_WIDTH / 2) - (shipWidth / 2);
        float right = (SCREEN_WIDTH / 2) + (shipWidth / 2);
        float top = (SCREEN_HEIGHT / 2) - (shipHeight / 2);
        float bottom = (SCREEN_HEIGHT / 2) + (shipHeight / 2);
        this.hitbox = new RectF(left, top, right, bottom);

        bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_ship);
    }

    /**
     * Constructor helper sets the position of ship hitbox which would be called when game start.
     */
    protected void setHitBox(float x, float y) {
        this.hitbox.offsetTo(x, y);
    }

    protected RectF getHitBox() {
        return this.hitbox;
    }

    protected Bitmap getBitmap() {
        return this.bitmap;
    }

    @Override
    protected void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {

    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.postTranslate((float) -bitmap.getWidth() / 2, (float) -bitmap.getHeight() / 2);
        matrix.postRotate(angle);
        matrix.postTranslate(hitbox.left, hitbox.top);
        canvas.drawBitmap(this.bitmap, matrix, paint);
    }
}
