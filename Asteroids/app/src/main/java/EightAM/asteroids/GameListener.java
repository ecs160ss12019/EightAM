package EightAM.asteroids;

interface GameListener {
    void onAlienDestroyed(Alien alien);

    void onAsteroidDestroyed(Asteroid asteroid);

    void onGameEnd(int score);

    // Not yet implemented
    //    void onPowerUp(PowerUp powerUp);

}
