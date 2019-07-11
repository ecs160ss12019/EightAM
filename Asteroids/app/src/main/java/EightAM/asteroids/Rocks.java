package EightAM.asteroids;

import android.graphics.RectF;
import java.util.Random;

class Rocks extends GameObject{

    /*
    Goals:
    - Spawn on space outskirts
    - rocks generates rocks in a collision
    - rocks float in space
    - rocks collide with everting other object except itself
    - speed: constant and dependent on size

    Interact with:
    Space
    GameObject
     */

    /**
     * Enum to denote 3 size types of asteroid rock
     */
    enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    private Size rockSize;
    private int MAXSPEED;

    protected Rocks(int xTotalPix, int yTotalPix) {
        //new Random().nextInt((max-min+1))+min to set bounds
        rockSize = Size.LARGE;
        Random rand = new Random();
        float xRand = (float) rand.nextInt(xTotalPix),
                yRand = (float) rand.nextInt(yTotalPix);
        spawn(xRand, yRand);


    }

    protected Rocks(int currentX, int currentY, Size parentSize) {
        //new Random().nextInt((max-min+1))+min to set bounds
        if (parentSize == Size.LARGE) {
            rockSize = Size.MEDIUM;
        }
        else {
            rockSize = Size.SMALL;
        }
        spawn(currentX, currentY);


    }

    protected void draw(){

    }

    protected void move(/*velocity*/) {

    }

    protected void collision() {

    }
}
