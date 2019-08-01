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
    private int alien_boss = -1;
    private int asteroid_large_explosion = -1;
    private int asteroid_medium_explosion = -1;
    private int asteroid_small_explosion = -1;
    private int asteroid_wave = -1;
    private int ship_explosion = -1;
    private int ship_shoot = -1;
    private int ship_powerup = -1;
    private int ship_teleport = -1;

    //temp
    private int ship_shoot1 = -1;
    private int ship_shoot2 = -1;

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
        alien_boss = sounds.load(context, R.raw.alien_boss, 1);
        asteroid_large_explosion = sounds.load(context, R.raw.asteroid_larger, 1);
        asteroid_medium_explosion = sounds.load(context, R.raw.asteroid_medium, 1);
        asteroid_small_explosion = sounds.load(context, R.raw.asteroid_small, 1);
        asteroid_wave = sounds.load(context, R.raw.sound_alarm, 1);
        ship_shoot = sounds.load(context, R.raw.ship_shoot, 1);
        ship_explosion = sounds.load(context, R.raw.ship_explosion, 1);
        ship_teleport = sounds.load(context, R.raw.ship_teleport, 1);
        ship_powerup = sounds.load(context, R.raw.ship_powerup, 1);
        music = MediaPlayer.create(context, R.raw.background_music);

        //temp
        ship_shoot1 = sounds.load(context, R.raw.ship_shoot1, 1);
        ship_shoot2 = sounds.load(context, R.raw.ship_shoot2, 1);
    }

    @Override
    public void onAlienExplosion() { playSound(alien_explosion); }
    @Override
    public void onAlienWave() { playSound(alien_alarm); }
    @Override
    public void onAsteroidWave() { playSound(asteroid_wave); }
    @Override
    public void onLargeAsteroidExplosion() { playSound(asteroid_large_explosion); }
    @Override
    public void onMediumAsteroidExplosion() { playSound(asteroid_medium_explosion); }
    @Override
    public void onSmallAsteroidExplosion() { playSound(asteroid_small_explosion); }
    @Override
    public void onShipShoot() {
        playSound(ship_shoot1);

        // temp, testing result: shoot1 is better (kenny)
//        playSound(ship_shoot);
//        playSound(ship_shoot2);
    }
    @Override
    public void onShipExplosion() { playSound(ship_explosion); }
    @Override
    public void onShipPowerup() { playSound(ship_powerup); }
    @Override
    public void onShipTeleport() { playSound(ship_teleport); }
    @Override
    public void onAlienBoss() { playSound(alien_boss); }

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
