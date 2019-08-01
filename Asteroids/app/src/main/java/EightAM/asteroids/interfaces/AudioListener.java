package EightAM.asteroids.interfaces;

public interface AudioListener {
    void onAlienExplosion();
    void onAlienWave();
    void onAsteroidWave();
    void onAlienBoss();
    void onLargeAsteroidExplosion();
    void onMediumAsteroidExplosion();
    void onSmallAsteroidExplosion();
    void onShipShoot();
    void onShipExplosion();
    void onShipPowerup();
    void onShipTeleport();
    void onMusic();
    void offMusic();
    void playSound(int soundID);
}
