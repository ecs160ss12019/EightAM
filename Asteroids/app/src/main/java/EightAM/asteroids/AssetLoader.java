package EightAM.asteroids;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import EightAM.asteroids.specs.BaseBitmapSpec;
import EightAM.asteroids.specs.BaseObjectSpec;
import EightAM.asteroids.specs.BasicBulletSpec;
import EightAM.asteroids.specs.BasicParticleSpec;
import EightAM.asteroids.specs.BasicShipSpec;
import EightAM.asteroids.specs.BigAlienSpec;
import EightAM.asteroids.specs.LargeAsteroidSpec;
import EightAM.asteroids.specs.MediumAsteroidSpec;
import EightAM.asteroids.specs.RandomLootSpec;
import EightAM.asteroids.specs.SlowLongBulletSpec;
import EightAM.asteroids.specs.SmallAlienSpec;
import EightAM.asteroids.specs.SmallAsteroidSpec;

final class AssetLoader {
    static final List<BaseObjectSpec> specList = Collections.unmodifiableList(
            Arrays.asList(new BasicBulletSpec(), new BasicParticleSpec(), new BasicShipSpec(),
                    new BigAlienSpec(), new SmallAlienSpec(), new LargeAsteroidSpec(),
                    new MediumAsteroidSpec(), new SmallAsteroidSpec(), new SlowLongBulletSpec(),
                    new RandomLootSpec()));

    static void load(Context c) {
        BitmapStore bitmapStore = BitmapStore.getInstance();
        PaintStore paintStore = PaintStore.getInstance();
        for (BaseObjectSpec baseSpec : specList) {
            if (baseSpec instanceof BaseBitmapSpec) {
                BaseBitmapSpec spec = (BaseBitmapSpec) baseSpec;
                bitmapStore.addVectorBitmap(c, spec.tag, spec.bitMapResourceID,
                        spec.dimensions, spec.dimensionBitMapRatio);
            }
            try {
                @ColorInt
                int paintColor = (int) baseSpec.getClass().getField("paintColor").get(null);
                Paint.Style paintStyle = (Paint.Style) baseSpec.getClass().getField(
                        "paintStyle").get(null);
                Paint paint = new Paint();
                paint.setStyle(paintStyle);
                paint.setColor(paintColor);
                paint.setAntiAlias(true);
                Log.d(AssetLoader.class.getCanonicalName(),
                        "Loaded: " + baseSpec.tag + ", Color: " + paintColor + ", Style: "
                                + paintStyle.toString());
                paintStore.addPaint(baseSpec.tag, paint);
            } catch (Exception e) {
                Log.d(AssetLoader.class.getCanonicalName(), "Failed to load: " + baseSpec.tag);
            }
        }
    }
}
