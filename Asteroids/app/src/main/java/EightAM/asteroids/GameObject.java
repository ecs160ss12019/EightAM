package EightAM.asteroids;

import android.graphics.RectF;

abstract class GameObject {
    private float velX;
    private float velY;
    private float posX;
    private float posY;
    private float delX;
    private float delY;

    private RectF hitbox;   // manipulate shape using set()

    protected void spawn(float x, float y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Use canvas and paint to draw objects
     */
    protected void draw();

    /**
     * Takes in velocity, computes new position
     * Set new position.
     */
    protected void move(/*velocity*/);
    
    /**
     * Possibly take in 2 RectF objects
     * Check intersection of hitboxes
     */
    protected void collision();
}
