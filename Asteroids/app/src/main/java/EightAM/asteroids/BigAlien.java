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
        float speed, direction = 0;
        // prepare bitmap
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_alien);

        spawn(xTotalPix, yTotalPix);

        Random rand = new Random();
        speed = 1 + rand.nextFloat() * ((ALIEN_MAXSPEED / 4) - 1);
        this.vel = new Velocity(speed, direction);

        // might use later
        this.objectID = ObjectID.ALIEN;
    }
}
