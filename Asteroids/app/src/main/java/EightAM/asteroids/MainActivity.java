package EightAM.asteroids;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    GameController gameController;
    GameModel gameModel;
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        Log.d("in main activity", "size.x =" + size.x);
        gameModel = new GameModel(size.x, size.y, gameView.getContext());
        gameController = new GameController(gameModel, size.x, size.y);
        gameView.setGameModel(gameModel);
        // temporary until menu is created
        gameView.onResume();
        //gameController.start();
    }

    @Override
    protected void onPause() {
        if (gameView != null) {
            gameView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null && !isPaused) gameView.onResume();
    }
}
