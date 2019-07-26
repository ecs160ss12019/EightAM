package EightAM.asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

protected class Particle extends GameObject {
    //Velocity vel;
    //RectF hitbox;
    float speed = 1;
    float angle;

    public Particle(float x, float y, float angle) {
        this.vel = new Velocity(speed, angle);
        setHitBox(x, y);
        this.paint = new Paint();

        Random rand = new Random();

        paint.setARGB(255,
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256));
    }

    protected void setHitBox(float posX, float posY) {
        // temp size of a particle
        this.hitbox = new RectF(posX-50, posY-50, posX+50, posY+50);
    }
    public void draw(Canvas canvas) {
        canvas.drawRect(this.hitbox, paint);
    }
}
