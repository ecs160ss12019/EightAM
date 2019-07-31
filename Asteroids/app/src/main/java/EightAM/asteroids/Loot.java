package EightAM.asteroids;

import static EightAM.asteroids.Constants.LOOT_FADE_TIME;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.specs.BaseLootSpec;

abstract class Loot extends GameObject implements EventGenerator, Collision {
    Bitmap bitmap;
    float dbmRatio;

    float randomAcceleration;
    Timer duration;

    // listeners
    EventHandler eventHandler;

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
        this.duration = new Timer(loot.duration.target, loot.duration.start);
    }

    @Override
    void update(long timeInMillisecond) {
        super.update(timeInMillisecond);
        if (duration.update(timeInMillisecond)) {
            this.destruct(null);
        }
        this.vel.accelerate(randomAcceleration, GameRandom.randomFloat((float) (2 * Math.PI), 0),
                1 - randomAcceleration);
    }

    void drawFading() {
        if (!(duration.curr % 10 <= 5)) {
            paint.setAlpha(0);
        } else {
            paint.setAlpha(255);
        }
    }

    @Override
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        if (Math.abs(duration.target - duration.curr) < LOOT_FADE_TIME) {
            drawFading();
        }
        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 2));
        canvas.drawBitmap(bitmap, matrix, paint);
    }


}
