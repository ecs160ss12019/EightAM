package EightAM.asteroids;

import android.content.Context;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BulletFactory extends Factory {
    Shooter lastShooter;

    public BulletFactory(GameModel gameModel) {
        model = gameModel;
        objectsToDelete = new ArrayDeque<Integer>();
    }

    public Bullet createNew() {
        return (new Bullet(lastShooter));
    }

    public void fireBullet(Shooter shooter) {
        lastShooter = shooter;
        model.bulletsFired.add(createNew());
    }

    public void removeAtIndex(int asteroidIndex) {
        model.bulletsFired.remove(asteroidIndex);
    }

    public void deleteOutOfRange() {
        for (int i = 0; i < model.bulletsFired.size(); i++) {
            if (model.bulletsFired.get(i).reachedMaxRange()) {
                objectsToDelete.push(i);
            }
        }
        this.destroy(model.bulletsFired);
    }

    private void destroy(ArrayList<Bullet> objects){
        while (objectsToDelete.size() > 0) {
            int objectIndex = objectsToDelete.pop();
            objects.remove(objectIndex);
        }
    }
}
