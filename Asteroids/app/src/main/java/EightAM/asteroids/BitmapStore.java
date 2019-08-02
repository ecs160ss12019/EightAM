package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import androidx.annotation.DrawableRes;

import java.util.HashMap;
import java.util.Map;

/**
 * BitmapStore stores the extrinsic attributes for the GameObjects
 * By doing so, the GameObjects also exhibit elements of the
 * Flyweight pattern.
 */
final class BitmapStore {
    private static Map<String, Bitmap> mBitmapsMap;
    private static BitmapStore instance;

    private BitmapStore() {
        mBitmapsMap = new HashMap<>();
    }

    // Calling this method is the only way to get a BitmapStore
    static BitmapStore getInstance() {
        if (instance == null) instance = new BitmapStore();
        return instance;
    }

    Bitmap getBitmap(String bitmapName) {
        if (mBitmapsMap.containsKey(bitmapName)) {
            return mBitmapsMap.get(bitmapName);
        } else {
            return null;
        }
    }

    void addVectorBitmap(Context c, String bitmapName, @DrawableRes int id, Point objectSize,
            float scale) {
        Bitmap bitmap;
        // Make a resource id out of the string of the file name
        bitmap = ImageUtils.getVectorBitmap(c, id);
        // Resize the bitmap
        bitmap = ImageUtils.getHitboxScaledBitmap(bitmap, objectSize, scale);
        mBitmapsMap.put(bitmapName, bitmap);
    }

    void clearStore() {
        for (Bitmap bm : mBitmapsMap.values()) {
            bm.recycle();
        }
        mBitmapsMap.clear();
    }
}
