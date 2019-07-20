package EightAM.asteroids;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

class InputControl {
    static Control playerInput = new Control();
    static ButtonSet buttonSet = new ButtonSet();

    // Connects buttons
    static void initializeButtons(Activity activity) {
        buttonSet.left = activity.findViewById(R.id.left_button);
        buttonSet.right = activity.findViewById(R.id.right_button);
        buttonSet.up = activity.findViewById(R.id.up_button);
        buttonSet.down = activity.findViewById(R.id.down_button);
        buttonSet.shoot = activity.findViewById(R.id.shoot_button);
        buttonSet.pause = activity.findViewById(R.id.pause_button);

        buttonSet.left.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("InputControl", "left_down");
                playerInput.LEFT = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("InputControl", "left_up");
                playerInput.LEFT = false;
            }
            return true;
        });
        buttonSet.right.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("InputControl", "right_down");
                playerInput.RIGHT = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("InputControl", "right_up");
                playerInput.RIGHT = false;
            }
            return true;
        });
        buttonSet.up.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("InputControl", "up_down");
                playerInput.UP = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("InputControl", "up_up");
                playerInput.UP = false;
            }
            return true;
        });
        buttonSet.down.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("InputControl", "down_down");
                playerInput.DOWN = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("InputControl", "down_up");
                playerInput.DOWN = false;
            }
            return true;
        });
        buttonSet.shoot.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playerInput.SPECIAL_1 = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                playerInput.SPECIAL_1 = false;
            }
            return true;
        });

        buttonSet.pause.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playerInput.PAUSE = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                playerInput.PAUSE = false;
            }
            return true;
        });
    }

    static class Control {
        boolean UP = false;
        boolean DOWN = false;
        boolean RIGHT = false;
        boolean LEFT = false;
        boolean SPECIAL_1 = false;
        boolean PAUSE = false;
    }

    static class ButtonSet {
        Button left;
        Button right;
        Button up;
        Button down;
        Button shoot;
        Button pause;
    }

}
