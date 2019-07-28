package EightAM.asteroids;

import java.util.Map;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.specs.BaseBulletSpec;

public class BulletGenerator extends GameObjectGenerator {
    static BulletGenerator instance;

    private BulletGenerator() {}

    static void init() { if (instance == null) instance = new BulletGenerator(); }

    static BulletGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    /**
     * Makes a new basic bullet and puts it into objectMap.
     * @param objectMap the map of object IDs and object instances from GameModel
     * @param shooter the one who is shooting the bullet
     */
    //TODO: Implement prepareSpec;
    public void createBullet(Map<ObjectID, GameObject> objectMap, Shooter shooter) {
        //BaseBulletSpec spec = prepareSpec(shooter);

        //GameObject bullet = BaseFactory.getInstance().create(spec);
        //objectMap.put(bullet.getID(), bullet);
    }

    /**
     * Modifies the pos and orientation inside a spec.
     * @param shooter the one shooting the bullet
     * @return a basic bullet spec
     */
    //TODO: Implement Bullet spec in bullet
    /*
    private BaseBulletSpec prepareSpec(Shooter shooter) {
        // prepare the bullet spec
        BaseBulletSpec spec = shooter.getBulletSpec();
        spec.initialPosition = shooter.getShotOrigin();
        spec.initialOrientation = shooter.getShotAngle();

        return spec;
    }
    */
}
