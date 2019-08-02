package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.AlienFactory;
import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BigAlienSpec;
import EightAM.asteroids.specs.KamikazeAlienSpec;
import EightAM.asteroids.specs.SmallAlienSpec;

class BaseAlienFactory implements AlienFactory {
    static BaseAlienFactory instance;
    private Map<BaseAlienSpec, AbstractAlien> prototypes;
//    private boolean debug = true;

    private BaseAlienFactory() {
        prototypes = new HashMap<>();
    }

    static void init() {
        if (instance == null) instance = new BaseAlienFactory();
    }

    static BaseAlienFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public AbstractAlien createAlien(BaseAlienSpec spec) {
        AbstractAlien alien = prototypes.get(spec);
        if (alien == null) {
            if (spec instanceof BigAlienSpec) {
                alien = new BigAlien((BigAlienSpec) spec);
            }
            if (spec instanceof SmallAlienSpec) {
                alien = new SmallAlien((SmallAlienSpec) spec);
            }
            if (spec instanceof KamikazeAlienSpec) {
                alien = new KamikazeAlien((KamikazeAlienSpec) spec);
            }
            prototypes.put(spec, alien);
        }
        return (AbstractAlien) alien.makeCopy();
    }
}
