package EightAM.asteroids;

import android.content.Context;
import android.util.Log;

import java.util.Random;

import static EightAM.asteroids.Constants.ALIEN_BIG_MAXSPEED;

public class BigAlien extends Alien {


    /**
     * Spawns a new alien on the screen at a random y position on either
     * the left or the right of the screen
     * @param xTotalPix total x dimensions of the screen
     * @param yTotalPix total y dimensions of the screen
     * @param context context of the game (passed from game model)
     */
    protected BigAlien(int xTotalPix, int yTotalPix, Context context) {
        // prepare bitmap
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_alien);

        spawn(xTotalPix, yTotalPix);

        // TODO: change behavior
        this.setMoveBehavior();

        // might use later
        this.objectID = ObjectID.ALIEN;
        this.setTimer();
        this.setShotDelay();
    }




    /**
     * Sets move behavior for this alien. Used in its constructor.
     */
    private void setMoveBehavior() {
        float speed, direction;

        Random rand = new Random();
        // TODO: change these #s later

        speed = ALIEN_BIG_MAXSPEED;
        direction = 1 + (rand.nextFloat() * 360);
        this.vel = new Velocity(speed, direction);
    }

    /**
     * Set random max and min timer for Alien to change directions.

     */
    protected void setTimer() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.delay = rand.nextInt((6000 - 4000) + 1) + 50;

    }


    protected void setShotDelay(){
        Random rand = new Random();
        this.shotDelay = rand.nextInt((5000 - 3000) + 1) + 50;
    }
}
