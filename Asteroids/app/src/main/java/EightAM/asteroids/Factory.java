package EightAM.asteroids;

import android.content.Context;

abstract class Factory {
    GameModel model;
    Deque<Integer> objectsToDelete;

    abstract GameObject create(Context context, GameObject object);

    public void createObjectArray(Context context, ArrayList<GameObject> objects, int arraySize){
        for (int i = 0; i < arraySize; i++) {
            objects.add(this.create(context, object));
        }
    }

    public void destroy(ArrayList<GameObject> objects){
        while (objectsToDelete.size() > 0) {
            int objectIndex = objectsToDelete.pop();
            objects.remove(objectIndex);
        }
    }
}
