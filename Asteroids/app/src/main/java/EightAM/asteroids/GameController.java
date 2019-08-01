package EightAM.asteroids;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import EightAM.asteroids.interfaces.GameOverListener;

/**
 * GameController modifies and updates the state of the GameModel
 * by providing with a "sense" of time, i.e. it's the game loop without
 * the business logic
 */
final class GameController implements Runnable {
    private Thread thread;
    private long currentTick;
    private boolean isRunning;
    private GameModel model;
    private GameOverListener gameOverListener;

    GameController(GameModel gameModel, GameOverListener listener) {
        // Initialize objects here
        currentTick = 0;
        isRunning = false;
        model = gameModel;
        this.gameOverListener = listener;
    }

    /**
     * The main run function of the GameController. It sends user input, and
     * time passed, to the GameModel, such that the GameModel will use the
     * information to update its state.
     *
     * The passing in of the information and data is mutually exclusive with
     * GameView.
     */
    @Override
    public void run() {
        currentTick = SystemClock.elapsedRealtime();
        while (isRunning && !model.isGameOver()) {
            long time = SystemClock.elapsedRealtime();
            long delta = time - currentTick;
            if (delta > 0) {
                model.getLock().lock();
                try {
                    model.input(InputControl.playerInput);
                    model.update(delta);
                } finally {
                    model.getLock().unlock();
                }
                currentTick = time;
            }
        }
        if (model.stats.livesLeft == 0) {
            new Handler(Looper.getMainLooper()).post(() -> {
                gameOverListener.onGameOver();
            });
        }
        isRunning = false;
    }

    void pause() {
        isRunning = false;
        this.model.audioListener.offMusic();
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    void resume() {
        isRunning = true;
        this.model.audioListener.onMusic();
        thread = new Thread(this);
        thread.start();
    }

    void onPause() {
        if (thread == null) return;
        pause();
        thread = null;
    }

    void onResume() {
        if (thread != null) onPause();
        resume();
    }
}
