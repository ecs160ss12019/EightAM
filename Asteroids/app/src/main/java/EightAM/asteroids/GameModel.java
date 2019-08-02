package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_PROB_INC;
import static EightAM.asteroids.Constants.ALIEN_SPAWN_PROB;
import static EightAM.asteroids.Constants.BOUNDARY_OFFSET;
import static EightAM.asteroids.Constants.BOUNDARY_SHRINK_RATE;
import static EightAM.asteroids.Constants.STARTING_ASTEROIDS;
import static EightAM.asteroids.Constants.STARTING_MAX_ALIENS;
import static EightAM.asteroids.Constants.STARTING_MAX_POWERUPS;
import static EightAM.asteroids.Constants.WAVE_GRACE_PERIOD;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.AIModule;
import EightAM.asteroids.interfaces.AudioGenerator;
import EightAM.asteroids.interfaces.AudioListener;
import EightAM.asteroids.interfaces.Collision;
import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.interfaces.EventGenerator;
import EightAM.asteroids.interfaces.EventHandler;
import EightAM.asteroids.interfaces.GameState;
import EightAM.asteroids.interfaces.Scoreable;
import EightAM.asteroids.interfaces.Shooter;
import EightAM.asteroids.interfaces.ShotListener;
import EightAM.asteroids.specs.BaseLootSpec;
import EightAM.asteroids.specs.RandomLootSpec;
import EightAM.asteroids.specs.TeleportShipSpec;

/**
 * GameModel holds the main business logic of the game. It decides
 * what other functions to invoke, and what conditions to invoke them.
 * Basically, it is the main game logic
 */
public class GameModel implements GameState, EventHandler, ShotListener, AudioGenerator {

    GameStats stats;
    Wave wave;
    private Lock lock;
    RectF boundaries;
    RectF spawnBoundaries;
    Map<ObjectID, GameObject> objectMap;
    Set<ObjectID> collideables;
    Set<ObjectID> adversaries;
    List<GameObject> createList;
    List<ObjectID> deleteList;

    ObjectID currPlayerShip;
    private boolean gameOver;

    AudioListener audioListener;

    /**
     * Main Constructor of the Model. Called at the start of every game session.
     */
    public GameModel(Point screenSize) {
        //TODO: Initialize Grid... Maybe?
        lock = new ReentrantLock();
        boundaries = new RectF(0, 0, screenSize.x, screenSize.y);
        spawnBoundaries = new RectF(boundaries.left - BOUNDARY_OFFSET,
                boundaries.top - BOUNDARY_OFFSET, boundaries.right + BOUNDARY_OFFSET,
                boundaries.bottom + BOUNDARY_OFFSET);
        objectMap = new HashMap<>();
        collideables = new HashSet<>();
        adversaries = new HashSet<>();
        createList = new ArrayList<>();
        deleteList = new ArrayList<>();

        this.gameOver = false;
        this.stats = new GameStats(boundaries);
    }

    /**
     * Initializes all parameters needed to start the game.
     */
    void startGame() {
        this.gameOver = false;
        resetObjects();
        stats.newGame();
        wave = new Wave(this, boundaries, spawnBoundaries, STARTING_MAX_ALIENS, STARTING_ASTEROIDS,
                STARTING_MAX_POWERUPS, WAVE_GRACE_PERIOD, ALIEN_SPAWN_PROB, ALIEN_PROB_INC);
        wave.registerAudioListener(audioListener);
        addObjects(respawnShip());
        putObjects();
    }

    /**
     * Reset stats and playing field. i.e. the objects in them.
     */
    private void resetObjects() {
        this.stats = new GameStats(boundaries);
        objectMap.clear();
        this.currPlayerShip = null;
    }

    /**
     * Respawns the ship by returning its instance within a collection singleton
     */
    private Collection<GameObject> respawnShip() {
        GameObject ship = BaseFactory.getInstance().create(new TeleportShipSpec());
        ship.hitbox.offsetTo(boundaries.width() / 2.0f, boundaries.height() / 2.0f);
        return Collections.singleton(ship);
    }


    public void removeObjects() {
        GameObject objectToDel;
        for (ObjectID id : deleteList) {
            objectToDel = objectMap.get(id);
            if (objectToDel instanceof Collision) {
                collideables.remove(id);
            }
            if (objectToDel instanceof AIModule) {
                adversaries.remove(id);
            }
            objectMap.remove(id);
            if (id == currPlayerShip) onDeath();
        }
        deleteList.clear();
    }

    public void onGameEnd() {
        this.gameOver = true;
    }

