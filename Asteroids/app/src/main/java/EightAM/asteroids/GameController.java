package EightAM.asteroids;

import android.os.SystemClock;

final class GameController implements Runnable {
    private int width;
    private int height;
    private long currentTick;
    private boolean isRunning;
    private GameModel model;
    Thread thread;

    GameController(GameModel gameModel, int width, int height) {
        // Initialize objects here
        currentTick = 0;
        isRunning = false;
        this.width = width;
        this.height = height;
        model = gameModel;
    }


    @Override
    public void run() {
        while (isRunning) {
            long time = SystemClock.elapsedRealtime();
            long delta = time - currentTick;
            if (delta > 0) {
                //                Log.d("Debug", "update ship");
                InputControl.Control i = InputControl.playerInput;
                model.lock.lock();
                try {
                    //                    Log.d("gameController", "lock acquired");
                    model.ship.input(i.UP, i.LEFT, i.RIGHT);
                    model.update(delta);
                } finally {
                    model.lock.unlock();
                }
                currentTick = time;
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
