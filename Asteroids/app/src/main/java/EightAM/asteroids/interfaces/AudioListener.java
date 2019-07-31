package EightAM.asteroids.interfaces;

public interface AudioListener {
    void onAlienExplosion();
    void onAlienWave();

    void onAsteroidWave();

    void onLargeAsteroidExplosion();
    void onMediumAsteroidExplosion();
    void onSmallAsteroidExplosion();
    void onShipShoot();
    void onShipAccelerate();
    void onShipExplosion();
    void onShipPowerup();
    void onShipTeleport();
    void onMusic();
    void offMusic();
}
