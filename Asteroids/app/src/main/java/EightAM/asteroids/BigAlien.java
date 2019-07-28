package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_BIG_MAXSPEED;

import android.graphics.RectF;
import android.util.Pair;

import java.util.Random;

import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BigAlienSpec;

public class BigAlien extends Alien {

    Pair<Integer, Integer> shotDelayRange;
    Pair<Integer, Integer> turnDelayRange;
    private int turnDelay;
    private int shotDelay;

    BigAlien(BigAlien alien) {
        this.id = ObjectID.getNewID(Faction.Alien);
        this.bitmap = alien.bitmap;
        this.paint = alien.paint;
        this.canShoot = false;
        this.hitbox = new RectF(alien.hitbox);
        this.angularVel = alien.angularVel;
        this.orientation = alien.orientation;
        this.turnDelayRange = alien.turnDelayRange;
        this.vel = new Velocity(alien.vel);
        this.shotDelayRange = alien.shotDelayRange;
    }

    BigAlien(BigAlienSpec spec) {
        this.id = ObjectID.getNewID(Faction.Alien);
        this.bitmap = BitmapStore.getInstance().getBitmap(((BaseAlienSpec) spec).bitMapName);
        this.paint = PaintStore.getInstance().getPaint(((BaseAlienSpec) spec).paintName);
        this.canShoot = false;
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + ((BaseAlienSpec) spec).dimensions.x,
                spec.initialPosition.y + ((BaseAlienSpec) spec).dimensions.y);
        this.angularVel = 0;
        this.orientation = spec.initialOrientation;
        this.turnDelayRange = spec.turnDelayRange;
        this.vel = new Velocity(0, 0, ((BaseAlienSpec) spec).maxSpeed);
        this.shotDelayRange = spec.shotDelayRange;
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
        this.turnDelay = rand.nextInt((turnDelayRange.second - turnDelayRange.first) + 1)
                + turnDelayRange.first;

    }

    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay() {
        Random rand = new Random();
        this.shotDelay = rand.nextInt((shotDelayRange.second - shotDelayRange.first) + 1)
                + shotDelayRange.first;
    }

    @Override
    public boolean canCollide() {
        return true;
    }
}
