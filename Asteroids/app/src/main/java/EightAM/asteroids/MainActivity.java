package EightAM.asteroids;


import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import EightAM.asteroids.interfaces.GameOverListener;

public class MainActivity extends AppCompatActivity implements GameOverListener {

    static int gameOverDelayTime = 3000;
    GameView gameView;
    GameController gameController;
    GameModel gameModel;

    // For mane layout (play, pause)
    RelativeLayout startLayout;
    RelativeLayout buttonLayout;
    RelativeLayout restartLayout;
    RelativeLayout pausedLayout;
    ImageView pauseButton;
    TextView startText;
    TextView scoreText;
    TextView restartText;
    TextView pausedText;

    Scoreboard scoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if (Build.VERSION.SDK_INT < 16) {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        } else {
                            getWindow().getDecorView().setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_FULLSCREEN);
                        }
                    }
                });


        gameView = findViewById(R.id.gameView);
        //start layout
        startLayout = findViewById(R.id.view_start);
        startText = findViewById(R.id.startText);
        scoreText = findViewById(R.id.scoreText);
        //button layout
        buttonLayout = findViewById(R.id.view_button);
        pauseButton = findViewById(R.id.pause_button);
        //paused layout
        pausedLayout = findViewById(R.id.view_paused);
        pausedText = findViewById(R.id.pausedText);
        //restart layout
        restartLayout = findViewById(R.id.view_restart);
        restartText = findViewById(R.id.restartText);

        AssetLoader.load(gameView.getContext());
        InputControl.initializeButtons(this);

        scoreboard = new Scoreboard(this);

//        startGame();
        onStartScreen();
//        gameController.onResume();

        // Play background music
        //gameView.audio.playMusic(MainActivity.this);

        // Find Layout Elements to be used/manipulated
        // gameModel.setButton(restartLayout, restartText);

        // Start game on tap
        setStartListener();

        // Set Listener for Pause
        setPauseListener();


        setResumeListener();

        // Set listener for Game Over
        //setGameOverListener();
    }

    void onStartScreen() {
        scoreText.setText(scoreboard.getHighScore());
        startLayout.setVisibility(View.VISIBLE);
        startText.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);
    }

    // TODO: disable collision detection before user taps on screen
    private void setStartListener() {
        startText.setOnClickListener(view -> {
            restartLayout.setVisibility(View.GONE);
            restartText.setVisibility(View.GONE);
            startLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            startGame();
        });
    }

    private void setPauseListener() {
        pausedText.setText("Paused - Tap to Resume");
        pauseButton.setOnClickListener(view -> {
            restartText.setVisibility(View.GONE);
            restartLayout.setVisibility(View.GONE);
            startLayout.setVisibility(View.GONE);
            startText.setVisibility(View.GONE);
            scoreText.setVisibility(View.GONE);
            pausedLayout.setVisibility(View.VISIBLE);
            pausedText.setVisibility(View.VISIBLE);
            onPause();
        });
    }
//
//    private void setGameOverListener() {
//        restartText.setText("Game Over");
//        restartText.setOnClickListener(view -> {
//            restartLayout.setVisibility(View.GONE);
//            restartText.setVisibility(View.GONE);
//
//            pausedLayout.setVisibility(View.GONE);
//            pausedText.setVisibility(View.GONE);
//
//            startLayout.setVisibility(View.VISIBLE);
//            startText.setVisibility(View.VISIBLE);
//            scoreText.setVisibility(View.VISIBLE);
//            startGame();
//        });
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null)
            gameView.onPause();
        if (gameController != null) {
            gameController.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView == null || gameModel == null || gameController == null) {
            onStartScreen();
        } else {
            gameView.onResume();
            gameController.onResume();
        }
    }

    protected void onGameOverScreen() {
        restartLayout.setVisibility(View.VISIBLE);
        restartText.setVisibility(View.VISIBLE);
        // TODO: set score
    }

    @Override
    public void onGameOver() {
        Log.d(this.getClass().getCanonicalName(), "onGameOver() called");
        gameController.onPause();
        gameView.onPause();
        scoreboard.processEndGameStats(gameModel.stats.score);
        onGameOverScreen();
        new Handler().postDelayed(() -> {
            restartLayout.setVisibility(View.GONE);
            restartText.setVisibility(View.GONE);
            setStartListener();
            startGame();
            onStartScreen();
        }, gameOverDelayTime);
    }

    public void startGame() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameModel = new GameModel(size, gameView.getContext());
        gameController = new GameController(gameModel, this);
        gameView.setGameModel(gameModel);
        gameView.onResume();
        gameController.onResume();
        gameModel.startGame();
    }

    protected void setResumeListener(){
        pausedText.setOnClickListener(view -> {
            pausedLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            onResume();
        });
    }
}