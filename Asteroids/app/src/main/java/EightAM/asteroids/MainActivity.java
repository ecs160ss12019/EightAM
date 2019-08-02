package EightAM.asteroids;


import static EightAM.asteroids.Constants.GAMEOVER_DELAY;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import EightAM.asteroids.interfaces.GameOverListener;
import EightAM.asteroids.specs.LevelAudioSpec;

public class MainActivity extends AppCompatActivity implements GameOverListener {

    GameMode mode;

    GameView gameView;
    GameController gameController;
    GameModel gameModel;

    Scoreboard scoreboard;

    RelativeLayout startLayout;
    RelativeLayout buttonLayout;
    RelativeLayout restartLayout;
    RelativeLayout pausedLayout;
    ConstraintLayout shipSelectLayout;
    ImageView pauseButton;
    TextView startText;
    TextView scoreText;
    TextView restartText;
    TextView pausedText;

    AudioUtility audio;
    SoundManager sound;

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
                visibility -> {
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
        );

        // Find layouts and views
        gameView = findViewById(R.id.gameView);
        startLayout = findViewById(R.id.view_start);
        startText = findViewById(R.id.startText);
        scoreText = findViewById(R.id.scoreText);         //start layout
        buttonLayout = findViewById(R.id.view_button);
        pauseButton = findViewById(R.id.pause_button);    //button layout
        pausedLayout = findViewById(R.id.view_paused);
        pausedText = findViewById(R.id.pausedText);       //paused layout
        restartLayout = findViewById(R.id.view_restart);
        restartText = findViewById(R.id.restartText);     //restart layout
        shipSelectLayout = findViewById(R.id.ShipSelect);


        mode = new StartScreen(this);

        // loads all sounds, bitmaps, and god knows what else
        audio = AudioUtility.getInstance();
        AssetLoader.load(gameView.getContext());
        audio.start();
        InputControl.initializeButtons(this);

        sound = new SoundManager(audio, new LevelAudioSpec());

        Messages.setPaint(this);
        scoreboard = new Scoreboard(this);

        onStartScreen();
        setStartListener();
        setPauseListener();
        setResumeListener();
    }

    void hideButtons() {
        buttonLayout.setVisibility(View.GONE);
    }

    void showButtons() {
        buttonLayout.setVisibility(View.VISIBLE);
    }

    void hideStartScreen() {
        startLayout.setVisibility(View.GONE);
    }

    void showStartScreen() {
        startLayout.setVisibility(View.VISIBLE);
    }

    void hideShipSelect() {
        shipSelectLayout.setVisibility(View.GONE);
    }

    void showShipSelect() {
        shipSelectLayout.setVisibility(View.VISIBLE);
    }

    void hidePauseScreen() {
        pausedLayout.setVisibility(View.GONE);
    }

    void showPauseScreen() {
        pausedLayout.setVisibility(View.VISIBLE);
    }

    void hideRestartScreen() {
        restartLayout.setVisibility(View.GONE);
    }

    void showRestartScreen() {
        restartLayout.setVisibility(View.VISIBLE);
    }

    void onStartScreen() {
        scoreText.setText(scoreboard.getHighScore());
//        startLayout.setVisibility(View.VISIBLE);
//        startText.setVisibility(View.VISIBLE);
//        scoreText.setVisibility(View.VISIBLE);
    }

    private void setStartListener() {
        startText.setOnClickListener(view -> {
            mode.transitionTo(new ShipSelection(this));
        });
    }

    private void setPauseListener() {
        pauseButton.setOnClickListener(view -> {
//            restartText.setVisibility(View.GONE);
//            restartLayout.setVisibility(View.GONE);
//            startLayout.setVisibility(View.GONE);
//            startText.setVisibility(View.GONE);
//            scoreText.setVisibility(View.GONE);
//            pausedLayout.setVisibility(View.VISIBLE);
//            pausedText.setVisibility(View.VISIBLE);
            mode.transitionTo(new Pause(this));
            onPause();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null) {
            gameView.onPause();
        }
        if (gameController != null) {
            gameController.onPause();
        }
        audio.sendMusicCommand(false, true, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView == null || gameModel == null || gameController == null) {
            onStartScreen();
        } else {
            audio.sendMusicCommand(false, false, true);
            gameController.onResume();
            gameView.onResume();
        }
    }

//    protected void onGameOverScreen() {
//        buttonLayout.setVisibility(View.GONE);
//        restartLayout.setVisibility(View.VISIBLE);
//        restartText.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onGameOver() {
        gameController.onPause();
        gameView.onPause();

        scoreboard.setHighScore(gameModel.stats.score);
        audio.offMusic();
//        onGameOverScreen();
        mode.transitionTo(new GameOver(this));
        new Handler().postDelayed(() -> {
//            restartLayout.setVisibility(View.GONE);
//            restartText.setVisibility(View.GONE);
//            setStartListener();
            mode.transitionTo(new StartScreen(this));
            onStartScreen();
        }, GAMEOVER_DELAY);
    }

    public void startGame(Ships shipType) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameModel = new GameModel(size, shipType);
        gameModel.registerAudioListener(sound);
        gameController = new GameController(gameModel, this);
        gameView.setGameModel(gameModel);
        gameView.onResume();
        gameController.onResume();
        gameModel.startGame();
        audio.startOverMusic();
    }

    protected void setResumeListener() {
        pausedText.setOnClickListener(view -> {
//            pausedLayout.setVisibility(View.GONE);
//            buttonLayout.setVisibility(View.VISIBLE);
            mode.transitionTo(new InGame(this));
            onResume();
        });
    }

}