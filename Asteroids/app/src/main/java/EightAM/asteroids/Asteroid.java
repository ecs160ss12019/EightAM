package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseAsteroidSpec;
import EightAM.asteroids.specs.LargeAsteroidSpec;

public class Asteroid extends GameObject implements Destructable {

    // ---------------Member variable---------------

    Bitmap bitmap;
    private Size rockSize;
    public BaseAsteroidSpec breaksInto;
    DestructListener destructListener;
    Pair<Float, Float> speedRange;
    Pair<Float, Float> spinRange;

    // ---------------Member methods----------------

    Asteroid(BaseAsteroidSpec spec) {
        this.id = ObjectID.getNewID(Faction.Neutral);
        if (spec instanceof LargeAsteroidSpec) {
            this.rockSize = Size.LARGE;
        }
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + spec.dimensions.x,
                spec.initialPosition.y + spec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, spec.speedRange.second);
        this.angularVel = spec.spinSpeedRange.second;
        this.speedRange = spec.speedRange;
        this.spinRange = spec.spinSpeedRange;
        this.breaksInto = spec.breaksInto;
    }

    Asteroid(Asteroid asteroid) {
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.rockSize = asteroid.rockSize;
        this.paint = asteroid.paint;
        this.bitmap = asteroid.bitmap;
        this.hitbox = new RectF(asteroid.hitbox);
        this.orientation = asteroid.orientation;
        this.vel = new Velocity(asteroid.vel);
        this.angularVel = asteroid.angularVel;
        this.speedRange = asteroid.speedRange;
        this.spinRange = asteroid.spinRange;
        this.breaksInto = asteroid.breaksInto;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation),
                (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f),
                hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, this.paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void destruct() {
        // implement destruction effects here.
    }

    @Override
    public void linkDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }

    // enum size used to denote three size types of asteroid rock
    enum Size {
        SMALL, MEDIUM, LARGE
    }
}
