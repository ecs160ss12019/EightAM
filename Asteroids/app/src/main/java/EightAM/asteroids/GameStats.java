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
    SharedPreferences.Editor editor;
    Context context;
    Point spaceSize;

    protected GameStats(Point spaceSize, Context context) {

        // Set game attributes
        this.score = 0;
        this.livesLeft = STARTING_LIVES;

        this.context = context;
        this.textFormatting = spaceSize.x / 40;
        this.spaceSize = spaceSize;

        // Get high score from the previous game
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("High Score", Context.MODE_PRIVATE);

        // Set high score, zero if not available
        this.highScore = prefs.getInt("High Score", 0);

        // Initialize editor
        this.editor = prefs.edit();
    }

    // ---------------Member methods---------------

    /**
     * Set the high score
     */
    public void setHighScore() {
        // Edit high score if exists
        if (score > highScore) {
            editor.putInt("High Score", highScore);
            editor.commit();
        }
    }

    public String getHighScore() {
        return "High Score: " + this.highScore;
    }

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

    public void score(GameObject object) {
        if (object instanceof Scoreable) plusScore(((Scoreable) object).score());
    }

    /**
     * Draw the HUD
     */
    public void drawAttributes(Canvas canvas, Paint paint, SurfaceHolder sh) {
        //paint.setColor(Color.argb(255,225,20,147));
        paint.setTextSize(textFormatting);
        canvas.drawText("Score: " + score, textFormatting, textFormatting * 2, paint);
        canvas.drawText("Lives: " + livesLeft, textFormatting, textFormatting * 3, paint);
        canvas.drawText("High Score: " + highScore, textFormatting, textFormatting * 4, paint);
    }
}
