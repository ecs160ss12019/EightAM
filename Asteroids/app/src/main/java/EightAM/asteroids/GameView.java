package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;

class GameView extends SurfaceView implements Runnable {
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private Thread thread;
    Drawable d;


    GameView(Context ctx) {
        this(ctx, null);
    }

    GameView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }

    GameView(Context ctx, AttributeSet attrs, int defStyleAttrs) {
        super(ctx, attrs, defStyleAttrs);
        init();
    }

    void init() {
        surfaceHolder = getHolder();
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int colorAccent = ContextCompat.getColor(getContext(), R.color.colorAccent);

    }

    @Override
    public void run() {
        while (isRunning) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas == null) return;
                canvas.drawColor(Color.WHITE);
                // game logic
                // TODO: Move this to correct part of code
                // we just wanted to draw the ship . haha

                // make a new ship just to test out drawing
                Ship mship = new Ship(canvas.getWidth(), canvas.getHeight(), getContext());
                Bitmap resized; // resize the image
                double scale = 0.2; // the % of the original image you want to resize to
                resized = Bitmap.createScaledBitmap(mship.getBitmap(),
                        (int)(mship.getBitmap().getWidth()*scale),
                        (int)(mship.getBitmap().getHeight()*scale),
                        true);
                // actually draw the ship
                // TODO: use drawBitmap defn that takes in Matrix
                canvas.drawBitmap(resized, mship.getHitBox().left, mship.getHitBox().top, paint);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void pause() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPause() {
        if (thread == null) return;
        pause();
        thread = null;
    }

    public void onResume() {
        if (thread != null) onPause();
        resume();
    }
}
