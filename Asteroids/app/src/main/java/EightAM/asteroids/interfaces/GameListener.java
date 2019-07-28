package EightAM.asteroids.interfaces;

import EightAM.asteroids.ObjectID;

public interface GameListener {
    void onCollision(ObjectID actorID, ObjectID targetID);

    void onDeath();

    void onGameEnd();

    // Not yet implemented
    //    void onPowerUp(PowerUp powerUp);

}