    public void onDeath() {
        currPlayerShip = null;
        stats.subLive();
        if (stats.getLife() > 0) {
            addObjects(respawnShip());
        } else {
            onGameEnd();
        }
    }

    /**
     * Updates the Game, i.e. move objects, check collisions, create new waves
     *
     * @param timeInMillisecond - time interval between frames, to be used
     *                          for moving objects.
     */
    void update(long timeInMillisecond) {
        putObjects();

        for (ObjectID i : adversaries) {
            ((AIModule) objectMap.get(i)).processGameState(this);
        }

        for (GameObject o : objectMap.values()) {
            o.update(timeInMillisecond);
        }

        for (Pair<Collision, GameObject> objectPair : CollisionChecker.enumerateCollisions(this)) {
            objectPair.first.onCollide(objectPair.second);
        }

        // there's an annoying bug where sometimes Wave object is not instantiated
        if (wave == null) {
            wave = new Wave(this, boundaries, spawnBoundaries, STARTING_MAX_ALIENS,
                    STARTING_ASTEROIDS, STARTING_MAX_POWERUPS, WAVE_GRACE_PERIOD, ALIEN_SPAWN_PROB,
                    ALIEN_PROB_INC);
        }

        wave.updateDuration(timeInMillisecond);

        removeObjects();
    }

    /**
     * @param i input by user.
     * @return if to pause game.
     */
    // Indirection of input to update currPlayerShip parameters
    void input(InputControl.Input i) {
        if (currPlayerShip != null) {
            getPlayerShip().input(i);
        }
    }

    public Ship getPlayerShip() {
        if (currPlayerShip != null && objectMap.containsKey(currPlayerShip)) {
            return (Ship) objectMap.get(currPlayerShip);
        } else {
            return null;
        }
    }

    @Override
    public Set<ObjectID> getCollideableIDs() {
        return collideables;
    }

    @Override
    public GameObject getGameObject(ObjectID id) {
        return objectMap.get(id);
    }

    /**
     * Adds objects to the create list
     *
     * @param objects - a collection of objects to be passed in by
     *                the generators
     */
    private void addObjects(Collection<GameObject> objects) {
        createList.addAll(objects);
    }

    /**
     * Transfers the objects on the create list to objectMap
     */
    private void putObjects() {
        for (GameObject o : createList) {
            ObjectID id = o.getID();
            if (!objectMap.containsKey(id)) {
                objectMap.put(id, o);
                if (o instanceof Collision) collideables.add(id);
                if (o instanceof AIModule) {
                    adversaries.add(id);
                } else if (o instanceof Ship) currPlayerShip = id;
                addListeners(o);
                addMoveStrategy(o);
                updateWaveParam(o, 1);
            }
        }
        createList.clear();
    }

    /**
     * Updates the parameters of the game depending on what object
     * is add/del to the game.
     *
     * @param object - the GameObject to check
     */
    private void updateWaveParam(GameObject object, int i) {
        if (object instanceof Asteroid) {
            wave.updateAsteroids(i);
        } else if (object instanceof Alien) {
            wave.updateAliens(i);
        } else if (object instanceof Loot) {
            wave.updatePowerups(i);
        }
    }

    /**
     * Links/Registers the listeners for the GameObject
     * dependent on their Interfaces
     *
     * @param object - the GameObject to check
     */
    private void addListeners(GameObject object) {
        ((EventGenerator) object).registerEventHandler(this);
        if (object instanceof Shooter) {
            ((Shooter) object).linkShotListener(this);
        }
        if (object instanceof AudioGenerator) {
            ((AudioGenerator) object).registerAudioListener(audioListener);
        }
    }

    /**
     * To set the strategy patter of the object that is passed in.
     * Only Aliens and Asteroids have their move strategy altered upon
     * spawning.
     * This is so that these objects naturally drift in from the fringes
     * of space. Switching move strategies once they're in the play area.
     * That is, they start to wrap around the screen.
     */
    private void addMoveStrategy(GameObject object) {
        if (object instanceof Alien || object instanceof Asteroid) {
            object.setMoveStrategy(
                    new MoveWrapWithSpawn(boundaries, spawnBoundaries, BOUNDARY_SHRINK_RATE));
        } else {
            object.setMoveStrategy(new MoveWrap(boundaries));
        }
    }

    /**
     * Creates debris from an object (unless it is a bullet or a particle).
     * All other objects produces particles as debris.
     * Asteroids, if they're big enough, breaks up into smaller ones.
     */
    private void createDebris(GameObject object) {
        if (!((object instanceof Particle) || (object instanceof Bullet))) {
            addObjects(ParticleGenerator.getInstance().createParticles(object.getObjPos()));
        }
        if (object instanceof Asteroid) {
            addObjects(AsteroidGenerator.breakUpAsteroid((Asteroid) object));
        }
    }

