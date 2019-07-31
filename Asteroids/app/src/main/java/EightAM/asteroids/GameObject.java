package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Drawable;
import EightAM.asteroids.interfaces.Identifiable;
import EightAM.asteroids.specs.BaseObjectSpec;

public abstract class GameObject implements Drawable, Identifiable {

    // ---------------Member variables-------------

    ObjectID id;
    Velocity vel;
    RectF hitbox;
    // dimensions of bitmap
    // Reference to the model holding the object
//    float angularVel;
//    float orientation;
    Rotation rotation;
    Paint paint;

    public GameObject(BaseObjectSpec spec) {
        // defer instantiation of id
        this.vel = new Velocity(spec.initialVelocity);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.rotation = new Rotation(spec.initialRotation);
        this.paint = PaintStore.getInstance().getPaint(spec.tag);
        this.paint = PaintStore.getInstance().getPaint(spec.tag);
    }

    GameObject(GameObject object) {
        this.vel = new Velocity(object.vel);
        this.hitbox = new RectF(object.hitbox);
        this.rotation = new Rotation(object.rotation);
        this.paint = object.paint;
    }

    // ---------------Member methods---------------

    /**
     * Move an object according to their velocity
     *
     * @param spaceSize         size of space (canvas)
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
     * Update method means rotating and moving the calling object.
     */
    void update(Point spaceSize, long timeInMillisecond) {
        rotation.rotate(timeInMillisecond);
        move(spaceSize, timeInMillisecond);
    }

    @Override
    public ObjectID getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameObject)) return false;
        return this.getID().equals(((GameObject) o).getID());
    }

    public Point getObjPos() {
        return new Point((int) hitbox.centerX(), (int) hitbox.centerY());
    }


    public abstract void draw(Canvas canvas);

    abstract GameObject makeCopy();
}
