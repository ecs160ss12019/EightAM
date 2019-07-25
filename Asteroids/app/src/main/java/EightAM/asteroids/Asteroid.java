package EightAM.asteroids;

import static EightAM.asteroids.Constants.ASTEROID_MEDIUM_RADIUS;
import static EightAM.asteroids.Constants.ASTEROID_SMALL_RADIUS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Destructable;
import EightAM.asteroids.specs.BaseAsteroidSpec;
import EightAM.asteroids.specs.LargeAsteroidSpec;

public class Asteroid extends GameObject implements Destructable {

    Bitmap bitmap;
    // ---------------Member variables-------------
    private Size rockSize;

    //    /**
    //     * First constructor constructs the asteroid rocks when there are no asteroids in
    //     * space, i.e. when there's a new game or when all asteroids in the field are
    //     * destroyed.
    //     *
    //     * Its velocity and position are random. However, its max possible velocity is
    //     * slower than that of smaller asteroids
    //     *
    //     * These asteroids only spawn when the currPlayerShip/player is a certain distance away
    //     * from the spawn point.
    //     *
    //     * @param xTotalPix - total horizontal pixels
    //     * @param yTotalPix - total vertical pixels
    //     * @param context   - Context for setting bitmap
    //     */
    //    protected Asteroid(GameModel gameModel, int xTotalPix, int yTotalPix, Ship ship, Context context) {
    //        float xRand, yRand;
    //        float xDistFromShip, yDistFromShip, DistFromShip;
    //        Random rand = new Random();
    //
    //        // might use later
    //
    //        // create large rock only
    //        rockSize = Size.LARGE;
    //
    //        // Prepare the bitmap
    //        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
    //        hitboxWidth = bitmap.getWidth() * 0.5f;
    //        hitboxHeight = bitmap.getHeight() * 0.5f;
    //
    //
    //        // We only want to spawn asteroids we are a certain distance away from the currPlayerShip
    //        // NOTE: May be inefficient, but more fair to the player.
    //        // Alternative: Only spawn asteroids close to the borders/outskirts of the
    //        // screen Alternative Implementation: new Random().nextInt((max-min+1))+min to
    //        // set bounds
    //        do {
    //            xRand = rand.nextInt(((xTotalPix - 1) + 1) + 1);
    //            yRand = rand.nextInt(((yTotalPix - 1) + 1) + 1);
    //            xDistFromShip = Math.abs(xRand - ship.getPosX());
    //            yDistFromShip = Math.abs(yRand - ship.getPosY());
    //            DistFromShip = (float) Math.sqrt(Math.pow(xDistFromShip, 2) + Math.pow(yDistFromShip, 2));
    //        } while (DistFromShip < SAFEDIST);
    //
    //        float speed = rand.nextFloat() * ASTEROID_MAXSPEED;
    //        float direction = Float.MIN_VALUE + rand.nextFloat() * (float) (ASTEROID_MAXANGLE - Float.MIN_VALUE);
    //        this.vel = new Velocity(speed, direction);
    //        this.setHitBox(xRand, yRand);
    //        this.paint = new Paint();
    //    }
    //
    //    /**
    //     * Second constructor constructs the asteroid rocks when there an asteroid of a
    //     * Size of LARGE or MEDIUM gets destroyed.
    //     *
    //     * These asteroids spawn in the wake of the destruction of its parent i.e. the
    //     * parent's position.
    //     *
    //     * Its velocity is randomized in accordance to its size. That is, smaller
    //     * asteroids, higher possible max speed.
    //     *
    //     * @param currentX   - current horizontal position of the parent asteroid
    //     * @param currentY   - current vertical position of the parent asteroid
    //     * @param parentSize - Size of parent
    //     * @param context    - Context for setting bitmap
    //     */
    //    protected Asteroid(float currentX, float currentY, Size parentSize, Context context) {
    //        this.paint = new Paint();
    //        Random rand = new Random();
    //        float direction = 1 + rand.nextFloat() * (float) (ASTEROID_MAXANGLE - 1);
    //        float speed = Float.MIN_VALUE + rand.nextFloat() * ((ASTEROID_MAXSPEED / 4) - Float.MIN_VALUE);
    //
    //        if (parentSize == Size.LARGE) {
    //            rockSize = Size.MEDIUM;
    //            speed /= 2;
    //
    //            // Prepare the bitmap
    //            // Load .png file in res/drawable
    //            // TODO: this asteroid figure subject to change
    //            bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
    //        } else {
    //            rockSize = Size.SMALL;
    //            speed /= 4;
    //
    //            /// Prepare the bitmap
    //            // Load .png file in res/drawable
    //            // TODO: this asteroid figure subject to change
    //            bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_asteroid_large);
    //        }
    //        this.vel = new Velocity(speed, direction);
    //        this.setHitBox(currentX, currentY);
    //    }

