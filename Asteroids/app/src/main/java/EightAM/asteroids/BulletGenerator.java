package EightAM.asteroids;

import android.graphics.Point;

import EightAM.asteroids.interfaces.Shooter;

public class BulletGenerator {
    private BulletGenerator() {
    }

    /**
     * Makes a new basic bullet and puts it into objectMap.
     */
    //TODO: Implement prepareSpec;
    public static Bullet createBullet(Shooter shooter) {
        Bullet bullet = (Bullet) BaseFactory.getInstance().create(shooter.getWeapon().bulletSpec);
        prepareBullet(shooter, bullet);
        return bullet;
    }

    /**
     * Modifies the pos and orientation inside a spec.
     *
     * @param shooter the one shooting the bullet
     * @return a basic bullet spec
     */
    //TODO: Implement Bullet spec in bullet
    private static void prepareBullet(Shooter shooter, GameObject bullet) {
        Point origin = shooter.getShotOrigin();
        ((Bullet) bullet).setOwner(shooter.getID());
        bullet.id = ObjectID.getNewID(shooter.getID().getFaction());
        bullet.hitbox.offsetTo(origin.x, origin.y);
        bullet.rotation.theta = shooter.getShotAngle();
        bullet.vel.resetVelocity(bullet.vel.maxSpeed, bullet.rotation.theta, bullet.vel.maxSpeed);
    }
}
