package EightAM.asteroids;

final class Constants {
    static final float DEF_ANGULAR_VELOCITY = 0;
    static final float DEF_ANGLE = (float) Math.PI / 2;
    static final int STARTING_ASTEROIDS = 3;
    static final int STARTING_LIVES = 1;
    static final int ASTEROID_LARGE_RADIUS = 3;
    static final int ASTEROID_MEDIUM_RADIUS = 2;
    static final int ASTEROID_SMALL_RADIUS = 1;
    static final float ASTEROID_MAXSPEED = 0.1f; // TODO: subject to change
    static final double ASTEROID_MAXANGLE = 2 * Math.PI;
    static final float SHIP_ACCELERATION = 0.005f;
    static final float SHIP_DECELERATION = 0.995f;
    static final float SHIP_ANGULARVELOCITY = 0.01f;
    static final float SHIP_BITMAP_HITBOX_SCALE = 0.5f;
}
