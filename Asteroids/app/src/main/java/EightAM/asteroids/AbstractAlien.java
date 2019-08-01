package EightAM.asteroids;

import android.graphics.Bitmap;

import EightAM.asteroids.interfaces.AIModule;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.specs.BaseAlienSpec;

abstract class AbstractAlien extends GameObject implements Collision, AIModule {
    private final int pointValue;
    private final int hitPoints;
    Bitmap bitmap;
    float dbmRatio;

    AbstractAlien(BaseAlienSpec spec) {
        super(spec);
        this.id = ObjectID.getNewID(Faction.Alien);

        this.bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;

        // alien spec
        this.pointValue = spec.pointValue;
        this.hitPoints = spec.hitPoints;
    }

    AbstractAlien(AbstractAlien alien) {
        super(alien);
        this.id = ObjectID.getNewID(Faction.Alien);

        this.bitmap = alien.bitmap;
        this.dbmRatio = alien.dbmRatio;

        this.pointValue = alien.pointValue;
        this.hitPoints = alien.hitPoints;
    }
}
