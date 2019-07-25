package EightAM.asteroids;

import android.os.SystemClock;
import android.util.Log;

final class GameController implements Runnable {
    private Thread thread;
    private long currentTick;
    private boolean isRunning;
    private GameModel model;

    GameController(GameModel gameModel, int width, int height) {
        // Initialize objects here
        currentTick = 0;
        isRunning = false;
        model = gameModel;
    }


    @Override
    public void run() {
        while (isRunning && !model.gameOver) {
            long time = SystemClock.elapsedRealtime();
            long delta = time - currentTick;
            if (delta > 0) {
                model.lock.lock();
                try {
                    // Get player input
                    model.input(InputControl.playerInput);
                    // Update model
                    model.update(delta);
                } finally {
                    model.lock.unlock();
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
