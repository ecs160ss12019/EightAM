package EightAM.asteroids;

import android.graphics.RectF;

import EightAM.asteroids.interfaces.MoveStrategy;

/**
 * MoveStrategy that moves objects while wrapping them around a rectangular boundary.
 */
class MoveWrap implements MoveStrategy {
    RectF boundaries;

    public MoveWrap(RectF boundaries) {
        this.boundaries = boundaries;
    }

    @Override
    public void move(GameObject o, long deltaTime) {
        float dx = o.vel.x * deltaTime;
        float dy = o.vel.y * deltaTime;
        o.hitbox.offset(dx, dy);
        float cx = o.hitbox.centerX();
        float cy = o.hitbox.centerY();
        // if the center passes the boundary, wrap around the hitbox
        if (cx > boundaries.right) {
            o.hitbox.offset(-boundaries.width(), 0);
        } else if (cx < boundaries.left) {
            o.hitbox.offset(boundaries.width(), 0);
        }
        if (cy > boundaries.bottom) {
            o.hitbox.offset(0, -boundaries.height());
        } else if (cy < 0) {
            o.hitbox.offset(0, boundaries.height());
        }

    }
}
