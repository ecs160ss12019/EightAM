package EightAM.asteroids;

import android.graphics.RectF;

/**
 * Build Ship object as a rectf object
 */

class Ship extends GameObject {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mShipWidth;
    private float mShipHeight;

    /**
     * Constructor
     *
     * @param screenX: width of screen
     * @param screenY: height of screen
     */
    protected Ship(int screenX, int screenY) {
        // Make the ball square and 1% of screen width
        // of the screen width
        mShipWidth = screenX / 100;
        mShipHeight = screenX / 100;

        // create ship in the middle of screen
        // TODO: locate ship in the center lower half of screen
        float left = (screenX/2) - (mShipWidth /2);
        float right = (screenX/2) + (mShipWidth /2);
        float top = (screenY/2) -(mShipHeight / 2);
        float bottom = (screenY/2) + (mShipHeight/2);

        mRect = new RectF(left, top, right, bottom);
        // How fast will the ship travel
        mYVelocity = -(screenY / 3);
        mXVelocity = (screenY / 3);

        //spawn(x, y);
    }

    /**
     * Called when restart game - reposition ship
     *
    */
    void reset(int screenX, int screenY){
        // Initialise the four points of
        // the rectangle which defines the Ships
        mRect.left = screenX / 2;
        mRect.top = mShipHeight;
        mRect.right = screenX / 2 + mShipWidth;
        mRect.bottom = mShipHeight;
    }

    protected void draw() {

    }
    protected void update() {

    }
    protected void collision() {

    }

    // Update the ship position.
    // Called each frame/loop
    protected void update(long fps) {
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)
        // Move the top left corner
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the ball
        mRect.right = mRect.left + mShipWidth;
        mRect.bottom = mRect.top + mShipHeight;
    }

    protected void setHitBox() {

    }

/*
    // Wrap around the ship movement based on
    // whether it hits the left or right-hand side
    void shipOut(RectF shipPos){
        // Detect centre of bat
//        float shipCenter = .left +
//                (batPosition.width() / 2);

        // detect the centre of the ball
        float ballCenter = mRect.left +
                (mBallWidth / 2);

        // Where on the bat did the ball hit?
        float relativeIntersect = (batCenter - ballCenter);

        // Pick a bounce direction
        if(relativeIntersect < 0){
            // Go right
            mXVelocity = Math.abs(mXVelocity);
            // Math.abs is a static method that
            // strips any negative values from a value.
            // So -1 becomes 1 and 1 stays as 1
        }else{
            // Go left
            mXVelocity = -Math.abs(mXVelocity);
        }
        // Having calculated left or right for
        // horizontal direction simply reverse the
        // vertical direction to go back up
        // the screen
        reverseYVelocity();
    }*/
}
