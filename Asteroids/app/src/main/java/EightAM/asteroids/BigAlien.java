package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_BIG_MAXSPEED;
import static EightAM.asteroids.Constants.BIGALIEN_SHOTDELAY_MAX;
import static EightAM.asteroids.Constants.BIGALIEN_SHOTDELAY_MIN;
import static EightAM.asteroids.Constants.BIGALIEN_TIMER_MAX;
import static EightAM.asteroids.Constants.BIGALIEN_TIMER_MIN;

import android.graphics.RectF;

import java.util.Random;

public class BigAlien extends Alien {


//    /**
//     * Spawns a new alien on the screen at a random y position on either
//     * the left or the right of the screen
//     *
//     * @param xTotalPix total x dimensions of the screen
//     * @param yTotalPix total y dimensions of the screen
//     * @param context   context of the game (passed from game model)
//     */
//    protected BigAlien(int xTotalPix, int yTotalPix, Context context) {
//        super(xTotalPix, yTotalPix);
//        // prepare bitmap
//        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_alien);
//        spawn(xTotalPix, yTotalPix);
//    }

    BigAlien(BigAlien alien) {
        this.id = ObjectID.getNewID(Faction.Alien);
        this.bitmap = alien.bitmap;
        this.paint = alien.paint;
        this.canShoot = false;
        this.hitbox = new RectF(alien.hitbox);
        this.angularVel = alien.angularVel;
        this.orientation = alien.orientation;
        this.turnDelay = alien.turnDelay;
        this.vel = new Velocity(alien.vel);
        this.shotAngle = 0;
        this.shotDelayCounter = 0;
        this.distanceTraveled = 0;
    }

    /**
     * Sets move behavior for this alien. Used in its constructor.
     */
    protected void setMoveBehavior() {
        float speed, direction;

        Random rand = new Random();

        speed = ALIEN_BIG_MAXSPEED;
        direction = 1 + (rand.nextFloat() * 360);
        this.vel = new Velocity(speed, direction, speed);
    }

    /**
     * Set random max and min timer for Alien to change directions.
     * max and min are in frames.
     */
    protected void setTimer() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.turnDelay = rand.nextInt((BIGALIEN_TIMER_MAX - BIGALIEN_TIMER_MIN) + 1)
                + BIGALIEN_TIMER_MIN;

    }

    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay() {
        Random rand = new Random();
        this.shotDelay = rand.nextInt((BIGALIEN_SHOTDELAY_MAX - BIGALIEN_SHOTDELAY_MIN) + 1)
                + BIGALIEN_SHOTDELAY_MIN;
    }
}
