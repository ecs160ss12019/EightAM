package EightAM.asteroids.specs;

import java.util.Collection;
import java.util.Collections;

import EightAM.asteroids.R;

public class GameMusicSpec implements MusicSpec {
    public int music = R.raw.background_music;

    @Override
    public Collection<Integer> getResIDs() {
        return Collections.singleton(music);
    }
}
