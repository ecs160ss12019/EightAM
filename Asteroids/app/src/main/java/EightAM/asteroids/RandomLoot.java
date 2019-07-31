package EightAM.asteroids;

import EightAM.asteroids.specs.PowerupSpec;
import EightAM.asteroids.specs.RandomLootSpec;

class RandomLoot extends Loot {
    PowerupSpec powerupSpec;

    public RandomLoot(RandomLootSpec spec) {
        super(spec);
    }

    RandomLoot(RandomLoot loot) {
        super(loot);
    }

    public void setPowerupSpec(PowerupSpec powerupSpec) {
        this.powerupSpec = powerupSpec;
    }

    @Override
    GameObject makeCopy() {
        return new RandomLoot(this);
    }

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return false;
    }

    @Override
    public void onCollide(GameObject gameObject) {

    }

    @Override
    public boolean canCollide() {
        return true;
    }
}
