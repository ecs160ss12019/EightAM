package EightAM.asteroids;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import EightAM.asteroids.interfaces.GameOverListener;

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


    @Override
    public void run() {
        currentTick = SystemClock.elapsedRealtime();
        while (isRunning && !model.isGameOver()) {
            long time = SystemClock.elapsedRealtime();
            long delta = time - currentTick;
            if (delta > 0) {
                model.getLock().lock();
                try {
                    // Get player input
                    model.input(InputControl.playerInput);
                    // Update model
                    model.update(delta);

                    // Detect Game Over
                } finally {
                    model.getLock().unlock();
                }
                currentTick = time;
            }
        }
        if (model.isGameOver()) {
            new Handler(Looper.getMainLooper()).post(() -> {
                //Log.d("this is it", "Kenny!");
                gameOverListener.onGameOver();
            });
        }
        isRunning = false;
    }

    Scoreboard outputStats() {
        return model.endGameStats();
    }

    void pause() {
        isRunning = false;
        Log.d("in game controller", "paused game");
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    void resume() {
        isRunning = true;
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
