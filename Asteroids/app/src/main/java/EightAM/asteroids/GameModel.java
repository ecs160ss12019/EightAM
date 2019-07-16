package EightAM.asteroids;

import android.content.Context;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class GameModel {

    static final int STARTING_ASTEROIDS = 5;
    static final int STARTING_LIVES = 1;

    static int numOfAsteroids;
    static int spaceWidth;
    static int spaceHeight;
    static int livesLeft;

    //temp
    float shipPosX, shipPosY;

    protected List<Asteroid> asteroidBelt;
    protected List<Bullet> bulletsFired;
    protected Ship ship;

    protected GameModel(int screenWidth, int screenHeight,Context context) {
        //TODO: Initialize Grid... Maybe?

        numOfAsteroids = STARTING_ASTEROIDS;
        livesLeft = STARTING_LIVES;
        ship = new Ship(spaceWidth,spaceHeight, context);
        this.createAsteroidBelt(context);
    }

    private void createAsteroidBelt(Context context /*, float shipPosX, float shipPosY*/){
        for (int i = 0; i < numOfAsteroids; i ++){
            asteroidBelt.add(new Asteroid(spaceWidth,spaceHeight,shipPosX, shipPosY, context));
        }
    }

    private void createBullet(GameObject shooter){
        //TODO: get Position and Angle/Orientation of the Shooter (Ship and Alien)
        //For Ship Team to figure out
        float shooterPosX, shooterPosY, shooterAngle;
        bulletsFired.add(new Bullet(shooter, shooterPosX, shooterPosY, shooterAngle));
    }

    private void updateAsteroidBelt(long timeInMillisecond){
        for (int i = 0; i < numOfAsteroids; i ++){
            asteroidBelt.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    private void checkBulletRange() {
        Deque<Integer> bulletsToDelete = new ArrayDeque<Integer>();
        for (int i = 0; i < bulletsFired.size(); i ++){
            if (bulletsFired.get(i).reachedMaxRange()){
                bulletsToDelete.push(i);
            }
        }
        while(bulletsToDelete.size() > 0){
            int bulletIndex = bulletsToDelete.pop();
            bulletsFired.remove(bulletIndex);
        }
    }

    private void updateBullets(long timeInMillisecond){
        this.checkBulletRange();
        for (int i = 0; i < bulletsFired.size(); i ++){
            bulletsFired.get(i).update(spaceWidth, spaceHeight, timeInMillisecond);
        }
    }

    protected void detectCollisions() {

    }

    protected void update(long timeInMillisecond) {
        this.ship.update(spaceWidth, spaceHeight, timeInMillisecond);
        this.updateAsteroidBelt(timeInMillisecond);
        if (bulletsFired.size() != 0) updateBullets(timeInMillisecond);
    }

    protected void removeEntity() {

    }
}
