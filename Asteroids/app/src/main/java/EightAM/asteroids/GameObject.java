package EightAM.asteroids;

import android.graphics.RectF;

abstract class GameObject {
    float velX;
    float velY;
    float posX;
    float posY;
    protected RectF hitbox;   // manipulate shape using set()

    float delX;
    float delY;

    /**
     * Enum to to determine the ID of object during collision detection,
     * as well as misc.
     */
    enum ObjectID {
        SHIP,
        ALIEN,
        ASTEROID,
        PROJECTILE
    }

    ObjectID objectID;

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
     * Move an object according to their velocity
     *
     * If the object hits the edge of space (the screen)
     * wrap around
     *
     * @param spaceWidth - width of space (canvas)
     * @param spaceHeight - height of space (canvas)
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
        if (this.hitbox.left < 0){
            this.hitbox.left += (double) spaceWidth;
        }
        else if (this.hitbox.right > (double) spaceWidth) {
            this.hitbox.right -= (double) spaceWidth;
        }

        if (this.hitbox.top < 0){
            this.hitbox.top += (double) spaceHeight;
        }
        else if (this.hitbox.bottom > (double) spaceHeight) {
            this.hitbox.bottom -= (double) spaceHeight;
        }
    }

    /**
     * Use canvas and paint to draw objects
     */
    abstract protected void draw();
    
    /**
     * Possibly take in 2 RectF objects
     * Check intersection of hitboxes
     */
    protected boolean detectCollisions(RectF approachingObject) {
        return hitbox.intersect(approachingObject);
    }

    /**
     * Set and/or updates hitbox
     */
    abstract protected void setHitBox();
}
