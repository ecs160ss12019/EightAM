package EightAM.asteroids;

import static EightAM.asteroids.Constants.DEF_ANGLE;
import static EightAM.asteroids.Constants.DEF_ANGULAR_VELOCITY;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

abstract class GameObject {

    // ---------------Member variables-------------

    Velocity vel;
    RectF hitbox;
    Bitmap bitmap;

    GameModel refGameModel;
    float angularVel = DEF_ANGULAR_VELOCITY;
    float orientation = DEF_ANGLE;
    ObjectID objectID;

    /**
     * Move an object according to their velocity
     *
     * @param spaceWidth        width of space (canvas)
     * @param spaceHeight       height of space (canvas)
     * @param timeInMillisecond moving distance calculated base on this input time
     */
    void move(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)
        // Move the top left corner
        float dx = vel.x * timeInMillisecond;
        float dy = vel.y * timeInMillisecond;
        hitbox.offset(dx, dy);
        float cx = hitbox.centerX();
        float cy = hitbox.centerY();
        // if the center passes the boundary, wrap around the hitbox
        if (cx > spaceWidth) {
            hitbox.offset(-(float) spaceWidth, 0);
        } else if (cx < 0) {
            hitbox.offset((float) spaceWidth, 0);
        }
        if (cy > spaceHeight) {
            hitbox.offset(0, -(float) spaceHeight);
        } else if (cy < 0) {
            hitbox.offset(0, (float) spaceHeight);
        }
    }

    /**
     * Rotate method rotates the object
     */
    void rotate() {
        orientation += angularVel;
        if (orientation > Math.PI) {
            orientation -= 2 * Math.PI;
        }
        if (orientation < -Math.PI) {
            orientation += 2 * Math.PI;
        }
    }

    /**
     * Update method means rotating and moving the calling object.
     */
    void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
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
    boolean detectCollisions(RectF approachingObject) {
        return hitbox.intersect(approachingObject);
    }

    /**
     * Set hitbox, object has its own version of hotbox
     */
    abstract void setHitBox(float posX, float posY);

    // -----------Abstract member methods-----------

    abstract void draw(Canvas canvas, Paint paint);

    // ObjectID as Enum determines the type of object during collision detection.
    enum ObjectID {
        SHIP, ALIEN, ASTEROID, PROJECTILE
    }
}
