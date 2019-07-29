package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

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
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.duration = spec.duration;
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, spec.speedRange.second);
    }

    Particle(Particle particle) {
        this.id = ObjectID.getNewID(Faction.Neutral); // Important
        this.duration = particle.duration;
        this.paint = particle.paint;
        this.bitmap = particle.bitmap;
        this.hitbox = new RectF(particle.hitbox);
        this.orientation = particle.orientation;
        this.vel = new Velocity(particle.vel);
    }

    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        super.update(spaceSize, timeInMillisecond);

//        this.duration -= timeInMillisecond;
        if (this.duration > timeInMillisecond) {
            this.duration -= timeInMillisecond;
        } else {
            this.destruct();
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
    public void destruct() {
        this.destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }
}
