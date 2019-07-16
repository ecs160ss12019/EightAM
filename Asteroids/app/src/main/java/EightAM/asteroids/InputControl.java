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
        buttonSet.left.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("debug", "left_down");
                playerInput.LEFT = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("debug", "left_up");
                playerInput.LEFT = false;
            }
            return true;
        });
        buttonSet.right.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playerInput.RIGHT = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                playerInput.RIGHT = false;
            }
            return true;
        });
        buttonSet.up.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playerInput.UP = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                playerInput.UP = false;
            }
            return true;
        });
        buttonSet.down.setOnTouchListener((View view, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                playerInput.DOWN = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
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
    }

    static class Control {
        boolean UP = false;
        boolean DOWN = false;
        boolean RIGHT = false;
        boolean LEFT = false;
        boolean SPECIAL_1 = false;
    }

    static class ButtonSet {
        Button left;
        Button right;
        Button up;
        Button down;
        Button shoot;
    }

}
