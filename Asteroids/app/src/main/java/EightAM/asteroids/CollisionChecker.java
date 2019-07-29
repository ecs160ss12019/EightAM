package EightAM.asteroids;

import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.GameState;

class CollisionChecker {

    private CollisionChecker() {
    }

//    private static ObjectID collidesWith(GameObject actor, Collection<GameObject> list) {
//        for (GameObject o : list) {
//            if (!o.getClass().equals(actor.getClass())) {
//                if ((o instanceof Collision) && ((Collision) actor).detectCollisions(o)) {
//                    if (o.id.getFaction() != actor.id.getFaction()) {
//                        return o.id;
//                    }
//                }
//            }
//        }
//        return null;
//    }

    private static GameObject collidesWith(Collision actor, GameState gameState) {
        for (ObjectID collideableID : gameState.getCollideableIDs()) {
            GameObject object = gameState.getGameObject(collideableID);
            if (!object.getID().equals(actor.getID())
                    && object.getID().getFaction() != actor.getID().getFaction()) {
                if (((Collision) object).canCollide() && actor.canCollide()) {
                    if ((actor).detectCollisions(object)) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public static void enumerateCollisions(GameState gameState) {

        for (ObjectID objectID : gameState.getCollideableIDs()) {
            computeCollision((Collision) gameState.getGameObject(objectID), gameState);
        }
    }

    private static void computeCollision(Collision actor, GameState gameState) {
        GameObject gameObject = CollisionChecker.collidesWith(actor, gameState);
        if (gameObject != null) {
            actor.onCollide(gameObject);
        }
    }

}
