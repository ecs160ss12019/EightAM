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
    private static final int LEFT_POSITION = 100;
    private static final int RIGHT_POSITION = 100;
    private static final int TOP_POSITION = 100;
    private static final int BOTTOM_POSITION = 100;
    private static Queue<Message> messageQueue = new ArrayDeque<>();
    private static long lastDrawTime;
    private static Lock lock = new ReentrantLock();
    private static Paint paint;
    private static int largeFontSize;
    private static int mediumFontSize;
    private static int smallFontSize;

    static void setPaint(Context c) {
        largeFontSize = c.getResources().getDimensionPixelSize(R.dimen.MessageSizeLarge);
        mediumFontSize = c.getResources().getDimensionPixelSize(R.dimen.MessageSizeMedium);
        smallFontSize = c.getResources().getDimensionPixelSize(R.dimen.MessageSizeSmall);
        paint = PaintStore.getInstance().getPaint("font_paint");
        paint.setColor(Color.WHITE);
        paint.setTextSize(largeFontSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

    }

    static void drawMessage(String message, int color, long durationMS) {
        drawMessage(message, color, durationMS, FontSize.Medium, HorizontalPosition.Center,
                VerticalPosition.Center);
    }

    static void drawMessage(String message, int color, long durationMS, FontSize fontSize,
            HorizontalPosition x,
            VerticalPosition y) {
        drawMessage(message, color, durationMS, fontSize, x, y, DEFAULT_FADE_TIME);
    }

    private Messages() {
    }

    static void drawMessage(String message, int color, long durationMS, FontSize fontSize,
            HorizontalPosition x, VerticalPosition y, long fadeTimeMS) {
        lock.lock();
        try {
            messageQueue.add(
                    new MessageWithFade(message, color, durationMS, fontSize, x, y, fadeTimeMS));
        } finally {
            lock.unlock();
        }
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

    enum VerticalPosition {
        Top, Center, Bottom
    }

    enum HorizontalPosition {
        Left, Center, Right
    }

    enum FontSize {
        Large, Medium, Small
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
        FontSize fontSize;
        HorizontalPosition x;
        VerticalPosition y;

        Message(String message, int color, long durationMS, FontSize fontSize, HorizontalPosition x,
                VerticalPosition y) {
            this.message = message;
            this.color = color;
            this.durationMS = durationMS;
            this.fontSize = fontSize;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Canvas canvas) {
            paint.setColor(this.color);
            switch (fontSize) {
                case Large:
                    paint.setTextSize(largeFontSize);
                    break;
                case Medium:
                    paint.setTextSize(mediumFontSize);
                    break;
                case Small:
                    paint.setTextSize(smallFontSize);
                    break;
            }
            int xPos = 0;
            int yPos = 0;
            if (x == HorizontalPosition.Center) {
                paint.setTextAlign(Paint.Align.CENTER);
                xPos = canvas.getWidth() / 2;
            } else if (x == HorizontalPosition.Left) {
                paint.setTextAlign(Paint.Align.LEFT);
                xPos = LEFT_POSITION;
            } else if (x == HorizontalPosition.Right) {
                paint.setTextAlign(Paint.Align.RIGHT);
                xPos = canvas.getWidth() - RIGHT_POSITION;
            }
            if (y == VerticalPosition.Center) {
                yPos = canvas.getHeight() / 2;
            } else if (y == VerticalPosition.Top) {
                yPos = TOP_POSITION;
            } else if (y == VerticalPosition.Bottom) {
                yPos = canvas.getHeight() - BOTTOM_POSITION;
            }
            canvas.drawText(message, xPos, yPos, paint);
        }
    }

    public static class MessageWithFade extends Message implements Drawable {
        long fadeTimeMS;

        public MessageWithFade(String message, int color, long durationMS, FontSize fontSize,
                HorizontalPosition x, VerticalPosition y, long fadeTimeMS) {
            super(message, color, durationMS, fontSize, x, y);
            this.fadeTimeMS = fadeTimeMS;
        }

        @Override
        public void draw(Canvas canvas) {
            paint.setColor(this.color);
            switch (fontSize) {
                case Large:
                    paint.setTextSize(largeFontSize);
                    break;
                case Medium:
                    paint.setTextSize(mediumFontSize);
                    break;
                case Small:
                    paint.setTextSize(smallFontSize);
                    break;
            }
            if (fadeTimeMS > durationMS) {
                paint.setAlpha((int) (255.0 * ((float) durationMS / fadeTimeMS)));
            }
            int xPos = 0;
            int yPos = 0;
            if (x == HorizontalPosition.Center) {
                paint.setTextAlign(Paint.Align.CENTER);
                xPos = canvas.getWidth() / 2;
            } else if (x == HorizontalPosition.Left) {
                paint.setTextAlign(Paint.Align.LEFT);
                xPos = LEFT_POSITION;
            } else if (x == HorizontalPosition.Right) {
                paint.setTextAlign(Paint.Align.RIGHT);
                xPos = canvas.getWidth() - RIGHT_POSITION;
            }
            if (y == VerticalPosition.Center) {
                yPos = canvas.getHeight() / 2;
            } else if (y == VerticalPosition.Top) {
                yPos = TOP_POSITION;
            } else if (y == VerticalPosition.Bottom) {
                yPos = canvas.getHeight() - BOTTOM_POSITION;
            }
            canvas.drawText(message, xPos, yPos, paint);

        }
    }

    public static class PulsatingMessage extends Message implements Drawable {
        int periodMS;
        long lastPeak;

        public PulsatingMessage(String message, int color, long durationMS, FontSize fontSize,
                HorizontalPosition x, VerticalPosition y, int periodMS) {
            super(message, color, durationMS, fontSize, x, y);
            this.periodMS = periodMS;
            this.lastPeak = System.currentTimeMillis();
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
