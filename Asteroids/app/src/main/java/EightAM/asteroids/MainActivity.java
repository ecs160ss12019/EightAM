package EightAM.asteroids;

import static EightAM.asteroids.Constants.SHIP_INVINCIBILITY_DURATION;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    GameController gameController;
    GameModel gameModel;
    GameState gameState;
    RelativeLayout startView;
    RelativeLayout buttonLayout;
    ImageView pauseButton;
    TextView screenMsg;
    private boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        InputControl.initializeButtons(this);
        gameView = findViewById(R.id.gameView);

        /*Temp solution to get screen width and height*/
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if (Build.VERSION.SDK_INT < 16) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameState = new GameState(size.x, size.y, gameView.getContext());
        gameModel = new GameModel(size.x, size.y, gameView.getContext());
        gameController = new GameController(gameModel, size.x, size.y);
        gameView.setGameModel(gameModel);
        // temporary until menu is created
        gameView.onResume();
        gameController.onResume();

        // Play background music
        gameView.audio.playMusic(MainActivity.this);

        // Find Layout Elements to be used/manipulated
        startView = findViewById(R.id.startView);
        buttonLayout = findViewById(R.id.button_layout);
        pauseButton = findViewById(R.id.pause_button);
        screenMsg = findViewById(R.id.startText);


        // Start game on tap
        onTapScreen();

        // Set Listener for Pause
        onPauseScreen();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null) gameView.onPause();

        isPaused = true;

        startView.setOnClickListener(view -> {
            startView.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            onResume();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null && !isPaused) gameView.onResume();

        isPaused = false;
    }


    // TODO: disable collision detection before user taps on screen
    protected void onTapScreen() {
        startView.setOnClickListener(view -> {
            startView.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            gameModel.currPlayerShip.invincible = true;
            gameModel.currPlayerShip.invincibilityDuration = SHIP_INVINCIBILITY_DURATION;
        });
    }

    protected void onPauseScreen() {
        pauseButton.setOnClickListener(view -> {
            Log.d("main activity", "onpause");
            startView.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);
            screenMsg.setText("Paused - Tap to Resume");
            onPause();
        });
    }


}
