package EightAM.asteroids;

import java.util.Random;

public class GameRandom {
    static Random rand = new Random();

    public static float randomFloat(float max, float min) {
        return rand.nextFloat() * (max - min) + min;
    }

    public static int randomInt(int max, int min) {
        return rand.nextInt(max - min + 1) + min;
    }
}
