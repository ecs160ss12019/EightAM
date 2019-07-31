package EightAM.asteroids;

import android.content.Context;
import android.content.SharedPreferences;

final class Scoreboard {
    private int highScore;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    Scoreboard(Context c) {
        prefs = c.getSharedPreferences("High Score", Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt("High Score", 0);
    }

    public String getHighScore() {
        return "High Score: " + this.highScore;
    }

    public void setHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            editor.putInt("High Score", highScore);
            editor.commit();
        }
    }
}
