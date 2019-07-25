package EightAM.asteroids.interfaces;

import EightAM.asteroids.Ship;
import EightAM.asteroids.specs.BaseShipSpec;

public interface ShipFactory {
    Ship createShip(BaseShipSpec spec);
}
