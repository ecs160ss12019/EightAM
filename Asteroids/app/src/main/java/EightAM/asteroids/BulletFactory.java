package EightAM.asteroids;

public class BulletFactory implements Factory {
    GameObject lastShooter;

    public BulletFactory(GameModel gameModel) {
        model = gameModel;
        objectsToDelete = new ArrayDeque<Integer>();
    }
    public GameObject create(Context context) {
        return (new Bullet(lastShooter));
    }

    public void fireBullet(Context context, ArrayList<GameObject> bulletsFired, GameObject shooter) {
        lastShooter = shooter;
        bulletsFired.add(create(context));
    }

    private void deleteOutOfRange(ArrayList<GameObject> bulletsFired) {
        for (int i = 0; i < bulletsFired.size(); i++) {
            if (bulletsFired.get(i).reachedMaxRange()) {
                objectsToDelete.push(i);
            }
        }
        this.destroy(bulletsFired);
    }
}
