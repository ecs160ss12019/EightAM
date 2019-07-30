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

    static BaseShipFactory getInstance() {
        if (instance == null) init();
        return instance;
    }

    @Override
    public Ship createShip(BaseShipSpec spec) {
        Ship ship;
        ship = prototypes.get(spec);
        if (ship == null) {
            ship = new Ship(spec);
            prototypes.put(spec, ship);
        }
        return (Ship) ship.makeCopy();
    }
}
