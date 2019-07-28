//package EightAM.asteroids;
//
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class ParticleEffects {
//    ArrayList<Particle> particles;
//    boolean hasExploded;
//    int explodeDuration;
//    int numParticles;
//    float x;
//    float y;
//    Paint paint;
//
//    GameModel model;
//
//    protected ParticleEffects(GameModel model) {
//        this.model = model;
//        this.paint = new Paint();
//        hasExploded = false;
//        explodeDuration = 1000;
//        numParticles = 10;
//        init(numParticles);
//    }
//
//
//    protected void init(int numParticles) {
//        particles = new ArrayList<>();
//
//        for (int i=0; i<numParticles; i++) {
//            Random rand = new Random();
//            float angle = (rand.nextInt(360));
//            angle = angle * 3.14f / 180f;
//            particles.add(new Particle(x, y, angle));
//        }
//    }
//
//    void update(long timeInMilliseconds) {
//        explodeDuration--;
//
//        for (Particle p:particles)
//            p.update(model.spaceWidth, model.spaceHeight, timeInMilliseconds);
//
//        if (explodeDuration < 0)
//            hasExploded = false;
//    }
//
//    void emitParticles(float x, float y) {
//        hasExploded = true;
//        explodeDuration = 500;
//        this.x = x;
//        this.y = y;
//    }
//
//    public void draw(Canvas canvas) {
//        for(Particle p:particles)
//            p.draw(canvas);
//    }
//}
