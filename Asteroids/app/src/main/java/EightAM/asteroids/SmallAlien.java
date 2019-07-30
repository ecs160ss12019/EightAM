package EightAM.asteroids;

import EightAM.asteroids.specs.SmallAlienSpec;

public class SmallAlien extends Alien {

    public SmallAlien(SmallAlienSpec spec) {
        super(spec);
        this.reloadTime = spec.reloadTime;
        this.turnDelayRange = spec.turnDelayRange;
        this.shotDelayRange = spec.shotDelayRange;
        this.bulletSpec = SmallAlienSpec.bulletSpec;
        this.accuracy = spec.accuracy;
        setUp();
    }

    public SmallAlien(SmallAlien alien) {
        super(alien);
        this.reloadTime = alien.reloadTime;
        this.turnDelayRange = alien.turnDelayRange;
        this.shotDelayRange = alien.shotDelayRange;
        this.bulletSpec = alien.bulletSpec;
        this.accuracy = alien.accuracy;
        setUp();
    }

    @Override
    GameObject makeCopy() {
        return new SmallAlien(this);
    }
}
