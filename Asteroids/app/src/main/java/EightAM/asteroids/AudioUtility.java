package EightAM.asteroids;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseIntArray;

import java.io.IOException;

import EightAM.asteroids.interfaces.AudioListener;

public class AudioUtility implements AudioListener, Runnable {

    // ---------------Member variables-------------

    // Background music
    Handler handler;
    Thread thread;
    MediaPlayer music;
    // Sound effects
    private SoundPool sounds;
    SparseIntArray resIDToSoundID;
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

    AudioUtility() {
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

        resIDToSoundID = new SparseIntArray();

    }

    void loadSound(Context c, int resID) {
        // Load the sounds from resource file
        alien_explosion = sounds.load(c, R.raw.alien_explosion, 1);
        alien_alarm = sounds.load(c, R.raw.alien_wave, 1);
        alien_boss = sounds.load(c, R.raw.alien_boss, 1);
        asteroid_large_explosion = sounds.load(c, R.raw.asteroid_larger, 1);
        asteroid_medium_explosion = sounds.load(c, R.raw.asteroid_medium, 1);
        asteroid_small_explosion = sounds.load(c, R.raw.asteroid_small, 1);
        asteroid_wave = sounds.load(c, R.raw.sound_alarm, 1);
        ship_shoot = sounds.load(c, R.raw.ship_shoot, 1);
        ship_explosion = sounds.load(c, R.raw.ship_explosion, 1);
        ship_teleport = sounds.load(c, R.raw.ship_teleport, 1);
        ship_powerup = sounds.load(c, R.raw.ship_powerup, 1);
        music = MediaPlayer.create(c, R.raw.background_music);

        //temp
        ship_shoot1 = sounds.load(c, R.raw.ship_shoot1, 1);
        ship_shoot2 = sounds.load(c, R.raw.ship_shoot2, 1);
    }

    void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            Looper.prepare();
            handler = new Handler();
            Looper.loop();
        } catch (Throwable t) {
            Log.d(this.getClass().getName(), "Halted due to error");
        }
    }

    boolean isInit() {
        return handler != null;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onAlienExplosion() {
        playSound(alien_explosion);
    }

    @Override
    public void onAlienWave() {
        playSound(alien_alarm);
    }

    @Override
    public void onAsteroidWave() {
        playSound(asteroid_wave);
    }

    @Override
    public void onLargeAsteroidExplosion() {
        playSound(asteroid_large_explosion);
    }

    @Override
    public void onMediumAsteroidExplosion() {
        playSound(asteroid_medium_explosion);
    }

    @Override
    public void onSmallAsteroidExplosion() {
        playSound(asteroid_small_explosion);
    }

    @Override
    public void onShipShoot() {
        playSound(ship_shoot1);

        // temp, testing result: shoot1 is better (kenny)
//        playSound(ship_shoot);
//        playSound(ship_shoot2);
    }

    @Override
    public void onShipExplosion() {
        playSound(ship_explosion);
    }

    @Override
    public void onShipPowerup() {
        playSound(ship_powerup);
    }

    public void onShipTeleport() {
        playSound(ship_teleport);
    }

    @Override
    public void onAlienBoss() {
        playSound(alien_boss);
    }

    @Override
    public void playSound(int soundID) {
        handler.post(new AudioRunnable(soundID, this));
    }

    public void sendPlayCommand(int soundID) {
        sounds.play(soundID, 1, 1, 0, 0, 1);
    }

    @Override
    public void sendMusicCommand(boolean startOver, boolean pause, boolean resume) {
        handler.post(new MusicRunnable(startOver, pause, resume, this));
    }

    /**
     * Background music
     */
    @Override
    public void onMusic() {
        sendMusicCommand(false, false, true);
    }

    /**
     * Background music stops when paused
     */
    @Override
    public void offMusic() {
        sendMusicCommand(false, true, false);
    }

    public void startOverMusic() {
        sendMusicCommand(true, false, false);
    }

    static class AudioRunnable implements Runnable {
        final int soundID;
        final AudioUtility audioUtility;

        public AudioRunnable(final int soundID, final AudioUtility audioUtility) {
            this.soundID = soundID;
            this.audioUtility = audioUtility;
        }

        @Override
        public void run() {
            audioUtility.sendPlayCommand(soundID);
        }
    }

    static class MusicRunnable implements Runnable {
        boolean startOver;
        boolean pause;
        boolean resume;
        AudioUtility audioUtility;

        public MusicRunnable(boolean startOver, boolean pause, boolean resume,
                AudioUtility audioUtility) {
            this.startOver = startOver;
            this.pause = pause;
            this.resume = resume;
            this.audioUtility = audioUtility;
        }

        @Override
        public void run() {
            if (startOver) {
                audioUtility.music.stop();
                try {
                    audioUtility.music.prepare();
                } catch (IOException e) {
                    Log.d(AudioUtility.class.getName(), "failed to restart music");
                }
                audioUtility.music.setLooping(true);
                audioUtility.music.seekTo(0);
                audioUtility.music.start();
            } else if (pause) {
                if (audioUtility.music.isPlaying()) {
                    audioUtility.music.pause();
                }
            } else if (resume) {
                if (!audioUtility.music.isPlaying()) {
                    audioUtility.music.start();
                }
            }
        }
    }
}
