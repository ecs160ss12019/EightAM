package EightAM.asteroids;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.LootFactory;
import EightAM.asteroids.specs.BaseLootSpec;
import EightAM.asteroids.specs.RandomLootSpec;

class BaseLootFactory implements LootFactory {
    static BaseLootFactory instance;
    private Map<BaseLootSpec, Loot> prototypes;

    private BaseLootFactory() {
        prototypes = new HashMap<>();
    }

    private static void init() {
        instance = new BaseLootFactory();
    }

    public static BaseLootFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Loot createLoot(BaseLootSpec spec) {
        Loot loot = prototypes.get(spec);
        if (loot == null) {
            if (spec instanceof RandomLootSpec) {
                loot = new RandomLoot((RandomLootSpec) spec);
            } else {
                Log.d(this.getClass().getName(), "Loot creation failed");
            }
        }
        return (Loot) loot.makeCopy();
    }
}
