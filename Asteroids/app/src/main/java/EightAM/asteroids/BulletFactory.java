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
    public GameObject create(Context context) {
        return (new Bullet(lastShooter));
    }

    public void fireBullet(Context context, ArrayList<GameObject> bulletsFired, Shooter shooter) {
        lastShooter = shooter;
        bulletsFired.add(create(context));
    }

    private void deleteOutOfRange(ArrayList<GameObject> bulletsFired) {
        for (int i = 0; i < bulletsFired.size(); i++) {
            Bullet currBullet = (Bullet) bulletsFired.get(i);
            if (currBullet.reachedMaxRange()) {
                objectsToDelete.push(i);
            }
        }
        this.destroy(bulletsFired);
    }
}
