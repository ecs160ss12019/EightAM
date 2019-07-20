package EightAM.asteroids;

import android.content.Context;

import java.util.ArrayList;
import java.util.Deque;

abstract class Factory {
    GameModel model;
    Deque<Integer> objectsToDelete;

    abstract GameObject create(Context context);

    public void createObjectArray(Context context, ArrayList<GameObject> objects, int arraySize){
        for (int i = 0; i < arraySize; i++) {
            objects.add(this.create(context));
        }
    }

    public void destroy(ArrayList<GameObject> objects){
        while (objectsToDelete.size() > 0) {
            int objectIndex = objectsToDelete.pop();
            objects.remove(objectIndex);
        }
    }
}
