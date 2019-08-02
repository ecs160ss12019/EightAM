package EightAM.asteroids.specs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.R;

public class LevelAudioSpec implements AudioSpec {
    public int alien_wave = R.raw.alien_wave;
    public int alien_boss = R.raw.alien_boss;
    public int asteroid_wave = R.raw.sound_alarm;
    public int game_over = R.raw.game_over;


    @Override
    public Collection<Integer> getResIDs() {
        return Collections.unmodifiableList(
                Arrays.asList(alien_wave, alien_boss, asteroid_wave, game_over));
    }
}
