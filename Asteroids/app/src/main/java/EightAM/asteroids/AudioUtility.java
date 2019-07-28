package EightAM.asteroids;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class AudioUtility {

    // ---------------Member variables-------------

    // Background music
    MediaPlayer music;
    // Sound effects
    private SoundPool sounds;
    private int ship_accelerate_ID = -1;
    private int ship_shoot_ID = -1;
    private int ship_destroy_ID = -1;

    // ---------------Member methods---------------

    AudioUtility(Context context) {
        // Initialize soundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

            sounds = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build();
        } else {
            sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        // Load the sounds from resource file
        ship_accelerate_ID = sounds.load(context, R.raw.ship_accelerate, 1);
        ship_shoot_ID = sounds.load(context, R.raw.ship_shoot, 1);
        ship_destroy_ID = sounds.load(context, R.raw.ship_destroy, 1);
    }

    /**
     * Play corresponding sound effect basing on button pressed by user
     */
    void playInputPress(boolean up, boolean down, boolean left, boolean right, boolean shoot) {
//        if (up) {
//            sounds.play(ship_accelerate_ID, 1, 1, 0, 0, 2);
//        } else if (down) {
//            sounds.play(ship_destroy_ID, 1, 1, 0, 0, 2);
//        } else if (left) {
//            sounds.play(ship_destroy_ID, 1, 1, 0, 0, 2);
//        } else if (right) {
//            sounds.play(ship_destroy_ID, 1, 1, 0, 0, 2);
//        } else if (shoot) sounds.play(ship_shoot_ID, 1, 1, 0, 0, 2);
    }

    /**
     * Background music plays inside MainActivity
     */
    void playMusic(Context context) {
        music = MediaPlayer.create(context, R.raw.background_music);
        music.setLooping(true);
        music.start();
    }

    /**
     * Background music stops when MainActivity paused
     */
    void stopMusic() {
        music.stop();
        music.release();
    }

    /**
     * Temporary sample code
     */
    void playShipDestroy() {
        sounds.play(ship_destroy_ID, 1, 1, 0, 0, 1);
    }
}
