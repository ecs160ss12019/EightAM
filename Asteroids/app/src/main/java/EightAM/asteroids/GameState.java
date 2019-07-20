package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import static EightAM.asteroids.Constants.STARTING_LIVES;

public class GameState {

    // ---------------Member variables-------------

    int mTextFormatting;
    int score;
    int livesLeft;

    // ---------------Member methods---------------
    void ScoreAttributes(int screenX) {
        score = 0;
        livesLeft = STARTING_LIVES;
        mTextFormatting = screenX / 50;
    }
    
    void drawAttributes(Canvas canvas, Paint paint, Ship ship){
        // Draw the HUD
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(mTextFormatting);
        canvas.drawText("Score: " + score, mTextFormatting,mTextFormatting * 2,paint);
        canvas.drawText("Lives: " + livesLeft, mTextFormatting,mTextFormatting * 3,paint);
    }

    void updateScore() {
        score++;
    }

    void updateLive() {
        livesLeft--;
    }
}
