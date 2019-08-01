package EightAM.asteroids;

import EightAM.asteroids.specs.SmallAlienSpec;

public class SmallAlien extends Alien {

    public SmallAlien(SmallAlienSpec spec) {
        super(spec);
        this.turnDelayRange = spec.turnDelayRange;
        this.shotDelayRange = spec.shotDelayRange;
        this.accuracy = SmallAlienSpec.accuracy;
        setUp();
    }

    public SmallAlien(SmallAlien alien) {
        super(alien);
        this.turnDelayRange = alien.turnDelayRange;
        this.shotDelayRange = alien.shotDelayRange;
        this.accuracy = alien.accuracy;
        setUp();
    }

    @Override
    public Object makeCopy() {
        return new SmallAlien(this);
    }
}
