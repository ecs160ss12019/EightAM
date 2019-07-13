package EightAM.asteroids;

import android.os.SystemClock;

final class GameController {

    long currentTick;

    GameController() {
        // Initialize objects here
        currentTick = 0;
    }

    void loop() {
        long time = SystemClock.elapsedRealtime();
        if (time - currentTick > 0) {
            // update
        }
        currentTick = time;
    }
}
