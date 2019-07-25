package EightAM.asteroids;

import java.util.Deque;

abstract class Factory {
    GameModel model;
    Deque<Integer> objectsToDelete;

    // Do not need maybe
    abstract public void removeAtIndex(int i);

    public void markToDelete(int i) {
        objectsToDelete.push(i);
    }

    public void removeMarked() {
        while (objectsToDelete.size() > 0) {
            int i = objectsToDelete.pop();
            this.removeAtIndex(i);
        }
    }
}
