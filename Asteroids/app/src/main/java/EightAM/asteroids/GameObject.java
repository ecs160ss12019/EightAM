package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

abstract class GameObject {

    // ---------------Member statics-------------

    static final int ANGULAR_VELOCITY = 1;

    // ---------------Member variables-------------

    Velocity vel;
    RectF hitbox;
    Bitmap bitmap;

    GameModel refGameModel;
    float angle;
    float angularVel = ANGULAR_VELOCITY;
    ObjectID objectID;

    /**
     * Move an object according to their velocity, if the object hits the space edge then wrap
     * around the screen.
     *
     * @param spaceWidth        width of space (canvas)
     * @param spaceHeight       height of space (canvas)
     * @param timeInMillisecond moving distance calculated base on this input time
     */
    protected void move(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)
        // Move the top left corner
        this.hitbox.left += (this.vel.velX() * timeInMillisecond) % (float) spaceWidth;
        this.hitbox.top += (this.vel.velY() * timeInMillisecond) % (float) spaceHeight;

        // Match up the bottom right corner
        // based on the size of the ball
        this.hitbox.right += (this.vel.velX() * timeInMillisecond) % (float) spaceWidth;
        this.hitbox.bottom += (this.vel.velY() * timeInMillisecond) % (float) spaceHeight;
    }

    // ---------------Member methods---------------

    /**
     * Constructor initializes the basic parameters of gameObject
     */
    //    GameObject(int posX, int posY, int width, int height, GameModel space, Bitmap bitmap) {
    //        this.hitbox = new RectF(posX, posY, posX + width, posY + height)
    //    }

    /**
     * Rotate method rotates the object
     */
    protected void rotate() { angle += angularVel; }

    /**
     * Update method means rotating and moving the calling object.
     */
    protected void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        rotate();
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

    /**
     * Set hitbox, object has its own version of hotbox
     */
    abstract protected void setHitBox(float posX, float posY);

    // -----------Abstract member methods-----------

    protected abstract void draw(Canvas canvas, Paint paint);

    // ObjectID as Enum determines the type of object during collision detection.
    enum ObjectID {
        SHIP, ALIEN, ASTEROID, PROJECTILE
    }
}
