package EightAM.asteroids;

import EightAM.asteroids.specs.BigAlienSpec;

public class BigAlien extends Alien {

    public BigAlien(BigAlienSpec spec) {
        super(spec);
        this.reloadTime = spec.reloadTime;
        this.turnDelayRange = spec.turnDelayRange;
        this.shotDelayRange = spec.shotDelayRange;
        this.bulletSpec = BigAlienSpec.bulletSpec;
        setUp();
    }

    public BigAlien(BigAlien alien) {
        super(alien);
        this.reloadTime = alien.reloadTime;
        this.turnDelayRange = alien.turnDelayRange;
        this.shotDelayRange = alien.shotDelayRange;
        this.bulletSpec = alien.bulletSpec;
        setUp();
    }

    @Override
    public void onCollide(GameObject approachingObject) {
        super.onCollide(approachingObject);
    }

    @Override
    GameObject makeCopy() {
        return new BigAlien(this);
    }
}
