package EightAM.asteroids.specs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import EightAM.asteroids.Powerups;

public class RandomPowerupSpec {
    // corresponds to the probabilities of each enum in Powerups
    List<Float> powerupProbabilities = Collections.unmodifiableList(
            Arrays.asList(.3f, .2f, .2f, .2f, .1f));

    public float getProbabilityOfEnum(Powerups e) {
        return powerupProbabilities.get(e.index);
    }
}
