package EightAM.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

class GameView extends SurfaceView implements Runnable {

    // ---------------Member variables-------------

    Ship ship;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private Thread thread;
    private GameModel model;

    // ---------------Member methods---------------

    /**
     * Constructors
     */
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
        paint = new Paint();
        paint.setColor(colorPrimary);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

    }

    @Override
    public void run() {
        while (isRunning) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas == null) return;
                // TODO: Move this to correct part of code
                // we just wanted to draw the ship . haha

                // make a new ship just to test out drawing
                //                ship.draw(canvas, paint);
                canvas.drawColor(Color.BLACK);
                model.lock.lock();
                try {
                    //                    if (Ship.lastLogMessage > 5000) {
                    //                        Log.d("gameView", "rendering ship");
                    //                    }
                    model.ship.draw(canvas, paint);
                } finally {
                    model.lock.unlock();
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * onPause stop the thread which controls when the run method execute
     */
    public void onPause() {
        if (thread == null) return;
        pause();
        thread = null;
    }

    /**
     * onResume stop the thread which controls when the run method execute
     */
    public void onResume() {
        if (thread != null) onPause();
        resume();
    }

    public void pause() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // debugging code
            Log.e("Exception", "onPause()" + e.getMessage());
        }
    }

    public void resume() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }


    public void setGameModel(GameModel gameModel) {
        model = gameModel;
    }
}
