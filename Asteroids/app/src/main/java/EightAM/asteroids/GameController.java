package EightAM.asteroids;

import android.os.SystemClock;

final class GameController {

    private int width;
    private int height;
    private long currentTick;
    private boolean isRunning;
    private GameModel model;

    GameController(GameModel gameModel, int width, int height) {
        // Initialize objects here
        currentTick = 0;
        isRunning = false;
        this.width = width;
        this.height = height;
        model = gameModel;
    }

    // Any preparation for starting the game done here
    void start() {
        isRunning = true;
        loop();
    }

    void loop() {
        while (isRunning) {
            long time = SystemClock.elapsedRealtime();
            if (time - currentTick > 0) {
                // update
            }
            currentTick = time;
        }
    }
}
