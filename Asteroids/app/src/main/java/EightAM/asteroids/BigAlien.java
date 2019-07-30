package EightAM.asteroids;

import EightAM.asteroids.specs.BigAlienSpec;

public class BigAlien extends Alien {

    public BigAlien(BigAlienSpec spec) {
        super(spec);
//        this.id = ObjectID.getNewID(Faction.Alien);
//        this.bitmap = BitmapStore.getInstance().getBitmap(((BaseAlienSpec) spec).bitMapName);
//        this.paint = PaintStore.getInstance().getPaint(((BaseAlienSpec) spec).paintName);
//        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y,
//                spec.initialPosition.x + ((BaseAlienSpec) spec).dimensions.x,
//                spec.initialPosition.y + ((BaseAlienSpec) spec).dimensions.y);
//        this.angularVel = 0;
//        this.orientation = ((BaseBitmapSpec) spec).initialOrientation;
        this.reloadTime = spec.reloadTime;
        this.turnDelayRange = spec.turnDelayRange;
//        this.vel = new Velocity(((BaseAlienSpec) spec).maxSpeed,
//                ((BaseAlienSpec) spec).initialAngle,
//                ((BaseAlienSpec) spec).maxSpeed);
        this.shotDelayRange = spec.shotDelayRange;
//        this.hitPoints = ((BaseAlienSpec) spec).hitPoints;
//        this.pointValue = ((BaseAlienSpec) spec).pointValue;
        setUp();
    }

    public BigAlien(BigAlien alien) {
        super(alien);
        this.reloadTime = alien.reloadTime;
        this.turnDelayRange = alien.turnDelayRange;
        this.shotDelayRange = alien.shotDelayRange;
        setUp();
    }

    @Override
    public void onCollide(GameObject approachingObject) {
        super.onCollide(approachingObject);
    }

    @Override
    GameObject makeCopy() {
        return new BigAlien(this);
    }
}
