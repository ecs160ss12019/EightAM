package EightAM.asteroids;

final class Constants {
    // GENERAL
    static final float DEF_ANGULAR_VELOCITY = 0;
    static final float DEF_ANGLE = 0;
    static final float MAX_SPEED = 2.0f;
    // ASTEROID
    static final int STARTING_ASTEROIDS = 3;
    static final int STARTING_LIVES = 3;
    static final int ASTEROID_LARGE_RADIUS = 3;
    static final int ASTEROID_MEDIUM_RADIUS = 2;
    static final int ASTEROID_SMALL_RADIUS = 1;
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
    static final int BIGALIEN_TIMER_MIN = 4000;
    static final int BIGALIEN_TIMER_MAX = 6000;
    static final int BIGALIEN_SHOTDELAY_MIN = 3000;
    static final int BIGALIEN_SHOTDELAY_MAX = 5000;

}
