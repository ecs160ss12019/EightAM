package EightAM.asteroids;

import android.content.Context;
import android.util.Log;

import java.util.Random;

import static EightAM.asteroids.Constants.ALIEN_BIG_MAXSPEED;
import static EightAM.asteroids.Constants.BIGALIEN_SHOTDELAY_MAX;
import static EightAM.asteroids.Constants.BIGALIEN_SHOTDELAY_MIN;
import static EightAM.asteroids.Constants.BIGALIEN_TIMER_MAX;
import static EightAM.asteroids.Constants.BIGALIEN_TIMER_MIN;

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

        speed = ALIEN_BIG_MAXSPEED;
        direction = 1 + (rand.nextFloat() * 360);
        this.vel = new Velocity(speed, direction);
    }

    /**
     * Set random max and min timer for Alien to change directions.
     * max and min are in frames.
     */
    protected void setTimer() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.delay = rand.nextInt((BIGALIEN_TIMER_MAX - BIGALIEN_TIMER_MIN) + 1)
                + BIGALIEN_TIMER_MIN;

    }

    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay(){
        Random rand = new Random();
        this.shotDelay = rand.nextInt((BIGALIEN_SHOTDELAY_MAX - BIGALIEN_SHOTDELAY_MIN) + 1)
                + BIGALIEN_SHOTDELAY_MIN;
    }
}
