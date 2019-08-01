package EightAM.asteroids;

import EightAM.asteroids.specs.BigAlienSpec;

public class BigAlien extends Alien {

    public BigAlien(BigAlienSpec spec) {
        super(spec);
        this.turnDelayRange = spec.turnDelayRange;
        this.shotDelayRange = spec.shotDelayRange;
        this.accuracy = BigAlienSpec.accuracy;
        setUp();
    }

    public BigAlien(BigAlien alien) {
        super(alien);
        this.turnDelayRange = alien.turnDelayRange;
        this.shotDelayRange = alien.shotDelayRange;
        this.accuracy = alien.accuracy;
        setUp();
    }

    @Override
    public void onCollide(GameObject approachingObject) {
        super.onCollide(approachingObject);
    }

    @Override
    public Object makeCopy() {
        return new BigAlien(this);
    }
}
