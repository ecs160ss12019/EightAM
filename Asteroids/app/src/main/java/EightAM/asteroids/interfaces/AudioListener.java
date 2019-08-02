package EightAM.asteroids.interfaces;

public interface AudioListener {
    void onAlienWave();
    void onAsteroidWave();
    void onAlienBoss();
    void onMusic();
    void offMusic();

    void sendSoundCommand(int resID);
    void sendMusicCommand(boolean startOver, boolean pause, boolean resume);
}
