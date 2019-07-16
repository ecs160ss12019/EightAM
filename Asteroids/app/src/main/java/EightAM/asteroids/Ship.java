package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    protected Ship(int screenX, int screenY, Context context) {
        SCREEN_WIDTH = screenX;
        SCREEN_HEIGHT = screenY;

        shipHeight = screenY / 10;
        shipWidth = shipHeight / 2;

        this.vel = new Velocity(0,0);

        this.setHitBox(0,0);

        // Prepare the bitmap
        // Load .png file in res/drawable
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_ship);
    }

    /**
     * Constructor helper sets the position of ship hitbox which would be called when game start.
     */
    protected void setHitBox(float x, float y){
        // create ship in the middle of screen
        float left = (SCREEN_WIDTH/2) - (shipWidth /2);
        float right = (SCREEN_WIDTH/2) + (shipWidth /2);
        float top = (SCREEN_HEIGHT/2) - (shipHeight / 2);
        float bottom = (SCREEN_HEIGHT/2) + (shipHeight /2);

        this.hitbox = new RectF(left, top, right, bottom);
    }

    protected RectF getHitBox() {
        return this.hitbox;
    }

    protected Bitmap getBitmap() {
        return this.bitmap;
    }
}
