package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Collections;

import EightAM.asteroids.interfaces.Copyable;
import EightAM.asteroids.interfaces.Drawable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Identifiable;
import EightAM.asteroids.interfaces.MoveStrategy;
import EightAM.asteroids.specs.BaseLootSpec;
import EightAM.asteroids.specs.BaseObjectSpec;
import EightAM.asteroids.specs.RandomLootSpec;

public abstract class GameObject implements Drawable, Identifiable, Copyable, EventGenerator {

    // ---------------Member variables-------------

    ObjectID id;
    Velocity vel;
    RectF hitbox;
    MoveStrategy moveStrategy;
    Rotation rotation;
    Paint paint;
    BaseLootSpec lootOnDeathSpec;

    // listeners
    EventHandler eventHandler;

    public GameObject(BaseObjectSpec spec) {
        // defer instantiation of id
        this.vel = new Velocity(spec.initialVelocity);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.rotation = new Rotation(spec.initialRotation);
        this.paint = PaintStore.getInstance().getPaint(spec.tag);
        this.lootOnDeathSpec = spec.lootOnDeath;
    }

    GameObject(GameObject object) {
        this.vel = new Velocity(object.vel);
        this.hitbox = new RectF(object.hitbox);
        this.rotation = new Rotation(object.rotation);
        this.paint = object.paint;
        this.lootOnDeathSpec = object.lootOnDeathSpec;
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

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        if (lootOnDeathSpec instanceof RandomLootSpec) {
            eventHandler.createObjects(Collections.singleton(
                    LootGenerator.createRandomLootAt(getObjPos(),
                            (RandomLootSpec) lootOnDeathSpec)));
        }
        eventHandler.onDestruct(this);
    }

    @Override
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }
}
