package EightAM.asteroids;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

public class AudioUtility {

    // ---------------Member variables-------------

    // background music

    // sound effects
    private SoundPool sounds;
    private int ship_accelerate_ID = -1;
    private int ship_shoot_ID = -1;
    private int ship_destroy_ID = -1;

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

        ship_accelerate_ID = sounds.load(context, R.raw.ship_accelerate, 1);
        ship_shoot_ID = sounds.load(context, R.raw.ship_shoot, 1);
        ship_destroy_ID = sounds.load(context, R.raw.ship_destroy, 1);
    }

    void playShipshoot(Boolean bool) {
        if (bool)
            sounds.play(ship_shoot_ID,1, 1, 0, 0, 1);

//        switch (view.getId()) {
//            case R.id.shoot_button:
//                sounds.play(ship_shoot_ID,1, 1, 0, 0, 1);
//                break;
//            case R.id.up_button:
//                sounds.play(ship_accelerate_ID,1, 1, 0, 0, 1);
//                break;
//        }
    }

    void playShipAccelerate (Boolean bool) {
        if (bool)
            sounds.play(ship_accelerate_ID,1, 1, 0, 0, 1);
    }

    void playShipDestroy() {
        sounds.play(ship_destroy_ID,1, 1, 0, 0, 1);
    }
}
