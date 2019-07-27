package EightAM.asteroids;

import android.content.Context;
import android.graphics.Paint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import EightAM.asteroids.specs.BaseSpec;
import EightAM.asteroids.specs.BasicBulletSpec;
import EightAM.asteroids.specs.BasicShipSpec;
import EightAM.asteroids.specs.BigAlienSpec;
import EightAM.asteroids.specs.LargeAsteroidSpec;
import EightAM.asteroids.specs.SmallAsteroidSpec;

final class AssetLoader {
    static final List<BaseSpec> specList = Collections.unmodifiableList(
            Arrays.asList(new BasicBulletSpec(), new BasicShipSpec(), new BigAlienSpec(),
                    new LargeAsteroidSpec(), new SmallAsteroidSpec()));

    static void load(Context c) {
        BitmapStore bitmapStore = BitmapStore.getInstance();
        PaintStore paintStore = PaintStore.getInstance();
        paintStore.addPaint("ship", new Paint());
        paintStore.addPaint("asteroid", new Paint());
        paintStore.addPaint("bullet", new Paint());
        paintStore.addPaint("alien", new Paint());
        paintStore.addPaint("message", new Paint());
        for (BaseSpec spec : specList) {
            if (spec.bitMapName == null || spec.bitMapResourceID == 0) {
                continue; // some specs dont have bitmaps to load
            }
            bitmapStore.addVectorBitmap(c, spec.bitMapName, spec.bitMapResourceID, spec.dimensions,
                    spec.dimensionBitMapRatio);
            // we might want to add the paints stored in each spec
        }
    }
}
