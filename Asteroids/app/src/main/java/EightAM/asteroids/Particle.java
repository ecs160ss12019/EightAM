package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseParticleSpec;

public class Particle extends GameObject implements Destructable {

    // ---------------Member variables-------------

    Bitmap bitmap;
    int duration;
    DestructListener destructListener;

    // ---------------Member methods---------------

    Particle(BaseParticleSpec spec) {
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.duration = BaseParticleSpec.duration;
        this.paint = PaintStore.getInstance().getPaint(BaseParticleSpec.paintName);
        this.bitmap = BitmapStore.getInstance().getBitmap(BaseParticleSpec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x,
                spec.initialPosition.y,
                spec.initialPosition.x + BaseParticleSpec.dimensions.x,
                spec.initialPosition.y + BaseParticleSpec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, BaseParticleSpec.speed);
    }

    Particle(Particle particle) {
        this.id = particle.id;
        this.duration = particle.duration;
        this.paint = particle.paint;
        this.bitmap = particle.bitmap;
        this.hitbox = particle.hitbox;
        this.orientation = particle.orientation;
        this.vel = particle.vel;
    }

    @Override
    void update(Point spaceSize, long timeInMillisecond) {
        super.update(spaceSize, timeInMillisecond);
        duration -= timeInMillisecond;
        if (duration <= 0) {
            this.destruct();
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(this.hitbox, this.paint);
    }

    @Override
    public void destruct() {
        this.destructListener.onDestruct(this);
    }

    @Override
    public void linkDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }
}
