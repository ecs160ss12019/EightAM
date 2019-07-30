package EightAM.asteroids;

// float random = min + r.nextFloat() * (max - min);
//int randomNum = rand.nextInt((max - min) + 1) + min;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Log;
import android.util.Pair;

import java.util.Random;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BaseBulletSpec;

public abstract class Alien extends GameObject implements Destructable, Collision, EventGenerator,
        Shooter {
    // --------------- Member variables --------------
    Bitmap bitmap;
    float dbmRatio;
    float shotAngle = 0;
    float accuracy;
    int pointValue;
    int hitPoints;
    int reloadTime;

    // listeners
    DestructListener destructListener;
    EventHandler eventHandler;
    BaseBulletSpec bulletSpec;
    ShotListener shotListener;
    float distanceTraveled;

    // movement
    Pair<Integer, Integer> turnDelayRange;
    int turnDelay;
    //private boolean debug = true;

    // shooting
    Pair<Integer, Integer> shotDelayRange;
    private int shotDelayCounter;
    boolean canShoot = false;

    Alien(BaseAlienSpec spec) {
        super(spec);
        // We now know the faction
        this.id = ObjectID.getNewID(Faction.Alien);

        // bitmap spec
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;

        // alien spec
        this.pointValue = spec.pointValue;
        this.hitPoints = spec.hitPoints;
    }

    Alien(Alien alien) {
        super(alien);

        this.id = ObjectID.getNewID(Faction.Alien);

        this.bitmap = alien.bitmap;
        this.dbmRatio = alien.dbmRatio;

        this.pointValue = alien.pointValue;
        this.hitPoints = alien.hitPoints;
    }

    // ---------------Member methods --------------

    /**
     * Sets move behavior, turn timer, and shot delay.
     */
    protected void setUp() {
        setTurnDelay();
        setShotDelay();
    }

    @Override
    protected void update(Point spaceSize, long timeInMillisecond) {
        move(spaceSize, timeInMillisecond);
        updateDistance(timeInMillisecond);


        //Let's Just Straight up not despawn it
        //Player needs to GITGUD all kill the damn thing
        /*
        //if (debug) Log.d("distance", Float.toString(this.distanceTraveled));
        if (reachedMaxRange(spaceSize)) {
            //selfDestruct();
        }
        //if (debug) Log.d("alien", Float.toString(this.hitbox.left));
        */

        // timer stuff
        updateTurnTimer();
    }


    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
//        matrix.postRotate((float) Math.toDegrees(orientation),
//                this.hitbox.centerX(),
//                this.hitbox.centerY());

        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.drawRect(this.hitbox, paint);
    }

    // ------------ BEGIN MOVEMENT METHODS ------------ //

    /**
     * Set random max and min timer for Alien to change directions.
     * max and min are in frames.
     */
    protected void setTurnDelay() {
        Random rand = new Random();
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.turnDelay = rand.nextInt((turnDelayRange.second - turnDelayRange.first) + 1)
                + turnDelayRange.first;

    }

    /**
     * Updates the turn timer and turns the alien when necessary.
     */
    protected void updateTurnTimer() {
        this.turnDelay--;
        if (this.turnDelay <= 0) {
            this.turn();
            this.setTurnDelay();
        }
    }

    /**
     * Calculates how far the alien has traveled.
     * (to be used in a super class to determine when the alien should die.)
     */
    private void updateDistance(long timeInMillisecond) {
        distanceTraveled += (this.vel.x * timeInMillisecond);
    }

    /**
     * Determines if the alien should continue to persist.
     *
     * @return true if the alien has exceeded its maximum range
     */
    @Deprecated
    protected boolean reachedMaxRange(Point spaceSize) {
        return distanceTraveled >= spaceSize.x;
    }

    /**
     * Randomly turns the alien. Never turns the alien straight up or straight down.
     */
    protected void turn() {
        float newAngle;
        do {
            newAngle = GameRandom.randomFloat((float) Math.PI / 4f, -(float) Math.PI / 4f);
            this.vel.resetVelocity(vel.maxSpeed, vel.getAngle() + newAngle, vel.maxSpeed);
        }while(Math.abs(this.vel.y) == this.vel.maxSpeed);
    }

    // ------------ END MOVEMENT METHODS ------------ //

    // ------------ BEGIN SHOOTING METHODS ------------ //

    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay() {
        Random rand = new Random();
        this.reloadTime = rand.nextInt((shotDelayRange.second - shotDelayRange.first) + 1)
                + shotDelayRange.first;
    }

    protected boolean canShoot() {
        return reloadTime <= 0;
    }

    protected void aimAtTarget(Point targetPos) {
        this.canShoot = false;
        float delX = targetPos.x - this.getObjPos().x + GameRandom.randomFloat(accuracy, -accuracy);
        float delY = targetPos.y - this.getObjPos().y + GameRandom.randomFloat(accuracy, -accuracy);
        this.shotAngle = (float) Math.atan2(delY, delX);
    }

    protected void tryShoot(Point targetPos) {
        if (this.shotDelayCounter > 0) {
            this.shotDelayCounter--;
        } else {
            this.shotDelayCounter = this.reloadTime;
            aimAtTarget(targetPos);
            shoot();
            setShotDelay();
        }
    }

    // ------------ END SHOOTING METHODS ------------ //

    // ------------ BEGIN SHOOTER IMPLEMENTION ------------ //
    @Override
    public void shoot() {
        shotListener.onShotFired(this);
    }

    public BaseBulletSpec getBulletSpec() {
        return bulletSpec;
    }

    public Point getShotOrigin() {
        return this.getObjPos();
    }

    public float getShotAngle() {
        return this.shotAngle;
    }

    public void linkShotListener(ShotListener listener) {
        this.shotListener = listener;
    }
    // ------------ END SHOOTER IMPLEMENTION ------------ //

    // ------------ BEGIN COLLISION IMPLEMENTION ------------ //
    /**
     * Collision detection method takes in the hitbox of approaching object, using intersection
     * method to check of collision
     *
     * @param approachingObject the hitbox of approaching object,
     * @return true for collision, otherwise false
     */
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject approachingObject) {
        boolean destroyThis = false;
        if (approachingObject instanceof Bullet) {
            hitPoints -= ((Bullet) approachingObject).damage;
            if (hitPoints <= 0) {
                destroyThis = true;
            }
        } else {
            destroyThis = true;
        }
        if (destroyThis) {
            destruct(new DestroyedObject(pointValue, id, approachingObject.id, this));
        }
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    // ------------ END COLLISION IMPLEMENTION ------------ //

    // ------------ BEGIN DESTRUCTABLE IMPLEMENTION ------------ //

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        eventHandler.processScore(destroyedObject);
        destructListener.onDestruct(this);
    }

    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }

    // ------------- END DESTRUCTABLE IMPLEMENTION ------------ //

    public void selfDestruct() {
        destructListener.onDestruct(this);
    }

    @Override
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }
}
