package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.ShipFactory;
import EightAM.asteroids.specs.BaseShipSpec;
import EightAM.asteroids.specs.BombShipSpec;
import EightAM.asteroids.specs.LaserShipSpec;
import EightAM.asteroids.specs.TeleportShipSpec;

public class BaseShipFactory implements ShipFactory {

    static BaseShipFactory instance;
    private Map<BaseShipSpec, AbstractShip> prototypes;

    private BaseShipFactory() {
        prototypes = new HashMap<>();
    }

    static void init() {
        if (instance == null) instance = new BaseShipFactory();
    }

    static BaseShipFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public AbstractShip createShip(BaseShipSpec spec) {
        AbstractShip ship;
        ship = prototypes.get(spec);
        if (ship == null) {
            if (spec instanceof TeleportShipSpec) {
                ship = new TeleportShip((TeleportShipSpec) spec);
            } else if (spec instanceof LaserShipSpec) {
                ship = new LaserShip((LaserShipSpec) spec);
            } else if (spec instanceof BombShipSpec) {
                ship = new BombShip((BombShipSpec) spec);
            }
            prototypes.put(spec, ship);
        }
        return (AbstractShip) ship.makeCopy();
    }
}
