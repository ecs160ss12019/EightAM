package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_MAXSPEED;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

abstract class Alien extends GameObject implements Shooter {
    // ---------------Member statics --------------
    static final int MAXSPEED = 3;
    static Bitmap bitmap;

    // ---------------Member methods --------------

    /**
     * Spawns alien either on left or right of the screen
     * @param xTotalPix total pixels of screen (width)
     * @param yTotalPix total pixels of the screen (height)
     */
    protected void spawn(int xTotalPix, int yTotalPix) {
        int randX, randY;

        // spawn alien w/ random speed & direction
        // on either side of the screen
        Random rand = new Random();
        // randX will either be 0 or xTotalPix
        randX = rand.nextInt(((xTotalPix - 1) + 1) + 1) * rand.nextInt(2);
        randY = rand.nextInt(((yTotalPix - 1) + 1) + 1);

        this.setHitBox(randX, randY);
    }

    @Override
    protected void update(int spaceWidth, int spaceHeight, long timeInMillisecond) {
        move(spaceWidth, spaceHeight, timeInMillisecond);
    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);

        hitboxWidth = bitmap.getWidth() * 0.5f;
        hitboxHeight = bitmap.getHeight() * 0.5f;

        hitbox.left -= hitboxWidth /2 ;
        hitbox.top -= hitboxHeight /2;
        hitbox.right += hitboxWidth/2;
        hitbox.bottom += hitboxHeight/2;
    }

    protected void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public float getPosX(){
        return hitbox.centerX();
    }

    public float getPosY() { return hitbox.centerY(); }

    public float getAngle() { return orientation; }

    public ObjectID getID() { return objectID; }

}
