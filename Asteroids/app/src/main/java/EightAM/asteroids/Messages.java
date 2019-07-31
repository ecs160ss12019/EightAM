package EightAM.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import EightAM.asteroids.interfaces.Drawable;

// In game messages given by GameController to be rendered by GameView
public class Messages {
    private final static long DEFAULT_FADE_TIME = 500;
    private final static long DEFAULT_DURATION_MS = 5000;
    private static Queue<Message> messageQueue = new ArrayDeque<>();
    private static long lastDrawTime;
    private static Lock lock = new ReentrantLock();
    private static Paint paint = new Paint();
    private static int fontSize;

    private Messages() {
    }

    static void setPaint(Context c) {
        fontSize = c.getResources().getDimensionPixelSize(R.dimen.inGameMessageSize);
        paint.setColor(Color.WHITE);
        paint.setTextSize(fontSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    static void addMessage(Message message) {
        lock.lock();
        try {
            if (messageQueue.size() == 0) {
                lastDrawTime = System.currentTimeMillis();
            }
            messageQueue.add(message);
        } finally {
            lock.unlock();
        }
    }

    static void clearMessages() {
        lock.lock();
        try {
            messageQueue.clear();
        } finally {
            lock.unlock();
        }
    }

    // Displays a message in the middle
    static void drawMessage(String message) {
        drawMessage(message, Color.WHITE);
    }

    static void drawMessage(String message, int color) {
        drawMessage(message, color, DEFAULT_DURATION_MS);
    }

    static void drawMessage(String message, int color, long durationMS) {
        drawMessage(message, color, durationMS, -1, -1);
    }

    static void drawMessage(String message, int color, long durationMS, float x, float y) {
        drawMessage(message, color, durationMS, x, y, DEFAULT_FADE_TIME);
    }

    static void drawMessage(String message, int color, long durationMS, float x, float y,
            long fadeTimeMS) {
        lock.lock();
        try {
            messageQueue.add(new MessageWithFade(message, color, durationMS, x, y, fadeTimeMS));
        } finally {
            lock.unlock();
        }
    }

    static void draw(Canvas canvas) {
        lock.lock();
        try {
            long diff = Math.abs(System.currentTimeMillis() - lastDrawTime);
            Message message = messageQueue.peek();
            if (message != null) {
//                Log.d(Messages.class.getCanonicalName(), "Message: " + message.message + ",
//                diff: " + diff);
                message.draw(canvas);
                message.durationMS -= diff;
                if (message.durationMS <= 0) {
                    messageQueue.poll();
                }
            }
            lastDrawTime = System.currentTimeMillis();
        } finally {
            lock.unlock();
        }
    }

    // Used by Messages
    public static class Message implements Drawable {
        String message;
        int color;
        long durationMS;
        float x, y;

        Message(String message, int color, long durationMS, float x, float y) {
            this.message = message;
            this.color = color;
            this.durationMS = durationMS;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Canvas canvas) {
            paint.setColor(this.color);
            if (x < 0 || y < 0 || x > canvas.getWidth() || y > canvas.getHeight()) {
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(message, (float) canvas.getWidth() / 2,
                        (float) canvas.getHeight() / 2, paint);
            }
            canvas.drawText(message, x, y, paint);
        }
    }

    public static class MessageWithFade extends Message implements Drawable {
        long fadeTimeMS;

        MessageWithFade(String message, int color, long durationMS, float x, float y,
                long fadeTimeMS) {
            super(message, color, durationMS, x, y);
            this.fadeTimeMS = fadeTimeMS;
        }

        @Override
        public void draw(Canvas canvas) {
            paint.setColor(this.color);
            if (fadeTimeMS > durationMS) {
                paint.setAlpha((int) (255.0 * ((float) durationMS / fadeTimeMS)));
            }
            if (x < 0 || y < 0 || x > canvas.getWidth() || y > canvas.getHeight()) {
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(message, (float) canvas.getWidth() / 2,
                        (float) canvas.getHeight() / 2, paint);
            }
            canvas.drawText(message, x, y, paint);
        }
    }

    public static class PulsatingMessage extends Message implements Drawable {
        int periodMS;
        long lastPeak;

        PulsatingMessage(String message, int color, long durationMS, float x, float y,
                int periodMS) {
            super(message, color, durationMS, x, y);
            this.periodMS = periodMS;
            lastPeak = System.currentTimeMillis();
        }

        @Override
        public void draw(Canvas canvas) {
            long delta = System.currentTimeMillis() - lastPeak;
            if (delta >= periodMS) {
                lastPeak += (delta / periodMS) * periodMS;
            }
            paint.setAlpha(intensity(delta));
            super.draw(canvas);
        }

        int intensity(double delta) {
            return (int) (255.0 * Math.pow(Math.cos(Math.PI * delta / (double) periodMS), 2.0));
        }
    }
}
