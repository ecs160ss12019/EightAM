package EightAM.asteroids;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static EightAM.asteroids.Constants.SHIP_INVINCIBILITY_DURATION;

public class Menu {

    RelativeLayout msgLayout;
    RelativeLayout buttonLayout;
    ImageView pauseButton;
    TextView screenMsg;
    boolean isPaused = false;
    boolean isStart = false;


    Menu() {

    }

    void initLayout(Activity act) {
        msgLayout = act.findViewById(R.id.view_gauze);
        buttonLayout = act.findViewById(R.id.view_button);
        pauseButton = act.findViewById(R.id.pause_button);
        screenMsg = act.findViewById(R.id.startText);

        msgLayout.setOnClickListener(view -> {
            if (isStart == false) {
                isStart = true;
            } else if (isPaused == true) {
                isPaused = false;
            }
        });

        pauseButton.setOnClickListener(view -> {
            isPaused = true;
        });

    }


    // TODO: disable collision detection before user taps on screen
    protected void onStartScreen(GameModel model) {
        msgLayout.setOnClickListener(view -> {
            msgLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);
            ((Ship)model.objectMap.get(model.currPlayerShip)).invincible = true;
            ((Ship)model.objectMap.get(model.currPlayerShip)).invincibilityDuration = SHIP_INVINCIBILITY_DURATION;
        });
    }

    protected void onPauseScreen() {
        pauseButton.setOnClickListener(view -> {
            Log.d("main activity", "onpause");
            msgLayout.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.GONE);
            screenMsg.setText("Paused - Tap to Resume");
            onPause();
        });
    }



}
