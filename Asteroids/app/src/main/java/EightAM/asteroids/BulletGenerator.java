package EightAM.asteroids;

import android.graphics.Point;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.interfaces.Shooter;

public class BulletGenerator extends CollidableObjectGenerator {
    private BulletGenerator() {
    }

    private static void debug(Bullet bullet) {
        if (bullet instanceof GameObject) Log.d("Bullet", "created");
        if (bullet == null)Log.d("Bullet", "null");
        Log.d("Bullet", "Speed: "+ bullet.vel.maxSpeed);
        Log.d("Bullet", "Magnitude: "+ bullet.vel.magnitude());
    }

    /**
     * Makes a new basic bullet and puts it into objectMap.
     */
    //TODO: Implement prepareSpec;
    public static Collection<GameObject> createBullets(Shooter shooter) {
        GameObject bullet = BaseFactory.getInstance().create(shooter.getBulletSpec());
        prepareBullet(shooter, bullet);
        debug((Bullet) bullet);
        return Collections.singleton(bullet);
    }

    /**
     * Modifies the pos and orientation inside a spec.
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
        bullet.rotation.theta = shooter.getShotAngle();
        bullet.vel.resetVelocity(bullet.vel.maxSpeed, bullet.rotation.theta, bullet.vel.maxSpeed);
    }
}
