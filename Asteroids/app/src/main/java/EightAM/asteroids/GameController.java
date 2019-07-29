package EightAM.asteroids;

import android.os.SystemClock;
import android.util.Log;

final class GameController implements Runnable {
    private Thread thread;
    private long currentTick;
    private boolean isRunning;
    private GameModel model;

    GameController(GameModel gameModel) {
        // Initialize objects here
        currentTick = 0;
        isRunning = false;
        model = gameModel;
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
                } finally {
                    model.getLock().unlock();
                }
                currentTick = time;
            }
        }
        isRunning = false;
    }

    EndGameStats outputStats() {
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
