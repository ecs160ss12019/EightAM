package EightAM.asteroids;

import android.content.Context;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

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
        int colorPrimary = ContextCompat.getColor(c, R.color.colorPrimary);
        int colorAccent = ContextCompat.getColor(c, R.color.colorAccent);
        Paint defaultPaint = new Paint();
        defaultPaint.setColor(colorPrimary);
        defaultPaint.setStyle(Paint.Style.FILL);
        defaultPaint.setAntiAlias(true);
        BitmapStore bitmapStore = BitmapStore.getInstance();
        PaintStore paintStore = PaintStore.getInstance();
        paintStore.addPaint("ship", new Paint(defaultPaint));
        paintStore.addPaint("asteroid", new Paint(defaultPaint));
        paintStore.addPaint("bullet", new Paint(defaultPaint));
        paintStore.addPaint("alien", new Paint(defaultPaint));
        paintStore.addPaint("message", new Paint(defaultPaint));
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