    Asteroid(BaseAsteroidSpec spec) {
        this.id = ObjectID.getNewID(Faction.Neutral);
        if (spec instanceof LargeAsteroidSpec) {
            this.rockSize = Size.LARGE;
        }
        this.paint = PaintStore.getInstance().getPaint(spec.paintName);
        this.bitmap = BitmapStore.getBitmap(spec.bitMapName);
        this.hitbox = new RectF(spec.initialPosition.x, spec.initialPosition.y, spec.dimensions.x, spec.dimensions.y);
        this.orientation = spec.initialOrientation;
        this.vel = new Velocity(0, 0, spec.speedRange.second);
    }

    Asteroid(Asteroid asteroid) {
        this.id = ObjectID.getNewID(Faction.Neutral);
        this.rockSize = asteroid.rockSize;
        this.paint = asteroid.paint;
        this.bitmap = asteroid.bitmap;
        this.hitbox = new RectF(asteroid.hitbox);
        this.orientation = asteroid.orientation;
        this.vel = new Velocity(asteroid.vel);
    }

    // ---------------Member methods---------------

    /**
     * Sets and/or updates the position of the hitbox of the asteroid
     */
    // TODO: Set RecF dependent on size of screen and position of Asteroid
    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);
        switch (rockSize) {
            case LARGE:
                hitbox.left -= hitboxWidth / 2;
                hitbox.top -= hitboxHeight / 2;
                hitbox.right += hitboxWidth / 2;
                hitbox.bottom += hitboxHeight / 2;
                break;
            case MEDIUM:
                hitbox.left -= ASTEROID_MEDIUM_RADIUS;
                hitbox.top -= ASTEROID_MEDIUM_RADIUS;
                hitbox.right += ASTEROID_MEDIUM_RADIUS;
                hitbox.bottom += ASTEROID_MEDIUM_RADIUS;
                break;
            case SMALL:
                hitbox.left -= ASTEROID_SMALL_RADIUS;
                hitbox.top -= ASTEROID_SMALL_RADIUS;
                hitbox.right += ASTEROID_SMALL_RADIUS;
                hitbox.bottom += ASTEROID_SMALL_RADIUS;
                break;
        }
    }

    //    /**
    //     * Got call when an asteroid explodes
    //     */
    //    void explode(Context context, ArrayList<Asteroid> asteroidsBelt) {
    //        // Get size as int of the parent asteroid
    //        float temp = 0.25f * 1;
    //
    //        // Call the asteroid constructor for explosion
    //        Asteroid firstChild, secondChild;
    //        firstChild = new Asteroid(this.hitbox.centerX() - temp, this.hitbox.centerY(), rockSize, context);
    //        secondChild = new Asteroid(this.hitbox.centerX() + temp, this.hitbox.centerY(), rockSize, context);
    //
    //        // Add the children into the asteroid belt
    //        asteroidsBelt.add(firstChild);
    //        asteroidsBelt.add(secondChild);
    //    }

    @Override
    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void destruct() {
        // implement destruction effects here.
    }

    // enum size used to denote three size types of asteroid rock
    enum Size {
        SMALL, MEDIUM, LARGE
    }
}
