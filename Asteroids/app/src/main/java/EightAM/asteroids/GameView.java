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

                Ship mship = new Ship(canvas.getWidth(), canvas.getHeight(), getContext());
                Bitmap resized;
                resized = Bitmap.createScaledBitmap(mship.getBitmap(),(int)(mship.getBitmap().getWidth()*0.8), (int)(mship.getBitmap().getHeight()*0.8), true);
                canvas.drawBitmap(resized, mship.getHitBox().left, mship.getHitBox().top, paint);
                /*d = new BitmapDrawable(getResources(), mship.bitmap);
                d.setBounds(100, 200, 100, 200);
                d.draw(canvas); */

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
