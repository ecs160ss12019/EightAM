package EightAM.asteroids;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import EightAM.asteroids.interfaces.AudioListener;

public class AudioUtility implements AudioListener {

    // ---------------Member variables-------------

    // Background music
    MediaPlayer music;
    // Sound effects
    private SoundPool sounds;
    private int alien_explosion = -1;
    private int alien_alarm = -1;
    private int asteroid_large_explosion = -1;
    private int asteroid_medium_explosion = -1;
    private int asteroid_small_explosion = -1;
    private int asteroid_wave = -1;
    private int ship_explosion = -1;
    private int ship_shoot = -1;
    private int ship_accelerate = -1;
    private int ship_powerup = -1;
    private int ship_teleport = -1;

    // ---------------Member methods---------------

    AudioUtility(Context context) {
        // Initialize soundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            sounds = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        // Load the sounds from resource file
        alien_explosion = sounds.load(context, R.raw.alien_explosion, 1);
        alien_alarm = sounds.load(context, R.raw.alien_wave, 1);
        asteroid_large_explosion = sounds.load(context, R.raw.asteroid_larger, 1);
        asteroid_medium_explosion = sounds.load(context, R.raw.asteroid_medium, 1);
        asteroid_small_explosion = sounds.load(context, R.raw.asteroid_small, 1);
        asteroid_wave = sounds.load(context, R.raw.sound_alarm, 1);
        ship_accelerate = sounds.load(context, R.raw.ship_accelerate, 1);
        ship_shoot = sounds.load(context, R.raw.ship_shoot, 1);
        ship_explosion = sounds.load(context, R.raw.ship_explosion, 1);
        ship_teleport = sounds.load(context, R.raw.ship_teleport, 1);
        ship_powerup = sounds.load(context, R.raw.ship_powerup, 1);
        music = MediaPlayer.create(context, R.raw.background_music);
    }

    @Override
    public void onAlienExplosion() {

        sounds.play(alien_explosion, 1, 1, 0, 0, 1);
    }
    @Override
    public void onAlienWave() {

        sounds.play(alien_alarm, 1, 1, 0, 0, 1);
    }
    @Override
    public void onAsteroidWave() {

        sounds.play(asteroid_wave, 1, 1, 0, 0, 1);
    }
    @Override
    public void onLargeAsteroidExplosion() {

        sounds.play(asteroid_large_explosion, 1, 1, 0, 0, 1);
    }
    @Override
    public void onMediumAsteroidExplosion() {

        sounds.play(asteroid_medium_explosion, 1, 1, 0, 0, 1);
    }
    @Override
    public void onSmallAsteroidExplosion() {

        sounds.play(asteroid_small_explosion, 1, 1, 0, 0, 1);
    }
    @Override
    public void onShipShoot() {

        sounds.play(ship_shoot, 1, 1, 0, 0, 2);
    }
    @Override
    public void onShipAccelerate() {

        sounds.play(ship_accelerate, 1, 1, 0, 0, 2);
    }
    @Override
    public void onShipExplosion() {

        sounds.play(ship_explosion, 1, 1, 0, 0, 1);
    }
    @Override
    public void onShipPowerup() {

        sounds.play(ship_powerup, 1, 1, 0, 0, 1);
    }
    @Override
    public void onShipTeleport() {

        sounds.play(ship_teleport, 1, 1, 0, 0, 1);
    }

    @Override
    public void playSound(int soundID) {
        sounds.play(soundID, 1, 1, 0, 0, 1);
    }

    /**
     * Background music
     */
    @Override
    public void onMusic() {
        music.setLooping(true);
        music.start();
    }

    /**
     * Background music stops when paused
     */
    @Override
    public void offMusic() {
        music.stop();
    }
}
