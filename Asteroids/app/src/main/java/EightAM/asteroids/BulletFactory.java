package EightAM.asteroids;

import java.util.ArrayDeque;
import java.util.ArrayList;

import EightAM.asteroids.interfaces.Shootable;

public class BulletFactory extends Factory {
    Shootable lastShooter;

    public BulletFactory(GameModel gameModel) {
        model = gameModel;
        objectsToDelete = new ArrayDeque<Integer>();
    }

    public Bullet createNew() {
        return (new Bullet(lastShooter));
    }

    public void fireBullet(Shootable shooter) {
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

    private void destroy(ArrayList<Bullet> objects) {
        while (objectsToDelete.size() > 0) {
            int objectIndex = objectsToDelete.pop();
            objects.remove(objectIndex);
        }
    }
}
