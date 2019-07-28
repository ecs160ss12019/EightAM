package EightAM.asteroids;

import static EightAM.asteroids.Constants.DEF_ANGLE;
import static EightAM.asteroids.Constants.DEF_ANGULAR_VELOCITY;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Drawable;
import EightAM.asteroids.interfaces.Identifiable;

public abstract class GameObject implements Drawable, Identifiable {

    // ---------------Member variables-------------

    Velocity vel;
    RectF hitbox;
    // dimensions of bitmap
    float hitboxWidth;
    float hitboxHeight;
    // Reference to the model holding the object
    float angularVel = DEF_ANGULAR_VELOCITY;
    float orientation = DEF_ANGLE;
    Paint paint;
    ObjectID id;

    // ---------------Member methods---------------

    /**
     * Move an object according to their velocity
     *
     * @param spaceSize        size of space (canvas)
     * @param timeInMillisecond moving distance calculated base on this input time
     */
    void move(Point spaceSize, long timeInMillisecond) {
        float dx = vel.x * timeInMillisecond;
        float dy = vel.y * timeInMillisecond;
        hitbox.offset(dx, dy);
        float cx = hitbox.centerX();
        float cy = hitbox.centerY();
        // if the center passes the boundary, wrap around the hitbox
        if (cx > spaceSize.x) {
            hitbox.offset(-(float) spaceSize.x, 0);
        } else if (cx < 0) {
            hitbox.offset((float) spaceSize.x, 0);
        }
        if (cy > spaceSize.y) {
            hitbox.offset(0, -(float) spaceSize.y);
        } else if (cy < 0) {
            hitbox.offset(0, (float) spaceSize.y);
        }
    }

    /**
     * Rotate method rotates the object
     */
    void rotate() {
        orientation += angularVel;
        if (orientation > Math.PI) {
            orientation -= 2 * Math.PI;
        }
        if (orientation < -Math.PI) {
            orientation += 2 * Math.PI;
        }
    }

    /**
     * Update method means rotating and moving the calling object.
     */
    void update(Point spaceSize, long timeInMillisecond) {
        rotate();
        move(spaceSize, timeInMillisecond);
    }

    @Override
    public ObjectID getID() { return id;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameObject)) return false;
        return this.getID().equals(((GameObject) o).getID());
    }

    public Point getObjPos() {
        return new Point((int)hitbox.centerX(), (int)hitbox.centerY());
    }

    public abstract void draw(Canvas canvas);
}
