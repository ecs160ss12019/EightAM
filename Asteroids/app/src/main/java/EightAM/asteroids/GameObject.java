package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.Collections;

import EightAM.asteroids.interfaces.Copyable;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.Drawable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.Identifiable;
import EightAM.asteroids.interfaces.MoveStrategy;
import EightAM.asteroids.specs.BaseObjectSpec;
import EightAM.asteroids.specs.RandomLootSpec;

public abstract class GameObject implements Drawable, Identifiable, Destructable, Copyable,
        EventGenerator {

    // ---------------Member variables-------------

    ObjectID id;
    Velocity vel;
    RectF hitbox;
    MoveStrategy moveStrategy;
    Rotation rotation;
    Paint paint;
    Loot lootOnDeath;

    // listeners
    DestructListener destructListener;
    EventHandler eventHandler;

    public GameObject(BaseObjectSpec spec) {
        // defer instantiation of id
        this.vel = new Velocity(spec.initialVelocity);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.rotation = new Rotation(spec.initialRotation);
        this.paint = PaintStore.getInstance().getPaint(spec.tag);
        if (spec.lootOnDeath != null) {
            this.lootOnDeath = BaseLootFactory.getInstance().createLoot(spec.lootOnDeath);
        }
    }

    GameObject(GameObject object) {
        this.vel = new Velocity(object.vel);
        this.hitbox = new RectF(object.hitbox);
        this.rotation = new Rotation(object.rotation);
        this.paint = object.paint;
        if (object.lootOnDeath != null) {
            this.lootOnDeath = object.lootOnDeath;
        }
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
        if (lootOnDeath != null) {
            eventHandler.createObjects(Collections.singleton(LootGenerator.createRandomLootAt(
                    new Point((int) hitbox.centerX(), (int) hitbox.centerY()),
                    new RandomLootSpec())));
        }
        destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }

    @Override
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }
}
