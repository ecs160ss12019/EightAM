package EightAM.asteroids;

import android.graphics.RectF;

abstract class GameObject {
    float velX;
    float velY;
    float posX;
    float posY;
    float delX;
    float delY;

    private RectF hitbox;   // manipulate shape using set()

    protected void spawn(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Use canvas and paint to draw objects
     */
    abstract protected void draw();

    /**
     * Takes in velocity, computes new position
     * Set new position.
     */
    abstract protected void move(/*velocity*/);
    
    /**
     * Possibly take in 2 RectF objects
     * Check intersection of hitboxes
     */
    abstract protected void collision();
}
