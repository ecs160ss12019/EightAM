package EightAM.asteroids;

import android.util.Log;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import EightAM.asteroids.interfaces.Collision;

class CollisionChecker {
    private static CollisionChecker instance = new CollisionChecker();
    private CollisionChecker() {}
    static ObjectID collisionID;

    static void init() {
        if (instance == null) instance = new CollisionChecker();
    }
    public static CollisionChecker getInstance(){
        if (instance == null) init();
        return instance;
    }


    private static ObjectID collidesWith(GameObject actor, Collection<GameObject> list) {
        for (GameObject o : list) {
            if (!o.getClass().equals(actor.getClass())) {
                if ((o instanceof Collision) && ((Collision) actor).detectCollisions(o)) {
                    if (o.id.getFaction() != actor.id.getFaction()) {
                        return o.id;
                    }
                }
            }
        }
        return null;
    }

    private static void computeCollision(ObjectID objectID, Map<ObjectID, GameObject> objectMap) {
        collisionID = CollisionChecker.collidesWith(objectMap.get(objectID), objectMap.values());
        if (collisionID != null) {
            ((Collision) objectMap.get(objectID)).onCollide(objectMap.get(collisionID));
        }
    }

    public static void enumerateCollisions(Set<ObjectID> objectIDSet, Map<ObjectID, GameObject> objectMap) {
        for (ObjectID objectID : objectIDSet) {
            computeCollision(objectID, objectMap);
        }
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
