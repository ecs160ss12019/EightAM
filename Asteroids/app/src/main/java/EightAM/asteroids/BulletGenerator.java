package EightAM.asteroids;

import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;

public class BulletGenerator extends GameObjectGenerator implements ShotListener {
    static BulletGenerator instance;

    private BulletGenerator() {}

    static void init() { if (instance == null) instance = new BulletGenerator(); }

    static BulletGenerator getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public void onShotFired(Shooter shooter) {

    }
}
