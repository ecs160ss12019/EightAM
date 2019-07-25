package EightAM.asteroids;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static EightAM.asteroids.Constants.STARTING_LIVES;

public class PlayerStats {

    // ---------------Member variables-------------

    int score;
    int livesLeft;
    int highScore;
    int textFormatting;

    // for storing high score, cited: textbook Chapter 21
    SharedPreferences.Editor editor;
    Context context;
    int spaceWidth;
    int spaceHeight;

    protected PlayerStats(int screenWidth, int screenHeight, Context context) {

        // Set game attributes
        this.score = 0;
        this.livesLeft = STARTING_LIVES;

        this.context = context;
        this.textFormatting = screenWidth / 40;
        spaceWidth = screenWidth;
        spaceHeight = screenHeight;

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
     void setHighScore() {
        // Edit high score if exists
        if (score > highScore) {
            editor.putInt("High Score", highScore);
            editor.commit();
        }
    }

    /**
     *
     */
    void resetScore() {
        score = 0;
        livesLeft = STARTING_LIVES;
    }

    /**
     *
     */
    void updateScore() {
        score++;
    }

    /**
     *
     */
    void updateLive() {
        livesLeft--;
    }

    /**
     * Draw the HUD
     */
    void drawAttributes(Canvas canvas, Paint paint, Ship ship, SurfaceHolder sh){
        //paint.setColor(Color.argb(255,225,20,147));
        paint.setTextSize(textFormatting);
        canvas.drawText("Score: " + score, textFormatting,textFormatting * 2,paint);
        canvas.drawText("Lives: " + livesLeft, textFormatting,textFormatting * 3,paint);
        canvas.drawText("High Score: " + highScore, textFormatting, textFormatting * 4, paint);
    }
}
