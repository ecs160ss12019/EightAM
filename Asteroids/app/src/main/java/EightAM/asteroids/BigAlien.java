package EightAM.asteroids;

import android.graphics.RectF;
import android.util.Log;

import EightAM.asteroids.specs.BaseAlienSpec;
import EightAM.asteroids.specs.BigAlienSpec;

public class BigAlien extends Alien {

    public BigAlien(BigAlienSpec spec) {
        this.id = ObjectID.getNewID(Faction.Alien);
        this.bitmap = BitmapStore.getInstance().getBitmap(((BaseAlienSpec) spec).bitMapName);
        this.paint = PaintStore.getInstance().getPaint(((BaseAlienSpec) spec).paintName);
        this.canShoot = false;
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
                spec.initialPosition.x + ((BaseAlienSpec) spec).dimensions.x,
                spec.initialPosition.y + ((BaseAlienSpec) spec).dimensions.y);
        this.angularVel = 0;
        this.orientation = spec.initialOrientation;
        this.turnDelayRange = spec.turnDelayRange;
        this.vel = new Velocity(((BaseAlienSpec) spec).maxSpeed,
                ((BaseAlienSpec) spec).initialAngle,
                ((BaseAlienSpec) spec).maxSpeed);
        this.shotDelayRange = spec.shotDelayRange;
        setUp();
    }

    public BigAlien(BigAlien alien) {
        this.id = ObjectID.getNewID(Faction.Alien);
        this.bitmap = alien.bitmap;
        this.paint = alien.paint;
        this.canShoot = false;
        this.hitbox = new RectF(alien.hitbox);
        this.angularVel = alien.angularVel;
        this.orientation = alien.orientation;
        this.turnDelayRange = alien.turnDelayRange;
        this.vel = new Velocity(alien.vel);
        this.shotDelayRange = alien.shotDelayRange;
        setUp();
    }
}
