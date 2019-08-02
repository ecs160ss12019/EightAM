package EightAM.asteroids.specs;

import java.util.Collection;

// Used by AssetLoader to load any sounds an object may have.
public interface AudioSpec {
    String getTag();
    Collection<Integer> getResIDs();
}
