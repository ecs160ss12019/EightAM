package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import androidx.annotation.DrawableRes;

import java.util.HashMap;
import java.util.Map;

final class BitmapStore {
    private static Map<String, Bitmap> mBitmapsMap;
    private static Context context;

    private BitmapStore() { }

    // Calling this method is the only way to get a BitmapStore
    static void initialize(Context context) {
        BitmapStore.context = context;
        mBitmapsMap = new HashMap<>();
    }

    static Bitmap getBitmap(String bitmapName) {
        if (mBitmapsMap.containsKey(bitmapName)) {
            return mBitmapsMap.get(bitmapName);
        } else {
            return null;
        }
    }

    static void addVectorBitmap(Context c, String bitmapName, @DrawableRes int id, RectF objectSize, int scale) {
        Bitmap bitmap;
        // Make a resource id out of the string of the file name
        bitmap = ImageUtils.getVectorBitmap(c, id);
        // Resize the bitmap
        bitmap = ImageUtils.getHitboxScaledBitmap(bitmap, objectSize, scale);
        mBitmapsMap.put(bitmapName, bitmap);
    }

    static void clearStore() {
        for (Bitmap bm : mBitmapsMap.values()) {
            bm.recycle();
        }
        mBitmapsMap.clear();
    }
}
