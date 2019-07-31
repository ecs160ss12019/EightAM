package EightAM.asteroids.interfaces;

public interface AudioListener {
    void onExplosion();
    void onAccelerate();
    void onShoot();

    void playSound(int soundID);
}
