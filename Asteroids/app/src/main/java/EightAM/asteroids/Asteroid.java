package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Pair;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.DestructListener;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.specs.BaseAsteroidSpec;

public class Asteroid extends GameObject implements Destructable, Collision, EventGenerator {

    // ---------------Member variable---------------

    Bitmap bitmap;
    BaseAsteroidSpec breaksInto;
    int breakCount;
    int pointValue;
    int hitPoints;
    DestructListener destructListener;
    Pair<Float, Float> speedRange;
    Pair<Float, Float> spinRange;

    // ---------------Member methods----------------

    Asteroid(BaseAsteroidSpec spec) {
        super(spec);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.bitMapName);
        this.speedRange = spec.speedRange;
        this.spinRange = spec.spinSpeedRange;
        this.breaksInto = spec.breaksInto;
        this.breakCount = spec.breakCount;
        this.pointValue = spec.pointValue;
        this.hitPoints = spec.hitPoints;
    }

    Asteroid(Asteroid asteroid) {
        super(asteroid);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.bitmap = asteroid.bitmap;
        this.speedRange = asteroid.speedRange;
        this.spinRange = asteroid.spinRange;
        this.breaksInto = asteroid.breaksInto;
        this.breakCount = asteroid.breakCount;
        this.pointValue = asteroid.pointValue;
        this.hitPoints = asteroid.hitPoints;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() -(float)(bitmap.getWidth()/2),
                this.hitbox.centerY() - (float)(bitmap.getHeight()/3));
        matrix.postRotate((float) Math.toDegrees(rotation.theta),
                this.hitbox.centerX(),
                this.hitbox.centerY());

        canvas.drawRect(this.hitbox, this.paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    GameObject makeCopy() {
        return new Asteroid(this);
    }

    public void destruct() {
        // implement destruction effects here.
        destructListener.onDestruct(this);
    }

    @Override
    public void registerDestructListener(DestructListener listener) {
        this.destructListener = listener;
    }

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return this.hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject gameObject) {
        destruct();
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    @Override
    public void registerEventHandler(EventHandler handler) {

    }
}
