package EightAM.asteroids;

import android.content.Context;
import android.content.SharedPreferences;
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

    // game status
    boolean gameRunning = false;
    boolean gamePaused = false;
    boolean gameOver = false;
    boolean gameDrawing = false;

    int score;
    int livesLeft;
    int highScore;
    int textFormatting;

    // for storing high score, cited: textbook Chapter 21
    SharedPreferences.Editor editor;
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

    }

    /**
     * Constructor
     */
//    void GameState(Context context, int screenWidth) {
//
//        //set game attributes
//        this.gameRunning = true;
//        this.score = 0;
//        this.livesLeft = STARTING_LIVES;
//        this.textFormatting = screenWidth / 50;
//
//        //get high score from the previous game
//        SharedPreferences prefs;
//        prefs = context.getSharedPreferences("High Score", Context.MODE_PRIVATE);
//
//        //set high score, zero if not available
//        this.highScore = prefs.getInt("High Score", 0);
//
//        //initialize editor
//        this.editor = prefs.edit();
//    }

    /**
     *
     */





    void updateScore() {
        score++;
    }

    void updateLive() {
        livesLeft--;
    }

    void newGame() {
        score = 0;
        livesLeft = STARTING_LIVES;
        mTextFormatting = screenX / 50;
    }

    /**
     *
     */
    void pauseGame() {
        gamePaused = true;

    }

    /**
     *
     */
    void resumeGame() {
        gamePaused = false;
        gameOver = false;
    }

    /**
     *
     */
    void endGame() {
        gamePaused = true;
        gameOver = true;
        gameRunning = false;

        //edit high score if exists
        if (score > highScore) {
            editor.putInt("High Score", highScore);
            editor.commit();
        }
    }

    /**
     *
     * @param canvas
     * @param paint
     * @param ship
     */
    void drawAttributes(Canvas canvas, Paint paint, Ship ship){
        // Draw the HUD
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(textFormatting);
        canvas.drawText("Score: " + score, textFormatting,textFormatting * 2,paint);
        canvas.drawText("Lives: " + livesLeft, textFormatting,textFormatting * 3,paint);
    }
}
