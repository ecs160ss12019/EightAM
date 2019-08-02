package EightAM.asteroids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Pair;

import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.specs.BaseAsteroidSpec;

public class Asteroid extends GameObject implements Destructable, Collision, EventGenerator,
        AudioGenerator {

    // ---------------Member variable---------------

    Bitmap bitmap;
    BaseAsteroidSpec breaksInto;
    int breakCount;
    int pointValue;
    int hitPoints;
    Pair<Float, Float> speedRange;
    Pair<Float, Float> spinRange;

    // sound stuff
    int explosionID;
    private AudioListener audioListener;

    // ---------------Member methods----------------

    Asteroid(BaseAsteroidSpec spec) {
        super(spec);
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.speedRange = spec.speedRange;
        this.spinRange = spec.spinSpeedRange;
        this.breaksInto = spec.breaksInto;
        this.breakCount = spec.breakCount;
        this.pointValue = spec.pointValue;
        this.hitPoints = spec.hitPoints;

        this.explosionID = spec.explosionID;
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

        this.explosionID = asteroid.explosionID;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();

        matrix.setTranslate(this.hitbox.centerX() - (float) (bitmap.getWidth() / 2),
                this.hitbox.centerY() - (float) (bitmap.getHeight() / 3));
        matrix.postRotate((float) Math.toDegrees(rotation.theta),
                this.hitbox.centerX(),
                this.hitbox.centerY());

        //canvas.drawRect(this.hitbox, this.paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public Object makeCopy() {
        return new Asteroid(this);
    }

    @Override
    public void destruct(DestroyedObject destroyedObject) {
        // implement destruction effects here.
        eventHandler.processScore(destroyedObject);
        audioListener.sendSoundCommand(explosionID);
        super.destruct(destroyedObject);
    }

    @Override
    public boolean detectCollisions(GameObject approachingObject) {
        return this.hitbox.intersect(approachingObject.hitbox);
    }

    @Override
    public void onCollide(GameObject gameObject) {
        boolean destroyThis = false;
        if (gameObject instanceof Bullet) {
            hitPoints -= ((Bullet) gameObject).damage;
            if (hitPoints <= 0) {
                destroyThis = true;
            }
        } else {
            destroyThis = true;
        }
        if (destroyThis) {
            destruct(new DestroyedObject(pointValue, id, gameObject.id, this));
        }
    }

    @Override
    public boolean canCollide() {
        return true;
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        this.audioListener = listener;
    }
}
