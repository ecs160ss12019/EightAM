package EightAM.asteroids;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import static EightAM.asteroids.Constants.SHIP_INVINCIBILITY_DURATION;

public class Menu {

    Menu() {

    }

    void initLayout(Activity act) {

    }


    // TODO: disable collision detection before user taps on screen
    protected void onTapScreen() {
//        gauze.setOnClickListener(view -> {
//            gauze.setVisibility(View.GONE);
//            buttonLayout.setVisibility(View.VISIBLE);
//            ((Ship)gameModel.objectMap.get(gameModel.currPlayerShip)).invincible = true;
//            ((Ship)gameModel.objectMap.get(gameModel.currPlayerShip)).invincibilityDuration = SHIP_INVINCIBILITY_DURATION;
//        });
    }

    protected void onPauseScreen() {
//        pauseButton.setOnClickListener(view -> {
//            Log.d("main activity", "onpause");
//            gauze.setVisibility(View.VISIBLE);
//            buttonLayout.setVisibility(View.GONE);
//            screenMsg.setText("Paused - Tap to Resume");
//            onPause();
//        });
    }



}
