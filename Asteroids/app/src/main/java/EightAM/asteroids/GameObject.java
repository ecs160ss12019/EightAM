package EightAM.asteroids;

import android.graphics.RectF;

abstract class GameObject {
    double velX;
    double velY;
    double posX;
    double posY;
    double delX;
    double delY;

    private RectF hitbox;   // manipulate shape using set()


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
    protected void spawn(double x, double y) {
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
    protected void move(int spaceWidth, int spaceHeight){
        // Move the object according to its velocity
        this.posX += this.velX;
        this.posY += this.velY;

        // Wrap around screen
        if (this.posX < 0){
            this.posX += (double) spaceWidth;
        }
        else if (this.posX > (double) spaceWidth) {
            this.posX -= (double) spaceWidth;
        }

        if (this.posY < 0){
            this.posY += (double) spaceHeight;
        }
        else if (this.posY > (double) spaceHeight) {
            this.posY -= (double) spaceHeight;
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
    abstract protected void collision();

    /**
     * Moves and check collision of object
     */
    abstract protected void update();

    /**
     * Set and/or updates hitbox
     */
    abstract protected void setHitBox();
}
