package EightAM.asteroids.interfaces;

import EightAM.asteroids.AbstractShip;
import EightAM.asteroids.specs.BaseShipSpec;

public interface ShipFactory {
    AbstractShip createShip(BaseShipSpec spec);
}
