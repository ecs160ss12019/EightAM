package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public final class ImageUtils {
    // From https://stackoverflow.com/questions/33696488/getting-bitmap-from-vector-drawable
    public static Bitmap getVectorBitmap(Context context, int resID) {
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), resID,
                context.getTheme());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(
                    drawable)).mutate();
        }

        Bitmap result = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return result;
    }

    public static Bitmap getHitboxScaledBitmap(Bitmap bm, Point dimensions, float scale) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float newWidth = dimensions.x * scale;
        float newHeight = dimensions.y * scale;
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}

