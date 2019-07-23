package EightAM.asteroids;

import android.content.Context;
import java.util.Random;
import static EightAM.asteroids.Constants.ALIEN_MAXSPEED;

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
    }




    /**
     * Sets move behavior for this alien. Used in its constructor.
     */
    private void setMoveBehavior() {
        float speed, direction;

        Random rand = new Random();
        speed = 1 + rand.nextFloat() * ((ALIEN_MAXSPEED / 4) - 1);
        direction = 1 + (rand.nextFloat() * 360);
        this.vel = new Velocity(speed, direction);


    }

    /**
     * Set random max and min timer for Alien to change directions.
     */
    protected void setTimer() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.delay = rand.nextInt((3000 - 2000) + 1) + 50;

    }
}
