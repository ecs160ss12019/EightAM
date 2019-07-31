package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Drawable;
import EightAM.asteroids.interfaces.Identifiable;
import EightAM.asteroids.interfaces.MoveStrategy;
import EightAM.asteroids.specs.BaseObjectSpec;

public abstract class GameObject implements Drawable, Identifiable {

    // ---------------Member variables-------------

    ObjectID id;
    Velocity vel;
    RectF hitbox;
    MoveStrategy moveStrategy;
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
     * @param timeInMillisecond moving distance calculated base on this input time
     */
    void move(long timeInMillisecond) {
        if (moveStrategy != null) {
            moveStrategy.move(this, timeInMillisecond);
        }
    }

    /**
     * Update method means rotating and moving the calling object.
     */
    void update(long timeInMillisecond) {
        rotation.rotate(timeInMillisecond);
        move(timeInMillisecond);
    }

    /**
     * Every GameObject instantiation needs this to be set before being added to GameModel.
     *
     * @param moveStrategy determines move behavior of GameObject
     */
    void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
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
