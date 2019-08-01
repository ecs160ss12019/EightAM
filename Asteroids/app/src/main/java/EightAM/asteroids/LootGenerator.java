package EightAM.asteroids;

import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.specs.RandomLootSpec;

class LootGenerator {
    private static void randomizePowerup(RandomLootSpec spec, RandomLoot loot) {
        float prob = (float) Math.random();
        float num = 0f;
        for (Powerups e : Powerups.values()) {
            float enumProb = spec.randomPowerupSpec.getProbabilityOfEnum(e);
            if (enumProb + num >= prob) {
                if (e.name().equals("Laser")) {
                    loot.setPowerup(new Powerups.LaserPowerup(e.amount));
                } else if (e.name().equals("Shotgun")) {
                    loot.setPowerup(new Powerups.ShotgunPowerup(e.amount));
                } else if (e.name().equals("Score")) {
                    loot.setPowerup(new Powerups.ScorePowerup(e.amount));
                } else if (e.name().equals("Invincible")) {
                    loot.setPowerup(new Powerups.InvincibilityPowerup(e.amount));
                } else if (e.name().equals("Life")) {
                    loot.setPowerup(new Powerups.LifePowerup(e.amount));
                }
                break;
            } else {
                num += enumProb;
            }
        }
    }

    static RandomLoot createRandomLootRandomly(RectF boundaries, RandomLootSpec spec) {
        RandomLoot loot = (RandomLoot) BaseFactory.getInstance().create(spec);
        loot.hitbox.offsetTo(GameRandom.randomFloat(boundaries.right, boundaries.left),
                GameRandom.randomFloat(boundaries.bottom, boundaries.top));
        randomizePowerup(spec, loot);
        assert loot.powerup != null;
        return loot;
    }

    static RandomLoot createRandomLootAt(Point point, RandomLootSpec spec) {
        RandomLoot loot = (RandomLoot) BaseFactory.getInstance().create(spec);
        loot.hitbox.offsetTo(point.x, point.y);
        randomizePowerup(spec, loot);
        assert loot.powerup != null;
        return loot;
    }
}
