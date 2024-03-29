package EightAM.asteroids;

final class Constants {
    // GENERAL
    static final double ALIEN_SPAWN_PROB = .00005d; // 100% while debugging

    // PARTICLE EFFECT
    static final int EFFECT_NUM = 20;

    // WAVE INFO
    static final int STARTING_ASTEROIDS = 2;
    static final float ALIEN_PROB_INC = .00001f;
    static final int ASTEROID_INC_WAVE = 1;
    static final int WAVE_GRACE_PERIOD = 5000;
    static final int STARTING_MAX_ALIENS = 1;

    // SPAWNBOX STUFF
    static final int BOUNDARY_OFFSET = 200;
    static final float BOUNDARY_SHRINK_RATE = 0.00001f;

    // PLAYER INFO
    static final int STARTING_LIVES = 3;

    // GAMEOVER DELAY
    static final int GAMEOVER_DELAY = 3000;

    // TEXT
    static final int TEXT_ADJUSTMENT = 40;

    // Lootbox
    static final int STARTING_MAX_POWERUPS = 1;
}
