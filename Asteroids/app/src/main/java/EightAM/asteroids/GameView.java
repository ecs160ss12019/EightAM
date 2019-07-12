package EightAM.asteroids;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

class GameView extends SurfaceView implements Runnable, View.OnTouchListener {
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private Thread thread;
    private InputControl inputControl;
    private InputControl.Control controlInstance;

    GameView(Context ctx) {
        this(ctx, null);
    }

    GameView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    @Override
    public void run() {

    }

    @Override
    public boolean onTouch(View var1, MotionEvent var2) {
        return true;
    }

}
