package EightAM.asteroids;

import android.content.Context;
import android.content.SharedPreferences;

final class Scoreboard {
    private int lastScore;
    private int highScore;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    Scoreboard(Context c) {
        prefs = c.getSharedPreferences("High Score", Context.MODE_PRIVATE);
        highScore = prefs.getInt("High Score", 0);
    }

    public String getHighScore() {
        return "High Score: " + this.highScore;
    }

    public void processEndGameStats(int score) {
        lastScore = score;
        if (score > highScore) {
            editor = prefs.edit();
            editor.putInt("High Score", highScore);
            editor.apply();
        }
    }

    int getLastScore() {
        return lastScore;
    }
}
