package EightAM.asteroids;


import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
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

    RelativeLayout msgLayout;
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
        AssetLoader.load(gameView.getContext());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //gameState = new GameState(size.x, size.y, gameView.getContext());
        gameModel = new GameModel(size, gameView.getContext());
        gameController = new GameController(gameModel, size.x, size.y);
        gameView.setGameModel(gameModel);
        // temporary until menu is created
        gameView.onResume();
        gameController.onResume();

        // Play background music
        //gameView.audio.playMusic(MainActivity.this);

        // Find Layout Elements to be used/manipulated

        msgLayout = findViewById(R.id.view_gauze);
        buttonLayout = findViewById(R.id.view_button);
        pauseButton = findViewById(R.id.pause_button);
        screenMsg = findViewById(R.id.startText);

        // Start game on tap
        onStartScreen();

        // Set Listener for Pause
        onPauseScreen();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null) gameView.onPause();

        isPaused = true;

        msgLayout.setOnClickListener(view -> {
            msgLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            onResume();
        });

    }

    // TODO: disable collision detection before user taps on screen
    protected void onStartScreen() {
        msgLayout.setOnClickListener(view -> {
            msgLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            ((Ship)gameModel.objectMap.get(gameModel.currPlayerShip)).invincible = true;
            ((Ship)gameModel.objectMap.get(gameModel.currPlayerShip)).invincibilityDuration = 500;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null && !isPaused) gameView.onResume();

        isPaused = false;
    }

    protected void onPauseScreen() {
        pauseButton.setOnClickListener(view -> {
            msgLayout.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);
            screenMsg.setText("Paused - Tap to Resume");
            onPause();
        });
    }


}
