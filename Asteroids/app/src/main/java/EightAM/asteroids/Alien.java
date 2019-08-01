package EightAM.asteroids;

// float random = min + r.nextFloat() * (max - min);
//int randomNum = rand.nextInt((max - min) + 1) + min;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseAlienSpec;

public abstract class Alien extends GameObject implements Destructable, Collision, EventGenerator,
        Shooter {
    // --------------- Member variables --------------
    Bitmap bitmap;
    float dbmRatio;
    float shotAngle = 0;
    float accuracy;
    int pointValue;
    int hitPoints;
    Weapon weapon;

    // listeners
    ShotListener shotListener;
    float distanceTraveled;

    // movement
    Pair<Integer, Integer> turnDelayRange;
    //    int turnDelay;
    Timer turnTimer;
    //private boolean debug = true;

    // shooting
    Pair<Integer, Integer> shotDelayRange;

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
        this.weapon = BaseWeaponFactory.getInstance().createWeapon(spec.weaponSpec);

        this.turnTimer = new Timer(0, 0);
    }

    Alien(Alien alien) {
        super(alien);

        this.id = ObjectID.getNewID(Faction.Alien);

        this.bitmap = alien.bitmap;
        this.dbmRatio = alien.dbmRatio;

        this.pointValue = alien.pointValue;
        this.hitPoints = alien.hitPoints;
        this.weapon = (Weapon) alien.weapon.makeCopy();

        this.turnTimer = new Timer(0, 0);
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
    protected void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        updateDistance(timeInMillisecond);
        turnTimer.update(timeInMillisecond);
        if (turnTimer.reachedTarget) {
            this.turn();
            this.setTurnDelay();
        }
        weapon.update(timeInMillisecond);
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
        //canvas.drawRect(this.hitbox, paint);
    }

    // ------------ BEGIN MOVEMENT METHODS ------------ //

    /**
     * Set random max and min timer for Alien to change directions.
     * max and min are in frames.
     */
    protected void setTurnDelay() {
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        this.turnTimer.resetTimer(
                GameRandom.randomInt(turnDelayRange.second, turnDelayRange.first));
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
        newAngle = GameRandom.randomFloat((float) Math.PI / 4f, -(float) Math.PI / 4f);
        this.vel.resetVelocity(vel.maxSpeed, vel.getAngle() + newAngle, vel.maxSpeed);
//        vel.resetVelocity(vel.maxSpeed, vel.getAngle() + (float) Math.PI, vel.maxSpeed);
    }

    // ------------ END MOVEMENT METHODS ------------ //

    // ------------ BEGIN SHOOTING METHODS ------------ //

    /**
     * Sets a shot delay for Alien as to not shoot continuously.
     */
    protected void setShotDelay() {
        weapon.reloadTimer.resetTimer(
                GameRandom.randomInt(shotDelayRange.second, shotDelayRange.first));
    }

    public boolean canShoot() {
        return weapon.canFire();
    }

    protected void aimAtTarget(Point targetPos) {
        float delX = targetPos.x - this.getObjPos().x + GameRandom.randomFloat(accuracy, -accuracy);
        float delY = targetPos.y - this.getObjPos().y + GameRandom.randomFloat(accuracy, -accuracy);
        this.shotAngle = (float) Math.atan2(delY, delX);
    }

    protected void tryShoot(Point targetPos) {
        if (weapon.canFire()) {
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

    @Override
    public Weapon getWeapon() {
        return weapon;
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
        super.destruct(destroyedObject);
    }

    // ------------- END DESTRUCTABLE IMPLEMENTION ------------ //

    public void selfDestruct() {
        destructListener.onDestruct(this);
    }
}
