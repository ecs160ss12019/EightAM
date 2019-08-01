package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseParticleSpec;

public class Particle extends GameObject implements Destructable {

    // ---------------Member variables-------------

    long duration;
    DestructListener destructListener;

    // ---------------Member methods---------------

    /**
     *  Main constructor of a Particle object.
     * @param spec specifies particle's attributes.
     */
    Particle(BaseParticleSpec spec) {
        super(spec);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.duration = spec.duration;
    }

    Particle(Particle particle) {
        super(particle);
        this.id = ObjectID.getNewID(Faction.Neutral); // Important
        this.duration = particle.duration;
    }

    /**
     * Decrement timer (duration). Once the timer is up, explosion fades its animation
     * @param timeInMillisecond
     */
    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);

//        this.durationMS -= timeInMillisecond;
        if (this.duration > timeInMillisecond) {
            this.duration -= timeInMillisecond;
        } else {
            this.destruct(null);
            this.duration = 0;
        }
    }


    @Override
    public void draw(Canvas canvas) {
        Random r = new Random();
        canvas.drawRect(this.hitbox, this.paint);
        this.paint.setARGB((int)this.duration, r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    @Override
    public Object makeCopy() {
        return new Particle(this);
    }

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        this.destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }
}
