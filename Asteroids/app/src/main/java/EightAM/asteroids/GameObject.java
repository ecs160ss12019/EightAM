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
     * does literally nothing
     * @param x some integer idk
     * @return another integer lol
     */
    protected int foo(int x) {return 0;}

    protected void draw() {
        /*
        Use canvas and paint to draw objects
        */
    }

    protected void move(/*velocity*/) {
        /*
        Takes in velocity, computes new position
        Set new position.
         */
    }

    protected void collision() {
        /*
        Possibly take in 2 RectF objects
        Check intersection of hitboxes
         */
    }



}
