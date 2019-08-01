package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.specs.BaseLootSpec;

public abstract class Loot extends GameObject implements EventGenerator, Collision {
    Bitmap bitmap;
    float dbmRatio;

    float randomAcceleration;
    Timer duration;

    public Loot(BaseLootSpec spec) {
        super(spec);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;
        this.randomAcceleration = spec.randomAcceleration;
        this.duration = new Timer(spec.durationMS, 0);
    }

    Loot(Loot loot) {
        super(loot);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.bitmap = loot.bitmap;
        this.dbmRatio = loot.dbmRatio;
        this.randomAcceleration = loot.randomAcceleration;
        this.duration = new Timer(loot.duration);
    }

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return this.hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        if (duration.update(timeInMillisecond)) {
            this.destruct(null);
        }
        // random movement
        this.vel.accelerate(randomAcceleration, GameRandom.randomFloat((float) (2 * Math.PI), 0),
                1 - randomAcceleration);
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
        canvas.drawBitmap(bitmap, matrix, paint);
    }


}
