package EightAM.asteroids;

import android.graphics.Bitmap;

import EightAM.asteroids.interfaces.AIModule;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.specs.BaseAlienSpec;

abstract class AbstractAlien extends GameObject implements Destructable, Collision, EventGenerator,
        AIModule {
    Bitmap bitmap;
    float dbmRatio;
    EventHandler eventHandler;

    AbstractAlien(BaseAlienSpec spec) {
        super(spec);
        this.bitmap = BitmapStore.getInstance().getBitmap(spec.tag);
        this.dbmRatio = spec.dimensionBitMapRatio;
    }

    AbstractAlien(AbstractAlien alien) {
        super(alien);
        this.bitmap = alien.bitmap;
        this.dbmRatio = alien.dbmRatio;
    }

    @Override
    public void registerEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }

}
