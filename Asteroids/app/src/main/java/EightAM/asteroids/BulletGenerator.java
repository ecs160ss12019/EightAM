package EightAM.asteroids;

import android.graphics.Point;
import android.util.Log;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;

public class BulletGenerator extends CollidableObjectGenerator {
    static BulletGenerator instance;

    private BulletGenerator() {
    }

    static void init() {
        if (instance == null) instance = new BulletGenerator();
    }

    static BulletGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    private void debug(Bullet bullet) {
        if (bullet instanceof GameObject) Log.d("Bullet", "created");
        if (bullet == null)Log.d("Bullet", "null");
        Log.d("Bullet", "Speed: "+ bullet.vel.maxSpeed);
        Log.d("Bullet", "Magnitude: "+ bullet.vel.magnitude());
    }

    /**
     * Makes a new basic bullet and puts it into objectMap.
     */
    //TODO: Implement prepareSpec;
    public void createBullet(GameModel model, Shooter shooter) {
        BaseBulletSpec spec = shooter.getBulletSpec();
        GameObject bullet = BaseFactory.getInstance().create(spec);
        positionBullet(shooter, bullet);
        ((Bullet) bullet).registerDestructListener(model);
        debug((Bullet) bullet);
        addToMap(bullet, model);
    }

    /**
     * Modifies the pos and orientation inside a spec.
     * @param shooter the one shooting the bullet
     * @return a basic bullet spec
     */
    //TODO: Implement Bullet spec in bullet
    private void positionBullet(Shooter shooter, GameObject bullet) {
        Point origin = shooter.getShotOrigin();
        bullet.id = ObjectID.getNewID(shooter.getID().getFaction());
        bullet.hitbox.offsetTo(origin.x, origin.y);
        bullet.orientation = shooter.getShotAngle();
        bullet.vel.resetVelocity(bullet.vel.maxSpeed, shooter.getShotAngle(), bullet.vel.maxSpeed);
    }
}
