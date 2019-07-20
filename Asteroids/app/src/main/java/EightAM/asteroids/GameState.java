package EightAM.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static EightAM.asteroids.Constants.STARTING_LIVES;
import static EightAM.asteroids.InputControl.playerInput;

public class GameState {

    // ---------------Member variables-------------

    int mTextFormatting;
    int score;
    int livesLeft;
    Lock lock;
    Context context;
    int spaceWidth;
    int spaceHeight;

    protected GameState(int screenWidth, int screenHeight, Context context) {
        lock = new ReentrantLock();
        this.context = context;
        spaceWidth = screenWidth;
        spaceHeight = screenHeight;
    }

    // ---------------Member methods---------------
    void input(InputControl.Control i) {
        if (i.PAUSE) {
            // call pause on gameview + game view
        }
    }


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
