package EightAM.asteroids;

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

    // ---------------Member methods---------------

    /**
     * Constructor constructs a static ship by setting up its size and hitbox.
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    protected Ship(int screenX, int screenY) {
        SCREEN_WIDTH = screenX;
        SCREEN_HEIGHT = screenY;

        // Make the ship square and 1% of screen width
        shipWidth = screenX / 100;
        shipHeight = screenX / 100;

        this.velX = 0;
        this.velY = 0;

        this.setHitBox();
    }

    /**
     * Constructor helper sets the position of ship hitbox which would be called when game start.
     */
    protected void setHitBox(){
        // create ship in the middle of screen
        float left = (SCREEN_WIDTH/2) - (shipWidth /2);
        float right = (SCREEN_WIDTH/2) + (shipWidth /2);
        float top = (SCREEN_HEIGHT/2) - (shipHeight / 2);
        float bottom = (SCREEN_HEIGHT/2) + (shipHeight /2);

        this.hitbox = new RectF(left, top, right, bottom);
    }
}
