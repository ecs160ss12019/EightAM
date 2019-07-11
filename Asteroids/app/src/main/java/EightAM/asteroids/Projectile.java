package EightAM.asteroids;

import android.graphics.RectF;
import java.lang.Math;

class Projectile extends GameObject {

    private int bulletSpeed;
    ObjectID owner;

    /**
     * Constructs projectile, i.e. shoots projectile in the orientation/angle
     * of the shooter.
     *
     * Alien shots are slower than the player's. This is to give the player
     * time to react.
     *
     * @param shooter - denotes if fired from player (true) or alien (false)
     * @param x - horizontal position of shooter
     * @param y - vertical position of shooter
     * @param orientation - angle/ orientation of the shooter
     */
    protected Projectile(ObjectID shooter, double x, double y, double orientation){
        this.posX = x;
        this.posY = y;
        this.objectID = ObjectID.PROJECTILE;
        this.owner = shooter;

        if (this.owner == ObjectID.SHIP){
            this.velX = bulletSpeed * Math.cos(orientation);
            this.velY = bulletSpeed * Math.sin(orientation);
        }
        else {
            this.velX = (bulletSpeed / 2) * Math.cos(orientation);
            this.velY = (bulletSpeed / 2) * Math.sin(orientation);
        }
    }
    protected void draw(){
        //TODO: Draw on canvas dependent on rockSize
    }

    protected void collision() {

    }

    protected void update(){

    }

    protected void setHitBox() {

    }
}
