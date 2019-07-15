package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.RectF;

abstract class GameObject {

    // ---------------Member variables-------------

    float velX;
    float velY;
    float posX;
    float posY;

    RectF hitbox;
    Bitmap bitmap;

    float angle;
    Space refSpace;
    float angularVel;

    // ObjectID as Enum determines the type of object during collision detection.
    enum ObjectID {
        SHIP,
        ALIEN,
        ASTEROID,
        PROJECTILE
    }
    ObjectID objectID;

    // ---------------Member methods---------------

    /**
     * Constructor initializes the basic parameters of gameObject
     */
//    GameObject(int posX, int posY, int width, int height, Space space, Bitmap bitmap) {
//        this.hitbox = new RectF(posX, posY, posX + width, posY + height)
//    }

    /**
     * Sets the position of the object
     *
     * @param x - horizontal spawn position for object
     * @param y - vertical spawn position for object
     */
    protected void spawn(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Move an object according to their velocity, if the object hits the space edge then wrap
     * around the screen.
     *
     * @param spaceWidth width of space (canvas)
     * @param spaceHeight height of space (canvas)
     * @param timeInMillisecond moving distance calculated base on this input time
     */
    protected void move(int spaceWidth, int spaceHeight, long timeInMillisecond){
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)
        // Move the top left corner
        this.hitbox.left = this.hitbox.left + (this.velX * timeInMillisecond);
        this.hitbox.top = this.hitbox.top + (this.velY * timeInMillisecond);

        // Match up the bottom right corner
        // based on the size of the ball
        this.hitbox.right = this.hitbox.right + (this.velX * timeInMillisecond);
        this.hitbox.bottom = this.hitbox.bottom + (this.velY * timeInMillisecond);

        // Move the object according to its velocity
        this.posX += this.velX;
        this.posY += this.velY;

        // Wrap around screen
        // TODO: need to be tested later on by adding unit test
        if (this.hitbox.left < 0){
            this.hitbox.left += (float) spaceWidth;
        }
        else if (this.hitbox.right > (float) spaceWidth) {
            this.hitbox.right -= (float) spaceWidth;
        }

        if (this.hitbox.top < 0){
            this.hitbox.top += (float) spaceHeight;
        }
        else if (this.hitbox.bottom > (float) spaceHeight) {
            this.hitbox.bottom -= (float) spaceHeight;
        }
    }

    /**
     * Rotate method does......
     *
     * @param timeInMillisecond
     */
    protected void rotate(long timeInMillisecond) {
        angle = angle + angularVel * timeInMillisecond;
    }

    /**
     * Update method means rotating and moving the calling object.
     *
     * @param spaceWidth
     * @param spaceHeight
     * @param timeInMillisecond
     */
    protected void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        rotate(timeInMillisecond);
        move(spaceWidth, spaceHeight, timeInMillisecond);
    }

    /**
     * Collision detection method takes in the hitbox of approaching object, using intersection
     * method to check of collision
     *
     * @param approachingObject the hitbox of approaching object,
     * @return true for collision, otherwise false
     */
    protected boolean detectCollisions(RectF approachingObject) {
        return hitbox.intersect(approachingObject);
    }

    // -----------Abstract member methods-----------

    /**
     * Set and/or updates hitbox, object has its own version of hitbox
     */
    abstract protected void setHitBox();
}
