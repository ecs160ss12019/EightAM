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

/**
 * GameView passively takes the information and data from GameModel
 * and displays it to the user.
 */
class GameView extends SurfaceView implements Runnable {

    private Paint defaultPaint;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private Thread thread;
    private GameModel model;
    private Canvas canvas;


    GameView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }


    void init() {
        surfaceHolder = getHolder();
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        defaultPaint = PaintStore.getInstance().getPaint("font_paint");
        defaultPaint.setColor(colorPrimary);
        defaultPaint.setStyle(Paint.Style.FILL);
        defaultPaint.setAntiAlias(true);
    }

    /**
     * This is the main run method to display the objects and the HUD to
     * the player.
     * The accessing of the GameModel is mutually exclusive with the GameController
     */
    @Override
    public void run() {
        while (isRunning) {
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) return;
                canvas.drawColor(Color.BLACK);
                model.getLock().lock();
                try {
                    drawObjects(canvas);
                    Messages.draw(canvas);
                    model.stats.drawAttributes(canvas, defaultPaint, getContext());
                } finally {
                    model.getLock().unlock();
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    void drawObjects(Canvas canvas) {
        for (GameObject object : model.objectMap.values()) object.draw(canvas);
    }


    /**
     * onPause stop the thread which controls when the run method execute
     */
    void onPause() {
        if (thread == null) return;
        pause();
        thread = null;
    }

    /**
     * onResume stop the thread which controls when the run method execute
     */
    void onResume() {
        if (thread != null) onPause();
        resume();
    }

    void pause() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // debugging code
            Log.e("Exception", "onPause()" + e.getMessage());
        }
    }

    void resume() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    void setGameModel(GameModel gameModel) {
        model = gameModel;
    }
}
