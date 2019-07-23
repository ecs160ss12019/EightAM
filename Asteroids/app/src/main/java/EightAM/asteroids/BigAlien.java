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

        if(this.shouldTurn()) { this.turn(); }
    }

    /**
     * Pretty simple probability function.
     * @return true 50% of times.
     */
    protected boolean shouldTurn() {
        Random rand = new Random();
        float f = rand.nextFloat();
        return (f > 0.01);
    }
}
