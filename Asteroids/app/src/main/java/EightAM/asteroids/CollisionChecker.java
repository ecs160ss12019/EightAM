package EightAM.asteroids;

import android.util.Pair;

import androidx.collection.ArraySet;

import java.util.Collection;

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

    /**
     * Alien collides with: Bullet(other factions), Asteroid, Ship
     * Asteroid collides with: Bullet(other factions), Alien, Ship
     * Bullet collides with: Bullet(other factions), Alien(if on other factions), Ship(if on other
     * faction), Asteroid
     * Loot collides with: Ship
     * Particle collides with: None
     * Ship collides with: Bullet(other factions), Alien, Asteroid
     */
    static boolean shouldICollide(Collision me, Collision them) {
        if (me.canCollide() && them.canCollide() && !me.getID().equals(them.getID())) {
            boolean shouldTestCollision = false;
            if (me instanceof AbstractAlien) {
                if (me.getID().getFaction() != them.getID().getFaction() && !(them instanceof Loot
                        || them instanceof Particle)) {
                    shouldTestCollision = true;
                }
            } else if (me instanceof Asteroid) {
                // Loot and Particles are both neutral faction
                if (me.getID().getFaction() != them.getID().getFaction()) {
                    shouldTestCollision = true;
                }
            } else if (me instanceof Bullet) {
                if (me.getID().getFaction() != them.getID().getFaction() && !(them instanceof Loot
                        || them instanceof Particle)) {
                    shouldTestCollision = true;
                }
            } else if (me instanceof Loot) {
                shouldTestCollision = (them.getID().getFaction() == Faction.Player) && (them instanceof Ship);
            } else if (me instanceof Ship) {
                if (((Ship) me).isInvulnerable()) {
                    shouldTestCollision = false;
                } else if (them.getID().getFaction() != me.getID().getFaction()
                        && !(them instanceof Particle || them instanceof Loot)) {
                    shouldTestCollision = true;
                }
            }
            if (shouldTestCollision) {
                return me.detectCollisions((GameObject) them);
            }
        }
        return false;
    }

    private static GameObject collidesWith(Collision actor, GameState gameState) {
        for (ObjectID collideableID : gameState.getCollideableIDs()) {
            GameObject object = gameState.getGameObject(collideableID);
            if (shouldICollide(actor, (Collision) object)) return object;
        }
        return null;
    }

    public static Collection<Pair<Collision, GameObject>> enumerateCollisions(GameState gameState) {
        Collection<Pair<Collision, GameObject>> collisions = new ArraySet<>();
        for (ObjectID objectID : gameState.getCollideableIDs()) {
            Pair<Collision, GameObject> temp = computeCollision(
                    (Collision) gameState.getGameObject(objectID), gameState);
            if (temp != null) {
                collisions.add(temp);
            }
        }
        return collisions;
    }

    private static Pair<Collision, GameObject> computeCollision(Collision actor,
            GameState gameState) {
        GameObject gameObject = CollisionChecker.collidesWith(actor, gameState);
        if (gameObject != null) {
            return new Pair<>(actor, gameObject);
        }
        return null;
    }

}
