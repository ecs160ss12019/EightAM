package EightAM.asteroids;

final class Constants {
    // GENERAL
    static final float ALIEN_SPAWN_PROB = .005f; // 100% while debugging

    // ASTEROID
    static final float SAFE_DISTANCE = 800;

    // SHIP
    static final int SHIP_RESTART_DURATION = 300;

    // BIG ALIEN
    static final float BIGALIEN_SPAWN_PROB = 0.7f;

    // SMALL ALIEN
    static final float SMALLALIEN_SPAWN_PROB = 0.3f;

    //PARTICLE EFFECT
    static final int EFFECT_NUM = 10;
    static final int EFFECT_RADIUS = 8;

    // WAVE INFO
    static final int STARTING_ASTEROIDS = 3;
    static final float ALIEN_PROB_INC = .1f;
    static final int ASTEROID_INC_WAVE = 1;
    static final long WAVE_GRACE_PERIOD = 5000;
    static final int MAX_ALIENS_PER_LEVEL = 1;

    // SPAWNBOX STUFF
    static final int BOUNDARY_OFFSET = 200;

    // PLAYER INFO
    static final int STARTING_LIVES = 3;

    // GAMEOVER DELAY
    static final int GAMEOVER_DELAY = 3000;
}
