package EightAM.asteroids;

import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.specs.LevelAudioSpec;

class SoundManager implements AudioListener {
    AudioUtility audioUtility;
    LevelAudioSpec spec;

    SoundManager(AudioUtility audioUtility, LevelAudioSpec spec) {
        this.audioUtility = audioUtility;
        this.spec = spec;
    }


    @Override
    public void onAlienWave() {
        audioUtility.playSoundFromResID(spec.alien_wave);
    }

    @Override
    public void onAsteroidWave() {
        audioUtility.playSoundFromResID(spec.asteroid_wave);
    }

    @Override
    public void onAlienBoss() {
        audioUtility.playSoundFromResID(spec.alien_boss);
    }

    @Override
    public void onMusic() {
        audioUtility.onMusic();
    }

    @Override
    public void offMusic() {
        audioUtility.offMusic();
    }

    @Override
    public void sendSoundCommand(int resID) {
        audioUtility.playSoundFromResID(resID);
    }

    @Override
    public void sendMusicCommand(boolean startOver, boolean pause, boolean resume) {
        audioUtility.sendMusicCommand(startOver, pause, resume);
    }
}
