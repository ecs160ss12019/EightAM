package EightAM.asteroids;

import java.util.Random;

public class GameRandom {
    public static float randomFloat(float max, float min){
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;
    }
}
