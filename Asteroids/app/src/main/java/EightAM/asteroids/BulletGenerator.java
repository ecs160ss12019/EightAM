package EightAM.asteroids;

import android.graphics.Point;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;

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
        GameObject bullet = BaseFactory.getInstance().create(prepareSpec(shooter));
        positionBullet(shooter, bullet);
        debug((Bullet) bullet);
        return Collections.singleton(bullet);
    }

    /**
     * Modifies the pos and orientation inside a spec.
     * @param shooter the one shooting the bullet
     * @return a basic bullet spec
     */
    //TODO: Implement Bullet spec in bullet
    private static void positionBullet(Shooter shooter, GameObject bullet) {
        Point origin = shooter.getShotOrigin();
        bullet.id = ObjectID.getNewID(shooter.getID().getFaction());
        bullet.hitbox.offsetTo(origin.x, origin.y);
        bullet.orientation = shooter.getShotAngle();
        bullet.vel.resetVelocity(bullet.vel.maxSpeed, bullet.orientation, bullet.vel.maxSpeed);
//        Log.d("Bullet", "Speed: "+ bullet.vel.maxSpeed);
//        Log.d("Bullet", "Magnitude: "+ bullet.vel.magnitude());
    }

    /**
     * Modifies the pos and orientation inside a spec.
     *
     * @param shooter the one shooting the bullet
     * @return a basic bullet spec
     */
    //TODO: Implement Bullet spec in bullet
    private static BaseBulletSpec prepareSpec(Shooter shooter) {
        // prepare the bullet spec
        BaseBulletSpec spec = shooter.getBulletSpec();
        spec.setOwner(shooter.getID());
        spec.initialPosition = shooter.getShotOrigin();
        spec.initialOrientation = shooter.getShotAngle();
        return spec;
    }
}
