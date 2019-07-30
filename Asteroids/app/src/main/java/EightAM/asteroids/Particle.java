package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Random;

import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseParticleSpec;

public class Particle extends GameObject implements Destructable {

    // ---------------Member variables-------------

    Bitmap bitmap;
    long duration;
    DestructListener destructListener;

    // ---------------Member methods---------------

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

    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        super.update(spaceSize, timeInMillisecond);

//        this.duration -= timeInMillisecond;
        if (this.duration > timeInMillisecond) {
            this.duration -= timeInMillisecond;
        } else {
            this.destruct(null);
            this.duration = 0;
            //Log.d(this.getClass().toString(), Long.toString(timeInMillisecond));
        }
    }


    @Override
    public void draw(Canvas canvas) {
        Random r = new Random();
        //this.paint.setARGB(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
        canvas.drawRect(this.hitbox, this.paint);

        this.paint.setARGB(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    @Override
    GameObject makeCopy() {
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
