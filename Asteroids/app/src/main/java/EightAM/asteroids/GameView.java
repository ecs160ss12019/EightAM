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
    private Paint defaultPaint;
    private Paint invulnerablePaint;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private Thread thread;
    private GameModel model;
    private Canvas canvas;

    //for sounds
    public AudioUtility audio;

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
        defaultPaint = new Paint();
        defaultPaint.setColor(colorPrimary);
        defaultPaint.setStyle(Paint.Style.FILL);
        defaultPaint.setAntiAlias(true);
        invulnerablePaint = new Paint();
        invulnerablePaint.setColor(Color.YELLOW);
        invulnerablePaint.setStyle(Paint.Style.FILL);
        invulnerablePaint.setAntiAlias(true);

        // For audio
        audio = new AudioUtility(getContext());
    }

    @Override
    public void run() {
        while (isRunning) {

            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) return;

                // make a new playerShip just to test out drawing
                //                playerShip.draw(canvas, defaultPaint);
                canvas.drawColor(Color.BLACK);
                model.lock.lock();
                try {
                    if (model.isPaused) {
                        Log.d("in gameview","paused");
                        drawPaused(canvas);
                        onPause();
                    }

                    // Drawings
                    drawShip(canvas, defaultPaint);
                    drawBullets(canvas, defaultPaint);
                    drawAsteroidBelt(canvas, defaultPaint);
                    if (model.alien != null) model.alien.draw(canvas, defaultPaint);

                    // Sound effects
                    this.audio.playInputPress(
                            InputControl.playerInput.UP,
                            InputControl.playerInput.DOWN,
                            InputControl.playerInput.LEFT,
                            InputControl.playerInput.RIGHT,
                            InputControl.playerInput.SPECIAL_1);
                } finally {
                    model.lock.unlock();
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    void drawBullets(Canvas canvas, Paint paint) {
        for (GameObject object : model.bulletsFired) object.draw(canvas, paint);
    }

    void drawAsteroidBelt(Canvas canvas, Paint paint) {
        for (GameObject object : model.asteroidBelt) object.draw(canvas, paint);
    }

    void drawShip(Canvas canvas, Paint paint) {
        if (model.playerShip != null) model.playerShip.draw(canvas, paint);
    }

    void drawPaused(Canvas canvas) {
        canvas.drawColor(Color.argb(0,0,0,0));
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
