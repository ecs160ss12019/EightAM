package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public final class ImageUtils {

    public static Bitmap getVectorBitmap(Context context, @DrawableRes int id) {
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), id, context.getTheme());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) drawable = (DrawableCompat.wrap(drawable)).mutate();

        Bitmap result = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return result;
    }

    public static Bitmap gradientBitmap(Bitmap src, int color1, int color2) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height, color1, color2, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);

        return result;
    }

    public static Bitmap getHitboxScaledBitmap(Bitmap bm, RectF hitbox, float scale) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float newWidth = hitbox.width() * scale;
        float newHeight = hitbox.height() * scale;
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

