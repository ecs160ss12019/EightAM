package EightAM.asteroids;

import android.graphics.RectF;

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

    // This is the constructor method.
    // It is called by the code:
    // mShip = new Ship(mScreenX);
    // in the AsteroidsGame class
    Ship(int screenX) {
        // Make the ball square and 1% of screen width
        // of the screen width
        mShipWidth = screenX / 100;
        mShipHeight = screenX / 100;

        // mRest holds the position of the Ship
        // Initialize the RectF with 0, 0, 0, 0
        // We do it here because we only want to
        // do it once.
        // We will initialize the detail
        // at the start of each game
        mRect = new RectF();

        //spawn(x, y);
    }

    // Reposition the ship at the start of every game.
    // The int variables x and y are passed in from the
    // PongGame class and will hold the horizontal and
    // vertical resolution of the screen.
    void reset(int x, int y){
        // Initialise the four points of
        // the rectangle which defines the Ships
        mRect.left = x / 2;
        mRect.top = 0;
        mRect.right = x / 2 + mShipWidth;
        mRect.bottom = mShipHeight;

        // How fast will the ship travel
        // You could vary this to suit
        // You could even increase it as the game progresses
        // to make it harder
        mYVelocity = -(y / 3);
        mXVelocity = (y / 3);
    }

    protected void draw() {

    }
    protected void move() {

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

    // Two helps:
    // When the PongGame class detects a
    // collision on either the top or bottom
    // of the screen it can simply call
    // reverseYVelocity and the ball will begin
    // heading in the opposite direction, the
    // next time the update method is called.
    // Similarly, reverseXVelocity switches
    // direction in the horizontal when either
    // the left or right sides are collided with.
    // Reverse the vertical direction of travel
    void reverseYVelocity(){
        mYVelocity = -mYVelocity;
    }
    // Reverse the horizontal direction of travel
    void reverseXVelocity(){
        mXVelocity = -mXVelocity;
    }

    // Wrap around the ship movement based on
    // whether it hits the left or right-hand side
    void batBounce(RectF batPosition){
        // Detect centre of bat
//        float batCenter = batPosition.left +
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
    }
}
