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
import java.util.Collection;

public class AudioUtility implements Runnable {

    static AudioUtility instance;

    private AudioUtility() {
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

    // ---------------Member variables-------------

    // Background music
    Handler handler;
    Thread thread;
    MediaPlayer music;
    // Sound effects
    private SoundPool sounds;
    SparseIntArray resIDToSoundID;

    // ---------------Member methods---------------

    public static AudioUtility getInstance() {
        if (instance == null) instance = new AudioUtility();
        return instance;
    }

    void loadMusic(Context c, int resID) {
        music = MediaPlayer.create(c, resID);
    }

    void loadSound(Context c, Collection<Integer> resIDs) {
        for (Integer resID : resIDs) {
            int soundID = sounds.load(c, resID, 1);
            resIDToSoundID.append(resID, soundID);
        }
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

    public void sendSoundCommand(int soundID) {
        handler.post(new AudioRunnable(soundID, this));
    }

    public void playSound(int soundID) {
        sounds.play(soundID, 1, 1, 0, 0, 1);
    }

    public void sendMusicCommand(boolean startOver, boolean pause, boolean resume) {
        handler.post(new MusicRunnable(startOver, pause, resume, this));
    }

    public void playSoundFromResID(int resID) {
        sendSoundCommand(resIDToSoundID.get(resID));
    }

    /**
     * Background music
     */
    public void onMusic() {
        sendMusicCommand(false, false, true);
    }

    /**
     * Background music stops when paused
     */
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
            audioUtility.playSound(soundID);
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
