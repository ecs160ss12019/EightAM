package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_LIVES;
import static EightAM.asteroids.Constants.TEXT_ADJUSTMENT;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import EightAM.asteroids.interfaces.Scoreable;

public class GameStats {

    // ---------------Member variables-------------

    int score;
    int livesLeft;
    int textFormatting;
    RectF spaceSize;

    // ---------------Member methods---------------

    /**
     * GameSates constructor initialize the start live and zero score
     * @param spaceSize
     */
    protected GameStats(RectF spaceSize) {
        // Set game attributes
        this.score = 0;
        this.livesLeft = STARTING_LIVES;
        this.textFormatting = (int) spaceSize.width() / 40;
        this.spaceSize = spaceSize;
    }

    /**
     * Reinitialize game stats for a new game
     */
    void newGame() {
        score = 0;
        livesLeft = STARTING_LIVES;
    }

    /**
     * Increment score
     * @param i
     */
    void plusScore(int i) {
        score += i;
    }

    /**
     * Decrement live
     * @param i
     */
    void subLive(int i) {
        livesLeft -= i;
    }

    void subLive() {
        subLive(1);
    }

    /**
     * Live getter
     * @return remaining lives
     */
    int getLife() {
        return livesLeft;
    }

    /**
     * Increment score basing on the type of object
     * @param object game entity defected by user
     */
    public void score(GameObject object) {
        if (object instanceof Scoreable)
            plusScore(((Scoreable) object).score());
    }

    /**
     * Draw the HUD on the top middle of screen
     */
    public void drawAttributes(Canvas canvas, Paint paint) {
        paint.setTextSize(textFormatting);
        canvas.drawText("Score: " + score, (spaceSize.width() / 2) - TEXT_ADJUSTMENT, textFormatting * 1, paint);
        canvas.drawText("Lives: " + livesLeft, (spaceSize.width() / 2) - TEXT_ADJUSTMENT, textFormatting * 2, paint);
    }
}
