package EightAM.asteroids;

import static EightAM.asteroids.Constants.STARTING_LIVES;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import EightAM.asteroids.interfaces.Scoreable;

public class GameStats {

    // ---------------Member variables-------------

    int score;
    int livesLeft;
    int highScore;
    int textFormatting;

    // For storing high score, cited: textbook Chapter 21
    Point spaceSize;

    protected GameStats(Point spaceSize) {

        // Set game attributes
        this.score = 0;
        this.livesLeft = STARTING_LIVES;

        this.textFormatting = spaceSize.x / 40;
        this.spaceSize = spaceSize;
    }

    // ---------------Member methods---------------

    void newGame() {
        score = 0;
        livesLeft = STARTING_LIVES;
    }

    void plusScore() {
        plusScore(1);
    }

    void plusScore(int i) {
        score += i;
    }

    void plusLive(int i) { livesLeft += i; }

    void subLive() { subLive(1);}

    void subLive(int i) { livesLeft -= i;}

    int getLife() { return livesLeft; }

    boolean isGameOver() {
        return (this.livesLeft == 0);
    }

    public void score(GameObject object) {
        if (object instanceof Scoreable) plusScore(((Scoreable) object).score());
    }

    /**
     * Draw the HUD
     */
    public void drawAttributes(Canvas canvas, Paint paint) {
        //paint.setColor(Color.argb(255,225,20,147));
        paint.setTextSize(textFormatting);
        canvas.drawText("Score: " + score, textFormatting, textFormatting * 1, paint);
        canvas.drawText("Lives: " + livesLeft, textFormatting, textFormatting * 3, paint);
    }
}
