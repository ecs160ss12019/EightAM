package EightAM.asteroids;

import android.content.Context;
import android.content.SharedPreferences;

final class Scoreboard {

    // ---------------Member variables-------------

    // For storing high score, cited: textbook Chapter 21
    private int highScore;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    // ---------------Member methods-------------

    /**
     * Scoreboard constructor
     * @param context
     */
    public Scoreboard(Context context) {
        this.prefs = context.getSharedPreferences("High Score", Context.MODE_PRIVATE);
        this.editor = prefs.edit();
        this.highScore = prefs.getInt("High Score", 0);
    }

    /**
     * Get highscore
     * @return string with previous high score
     */
    public String getHighScore() {
        return "High Score: " + this.highScore;
    }

    /**
     * Set highscore
     * @param score the current score of game
     */
    public void setHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            editor.putInt("High Score", highScore);
            editor.commit();
        }
    }
}
