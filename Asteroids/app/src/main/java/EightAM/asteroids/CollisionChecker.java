package EightAM.asteroids;

import java.util.ArrayList;
import java.util.Collection;

import EightAM.asteroids.interfaces.Collideable;

class CollisionChecker {

    static Collection<GameObject> collidesWith(GameObject actor, Collection<GameObject> list) {
        ArrayList<GameObject> ret = new ArrayList<>();
        for (GameObject o : list) {
            if (o instanceof Collideable && ((Collideable) o).detectCollisions(actor)) {
                ret.add(o);
            }
        }
        return ret;
    }

    //    static void shipCollision(GameModel model) {
    //        Ship playerShip = model.getPlayerShip();
    //        for (GameObject o : model.objectMap.values()) {
    //            if (playerShip.detectCollisions(o)) {
    //                model.onCollision(playerShip.getID(), o.getID());
    //                break;
    //            }
    //        }
    //    }
    //
    //    static void bulletsCollision(GameModel model) {
    //        HashSet<Integer> asteroidSet = new HashSet<>();
    //        for (int i = 0; i < model.bulletsFired.size(); i++) {
    //            for (int j = 0; j < model.asteroidBelt.size(); j++) {
    //                if (model.bulletsFired.get(i).detectCollisions(model.asteroidBelt.get(j))) {
    //                    model.bulletFactory.markToDelete(i);
    //                    if (!asteroidSet.contains(j)) {
    //                        asteroidSet.add(j);
    //                        model.asteroidFactory.markToDelete(j);
    //                    }
    //                }
    //            }
    //        }
    //        model.bulletFactory.removeMarked();
    //        model.asteroidFactory.removeMarked();
    //    }

    /*
    private void bulletsCollision2() {


        Iterator <Bullet>bulletsFiredIter = bulletsFired.iterator();
        Iterator <Asteroid>asteroidBeltIter = asteroidBelt.iterator();

        while (bulletsFiredIter.hasNext()) {
            while (asteroidBeltIter.hasNext()) {
                if (bulletsFiredIter.next().detectCollisions(asteroidBeltIter.next())){
                    bulletsFiredIter.remove();
                    asteroidBeltIter.remove();
                }
            }
        }

    }
    */
}
