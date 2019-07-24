package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class Messages implements Drawable {
    private final static long DEFAULT_FADE_TIME = 500;
    private final static long DEFAULT_DURATION_MS = 5000;
    private List<Message> messageList;
    private long lastDrawTime;

    Messages() {
        messageList = new ArrayList<>();
    }

    void addMessage(Message message) {
        if (messageList.size() == 0) {
            lastDrawTime = System.currentTimeMillis();
        }
        messageList.add(message);
    }

    void clearMessages() {
        messageList.clear();
    }

    // Displays a message in the middle
    void drawMessage(String message) {
        drawMessage(message, DEFAULT_DURATION_MS);
    }

    void drawMessage(String message, long durationMS) {
        drawMessage(message, durationMS, -1, -1);
    }

    void drawMessage(String message, long durationMS, float x, float y) {
        drawMessage(message, durationMS, x, y, DEFAULT_FADE_TIME);
    }

    void drawMessage(String message, long durationMS, float x, float y, long fadeTimeMS) {
        Paint paint = PaintStore.getInstance().getPaint("default");
        messageList.add(new MessageWithFade(message, paint, durationMS, x, y, fadeTimeMS));
    }

    @Override
    public void draw(Canvas canvas) {
        long diff = Math.abs(System.currentTimeMillis() - lastDrawTime);
        for (ListIterator<Message> it = messageList.listIterator(); it.hasNext(); ) {
            Message element = it.next();
            element.draw(canvas);
            element.durationMS -= diff;
            if (element.durationMS < 0) {
                it.remove();
            }
        }
        lastDrawTime = System.currentTimeMillis();
    }

    static class Message implements Drawable {
        String message;
        long durationMS;
        float x, y;
        Paint paint;

        Message(String message, Paint paint, long durationMS, float x, float y) {
            this.message = message;
            this.paint = paint;
            this.durationMS = durationMS;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Canvas canvas) {
            if (x < 0 || y < 0) { // default drawing in middle
                canvas.drawText(message, (float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2, paint);
            }
            canvas.drawText(message, x, y, paint);
        }
    }

    static class MessageWithFade extends Message implements Drawable {
        long fadeTimeMS;

        MessageWithFade(String message, Paint paint, long durationMS, float x, float y, long fadeTimeMS) {
            super(message, paint, durationMS, x, y);
            this.fadeTimeMS = fadeTimeMS;
        }

        @Override
        public void draw(Canvas canvas) {
            if (fadeTimeMS > durationMS) {
                paint.setAlpha((int) (255.0 * ((float) durationMS / fadeTimeMS)));
            }
            super.draw(canvas);
        }
    }

    static class PulsatingMessage extends Message implements Drawable {
        int periodMS;
        long lastPeak;

        PulsatingMessage(String message, Paint paint, long durationMS, float x, float y, int periodMS) {
            super(message, paint, durationMS, x, y);
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
