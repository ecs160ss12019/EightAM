package EightAM.asteroids;

import java.util.HashMap;
import java.util.Map;

import EightAM.asteroids.interfaces.ShipFactory;
import EightAM.asteroids.specs.BaseShipSpec;

public class BaseShipFactory implements ShipFactory {

    static BaseShipFactory instance;
    private Map<BaseShipSpec, Ship> prototypes;

    private BaseShipFactory() {prototypes = new HashMap<>();}

    static void init() {
        if (instance == null) instance = new BaseShipFactory();
    }

    @Override
    public Ship createShip(BaseShipSpec spec) {
        Ship ret;
        if (prototypes.containsKey(spec)) {
            ret = new Ship(prototypes.get(spec));
        } else {
            Ship ship = new Ship(spec);
            prototypes.put(spec, ship);
            ret = new Ship(spec);
        }
        // Position (i.e. center of screen) will be set by generator
        return ret;
    }
}
