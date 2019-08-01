package EightAM.asteroids.interfaces;

import EightAM.asteroids.Loot;
import EightAM.asteroids.specs.BaseLootSpec;

public interface LootFactory {
    Loot createLoot(BaseLootSpec spec);
}
