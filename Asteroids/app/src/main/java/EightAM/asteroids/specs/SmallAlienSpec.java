package EightAM.asteroids.specs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import EightAM.asteroids.R;
import EightAM.asteroids.Rotation;
import EightAM.asteroids.Velocity;

public class SmallAlienSpec extends BaseAlienSpec implements AudioSpec {
    public static int pointValue = 100;
    public static int hitPoints = 1;
    public static float maxSpeed = 0.25f;
    public static float initialAngle = (float) Math.PI * 0.25f;

    public static String tag = "alien_small";
    public static Point dimensions = new Point(40, 40);
    public static Point initialPosition = new Point(0, 0);
    public static Velocity initialVelocity = new Velocity(0, 0, maxSpeed);
    public static Rotation initialRotation = new Rotation(initialAngle, 0);

    public static float accuracy = 80;

    public static int paintColor = Color.BLUE;
    public static Paint.Style paintStyle = Paint.Style.FILL;

    public static int resID = R.drawable.ic_small_alien;
    public static float dbmRatio = 2f;

    public static Pair<Integer, Integer> _turnDelayRange = new Pair<>(1500, 2000);
    public static Pair<Integer, Integer> _shotDelayRange = new Pair<>(1000, 1500);

    public static BaseWeaponSpec weaponSpec = new SmallAlienWeaponSpec();

    public Pair<Integer, Integer> turnDelayRange;
    public Pair<Integer, Integer> shotDelayRange;

    // sound resIDs
    public int explosion = R.raw.alien_explosion;

    public SmallAlienSpec(String tag, Point dimensions, Point initialPosition,
            Velocity initialVelocity, Rotation initialRotation, int bitMapResourceID,
            float dimensionBitMapRatio, int pointValue,
            int hitPoints, BaseWeaponSpec weaponSpec,
            Pair<Integer, Integer> turnDelayRange,
            Pair<Integer, Integer> shotDelayRange) {
        super(tag, dimensions, initialPosition, initialVelocity, initialRotation, bitMapResourceID,
                dimensionBitMapRatio, pointValue, hitPoints, weaponSpec);
        this.turnDelayRange = turnDelayRange;
        this.shotDelayRange = shotDelayRange;
    }

    public SmallAlienSpec() {
        this(tag, dimensions, initialPosition, initialVelocity, initialRotation, resID, dbmRatio,
                pointValue, hitPoints, weaponSpec, _turnDelayRange,
                _shotDelayRange);
        setLootOnDeath(new RandomLootSpec());
        setExplosionID(explosion);
    }

    @Override
    public String getTag() {
        return tag;
    }
}