    @Override
    public void onDestruct(Destructable destructable) {
        ObjectID id = destructable.getID();
        deleteList.add(id);
        createDebris((GameObject) destructable);
        updateWaveParam((GameObject) destructable, -1);
    }

//    /**
//     * Plays the explosion sound effects from a destructable object.
//     * As each instance of an object has a different sound.
//     */
//    private void playExplosion(GameObject object) {
//        if (object instanceof Asteroid) {
//            if (((Asteroid) object).breaksInto instanceof MediumAsteroidSpec) {
//                audioListener.onLargeAsteroidExplosion();
//            } else if (((Asteroid) object).breaksInto instanceof SmallAsteroidSpec) {
//                audioListener.onMediumAsteroidExplosion();
//            } else {
//                audioListener.onSmallAsteroidExplosion();
//            }
//        } else if (object instanceof Alien) {
//            audioListener.onAlienExplosion();
//        }
//    }

    @Override
    public void onShotFired(Shooter shooter) {
        if (shooter.canShoot()) {
            addObjects(shooter.getWeapon().fire(shooter));
        }
    }

    @Override
    public void createObjects(Collection<GameObject> objects) {
        addObjects(objects);
    }

    @Override
    public void destroyObjects(Collection<Destructable> objects, ObjectID killer) {
        for (Destructable d : objects) {
            DestroyedObject destroyedObject = null;
            if (killer != null && d instanceof GameObject && d instanceof Scoreable) {
                destroyedObject = new DestroyedObject(((Scoreable) d).score(), d.getID(), killer,
                        (GameObject) d);
            }
            d.destruct(destroyedObject);
        }
    }

    /**
     * The business logic for teleporting objects. This is mainly
     * used for HyperSpace, the "panic button"
     */
    @Override
    public void teleportObjects(Collection<ObjectID> tpList) {
        for (ObjectID objectID : tpList) {
            GameObject obj = objectMap.get(objectID);
            if (obj != null) {
                obj.hitbox.offsetTo(GameRandom.randomFloat(boundaries.left, boundaries.right),
                        GameRandom.randomFloat(boundaries.bottom, boundaries.top));
            }
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    Lock getLock() {
        return lock;
    }

    @Override
    public void processScore(DestroyedObject object) {
        if (object.getKiller().getFaction() == Faction.Player) {
            stats.plusScore(object.score());
            /*
            Messages.addMessage(
                    new Messages.MessageWithFade(object.score() + "+", Color.WHITE, 500,
                            Messages.FontSize.Small, Messages.HorizontalPosition.Center,
                            Messages.VerticalPosition.Bottom, 500));
            */
        }
    }

    @Override
    public void sendMessage(Messages.Message message) {
        Messages.addMessage(message);
    }

    @Override
    public void givePrimaryWeapon(Weapon weapon) {
        weapon.registerAudioListener(audioListener);
        getPlayerShip().primaryWeapon = weapon;
        Messages.addMessage(
                new Messages.MessageWithFade(weapon.name + " equipped", Color.WHITE, 1500,
                        Messages.FontSize.Small,
                        Messages.HorizontalPosition.Center, Messages.VerticalPosition.Bottom,
                        1500));
    }

    @Override
    public void incrementLife(int amount) {
        Messages.addMessage(
                new Messages.MessageWithFade(amount + " life gained", Color.WHITE, 1500,
                        Messages.FontSize.Small,
                        Messages.HorizontalPosition.Center, Messages.VerticalPosition.Bottom,
                        1500));
        stats.livesLeft++;
    }

    @Override
    public void giveInvincibility(int duration) {
        getPlayerShip().invDurationTimer.resetTimer(duration);
        Messages.addMessage(
                new Messages.MessageWithFade(duration / 1000 + "s Invincibility", Color.WHITE, 1500,
                        Messages.FontSize.Small,
                        Messages.HorizontalPosition.Center, Messages.VerticalPosition.Bottom,
                        1500));
    }

    @Override
    public void createLoot(BaseLootSpec spec) {
        if (spec instanceof RandomLootSpec) {
            addObjects(Collections.singleton(
                    LootGenerator.createRandomLootRandomly(boundaries, (RandomLootSpec) spec))
            );
        }
    }

    @Override
    public void registerAudioListener(AudioListener listener) {
        this.audioListener = listener;
    }
}
