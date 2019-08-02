package EightAM.asteroids;

import EightAM.asteroids.specs.RandomLootSpec;

class RandomLoot extends Loot {
    Powerups.Powerup powerup;

    RandomLoot(RandomLootSpec spec) {
        super(spec);
    }

    RandomLoot(RandomLoot loot) {
        super(loot);
        this.powerup = loot.powerup;
    }

    public void setPowerup(Powerups.Powerup powerup) {
        this.powerup = powerup;
    }

    @Override
    public Object makeCopy() {
        return new RandomLoot(this);
    }

    @Override
    public void onCollide(GameObject gameObject) {
        if (gameObject instanceof AbstractShip) {
            powerup.applyPowerUp(eventHandler);
            destruct(null);
        }
    }
}
