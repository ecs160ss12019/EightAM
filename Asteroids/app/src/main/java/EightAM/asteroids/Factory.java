package EightAM.asteroids;

public interface Factory {
    GameObject create(Context context, GameObject object);

    void destroy(GameObject object);

    void respawn(GameObject object);

}
