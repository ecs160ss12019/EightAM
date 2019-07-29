package EightAM.asteroids;

import static EightAM.asteroids.Constants.BULLET_MAX_RANGE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseBulletSpec;

public class Bullet extends GameObject implements Collision, Destructable {
    // set by generator
    private Faction faction;
    private float shooterAngle;

    Bitmap bitmap;
    private float distanceTraveled;
    private DestructListener destructListener;

    //    * OLD CONSTRUCTOR
//     * Constructs projectile, i.e. shoots projectile in the orientation/angle
//     * of the shooter.
//     *
//     * Alien shots are slower than the player's. This is to give the player
//     * time to react.
//     *
//     * @param shooter - denotes if fired from player (true) or alien (false)
//
//    protected Bullet(Shooter shooter) {
//        this.shooterAngle = shooter.getShotAngle();
//        hitbox = new RectF(shooter.getPosX() - 3, shooter.getPosY() - 3, shooter.getPosX() + 3,
//        shooter.getPosY() + 3);
//        this.owner = shooter.getID();
//        distanceTraveled = 0;
//        this.paint = new Paint();
//
//        if (this.owner.getFaction() == Faction.Player) {
//            this.vel = new Velocity(BULLET_SPEED, shooter.getShotAngle());
//        } else {
//            this.vel = new Velocity(BULLET_SPEED / 2, shooter.getShotAngle());
//        }
//    }
    Bullet(BaseBulletSpec spec) {
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        // TODO: draw bullet
        //this.bitmap = BitmapStore.getBitmap(spec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(spec.speed, this.orientation, spec.speed);
    }

    Bullet(Bullet bullet) {
        this.paint = bullet.paint;
        // TODO: draw bullet
        //this.bitmap = bullet.bitmap;
        this.hitbox = new RectF(bullet.hitbox);
        this.orientation = bullet.orientation;
        this.vel = new Velocity(bullet.vel);
    }


    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        rotate();
        move(spaceSize, timeInMillisecond);
        distanceTraveled(timeInMillisecond);
        if (reachedMaxRange()) destruct();
    }

    // IMPLEMENT COLLISION INTERFACE
    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return hitbox.intersect(approachingObject.hitbox);
    }

    public void onCollide(GameObject approachingObject) {
        destruct();
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    // TODO: implement onCollide
    // IMPLEMENT COLLISION INTERFACE

    protected void setFaction(Faction newFaction) {
        faction = newFaction;
    }

    /**
     * Calculates how far the bullet has traveled.
     * (to be used in a super class to determine when the bullet should die.)
     *
     * @param timeInMillisecond current time of the game in ms
     */
    private void distanceTraveled(long timeInMillisecond) {
        distanceTraveled += timeInMillisecond * this.vel.magnitude();
    }

    /**
     * Determines if the bullet should continue to persist.
     *
     * @return true if the bullet has exceeded its maximum range
     */
    protected boolean reachedMaxRange() {
        return distanceTraveled > BULLET_MAX_RANGE;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.hitbox, paint);

        // some math stuff
        /*
        float dX = (float) Math.cos(shooterAngle) * 100;
        float dY = (float) Math.sin(shooterAngle) * 100;

        float endX = hitbox.centerX() - dX;
        float endY = hitbox.centerY() - dY;

        this.paint.setColor(Color.WHITE);
        canvas.drawLine(hitbox.centerX(), hitbox.centerY(), endX, endY, paint);
        */
        this.paint.setColor(Color.WHITE);
        canvas.drawRect(this.hitbox, paint);
        //canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void destruct() {
        destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }
}
