package EightAM.asteroids;

import android.graphics.RectF;
import java.lang.Math;

class Projectile extends GameObject {

    private int bulletSpeed;

    Owner owner;

    enum Owner {
        SHIP,
        ALIEN
    }

    /**
     * Constructs projectile, i.e. shoots projectile in the orientation/angle
     * of the shooter.
     *
     * Alien shots are slower than the player's. This is to give the player
     * time to react.
     *
     * @param friendly - denotes if fired from player (true) or alien (false)
     * @param x - horizontal position of shooter
     * @param y - vertical position of shooter
     * @param orientation - angle/ orientation of the shooter
     */
    protected Projectile(boolean friendly, float x, float y, double orientation){
        this.posX = x;
        this.posY = y;
        if (friendly){
            this.owner = Owner.SHIP;
            this.velX = bulletSpeed * Math.cos(orientation);
            this.velY = bulletSpeed * Math.sin(orientation);
        }
        else {
            this.owner = Owner.ALIEN;
            this.velX = (bulletSpeed / 2) * Math.cos(orientation);
            this.velY = (bulletSpeed / 2) * Math.sin(orientation);
        }
    }
    protected void draw(){
        //TODO: Draw on canvas dependent on rockSize
    }

    protected void collision() {

    }
}
