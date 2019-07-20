package EightAM.asteroids;

import static EightAM.asteroids.Constants.ALIEN_MAXSPEED;

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
    static Bitmap bitmap;

    // denotes what kind of alien
    // ---------------Member methods --------------
    protected Alien(int xTotalPix, int yTotalPix, Context context) {
        // TODO: implement different alien sizes
        // randomly choose if large or small alien?

        int randX, randY;
        float speed, direction = 0;
        // prepare bitmap
        if (bitmap == null) bitmap = ImageUtils.getVectorBitmap(context, R.drawable.ic_alien);
        hitboxWidth = bitmap.getWidth() * 0.5f;
        hitboxHeight = bitmap.getHeight() * 0.5f;

        // spawn alien w/ random speed & direction
        // on either side of the screen
        Random rand = new Random();
        // randX will either be 0 or xTotalPix
        randX = rand.nextInt(((xTotalPix - 1) + 1) + 1) * rand.nextInt(2);
        randY = rand.nextInt(((yTotalPix - 1) + 1) + 1);

        speed = 1 + rand.nextFloat() * ((ALIEN_MAXSPEED / 4) - 1);
        this.vel = new Velocity(speed, direction);
        this.setHitBox(randX, randY);

        //size = s; // set size of alien

        // might use later
        this.objectID = ObjectID.ALIEN;

        // TODO: change alien pic based on size
    }

    protected void setHitBox(float posX, float posY) {
        hitbox = new RectF(posX, posY, posX, posY);

        hitbox.left -= hitboxWidth /2 ;
        hitbox.top -= hitboxHeight /2;
        hitbox.right += hitboxWidth/2;
        hitbox.bottom += hitboxHeight/2;
    }

    protected void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) Math.toDegrees(90 * Math.PI / 180), (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        matrix.postTranslate(hitbox.left - (hitboxWidth * 0.5f), hitbox.top - (hitboxHeight * 0.5f));
        canvas.drawRect(this.hitbox, paint);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
