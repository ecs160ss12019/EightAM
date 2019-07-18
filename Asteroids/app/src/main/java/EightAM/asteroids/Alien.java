package EightAM.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

class Alien extends GameObject {
    // ---------------Member statics --------------
    static final int MAXSPEED = 3;
    private Size size;

    // denotes what kind of alien
    // ---------------Member methods --------------
    protected Alien(int xTotalPix, int yTotalPix, Size s, Context context) {
        float randX, randY;
        float speed, direction = 0;

        // spawn alien w/ random speed & direction
        // on either side of the screen
        Random rand = new Random();
        // randX will either be 0 or xTotalPix
        randX = rand.nextInt(xTotalPix) * rand.nextInt(2);
        randY = rand.nextInt(yTotalPix);

        speed = rand.nextInt(MAXSPEED / 2);
        this.vel = new Velocity(speed, direction);
        this.setHitBox(randX, randY);

        size = s; // set size of alien

        // might use later
        this.objectID = ObjectID.ALIEN;

        // prepare bitmap
        // TODO: put ic.alien_png
        // TODO: change alien pic based on size
        //this.bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_alien_large);
    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);
    }

    protected void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(orientation), (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (bitmapWidth * 0.5f), hitbox.top - (bitmapHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(this.bitmap, matrix, paint);
    }

    // ---------------Member variables ------------
    enum Size {
        SMALL, LARGE
    }
}
