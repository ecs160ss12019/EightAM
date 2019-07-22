package EightAM.asteroids;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

public class CollisionChecker {
    GameModel model;

    public CollisionChecker(GameModel gamemodel){
        model = gamemodel;
    }

    public void shipCollision() {
        for (int i = 0; i < model.asteroidBelt.size(); i++) {
            if (model.playerShip.detectCollisions(model.asteroidBelt.get(i))) {
                model.destroyShip();
                model.asteroidFactory.removeAtIndex(i);
                break;
            }
        }
        // TODO: Add aliens collisions
    }

    public void bulletsCollision() {
        HashSet<Integer> asteroidSet = new HashSet<>();
        for (int i = 0; i < model.bulletsFired.size(); i++) {
            for (int j = 0; j < model.asteroidBelt.size(); j++) {
                if (model.bulletsFired.get(i).detectCollisions(model.asteroidBelt.get(j))) {
                    model.bulletFactory.markToDelete(i);
                    if(!asteroidSet.contains(j)){
                        asteroidSet.add(j);
                        model.asteroidFactory.markToDelete(j);
                    }
                }
            }
        }

        model.bulletFactory.removeMarked();
        model.asteroidFactory.removeMarked();

    }

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
