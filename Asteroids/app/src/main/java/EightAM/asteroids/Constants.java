package EightAM.asteroids;

final class Constants {
    // GENERAL
    static final float DEF_ANGULAR_VELOCITY = 0;
    static final float DEF_ANGLE = 0;
    static final float MAX_SPEED = 2.0f;

    // ASTEROID
    static final int ASTEROID_LARGE_RADIUS = 3;
    static final int ASTEROID_MEDIUM_RADIUS = 2;
    static final int ASTEROID_SMALL_RADIUS = 1;
    static final float SAFE_DISTANCE = 800;
    static final float ASTEROID_MAXSPEED = 0.5f; // TODO: subject to change
    static final double ASTEROID_MAXANGLE = 2 * Math.PI;

    // BULLET
    static final int BULLET_SPEED = 10;
    static final float BULLET_MAX_RANGE = 400; // Max distance bullet travels

    // SHIP
    static final float SHIP_BITMAP_HITBOX_SCALE = 0.5f;
    static final int SHIP_RESTART_DURATION = 300;

    // ALIEN
    static final float ALIEN_BIG_MAXSPEED = 0.1f; // TODO: also subject to change
    static final int ALIEN_VALUE = 10;
    static final float ALIEN_TARGET_ACCURACY = 200f;

    // BIG ALIEN
    static final float BIGALIEN_SPAWN_PROB = 0.7f;

    // SMALL ALIEN
    static final float SMALLALIEN_SPAWN_PROB = 0.3f;

    //PARTICLE EFFECT
    static final int EFFECT_NUM = 50;
    static final int EFFECT_RADIUS = 8;

    // WAVE INFO
    static final int STARTING_ASTEROIDS = 3;
    static final float alienProbInc = .1f;
    static final int asteroidIncWave = 1;

    // PLAYER INFO
    static final int STARTING_LIVES = 4;
}
