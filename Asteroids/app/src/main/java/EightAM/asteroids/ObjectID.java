package EightAM.asteroids;

public class ObjectID {
    private static int currID = 0;
    private Integer id;
    private Faction faction;

    /**
     * Object ID is used to give each object a unique ID such that it can be referred to
     * in the GameModel's ObjectMap.
     *
     * It also contains the Faction ID, which is used to determine hit detection and point
     * scoring.
     *
     * Only used internally
     * @param id
     * @param faction
     */

    private ObjectID(Integer id, Faction faction) {
        this.id = id;
        this.faction = faction;
    }

    /**
     * Distributes a unique ID, and associates the ID with a faction
     * @param type
     * @return
     */
    public static ObjectID getNewID(Faction type) {
        currID++;
        return new ObjectID(currID, type);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectID)) return false;
        return id.equals(((ObjectID) o).id);
    }

    Integer getId() { return id;}

    Faction getFaction() { return faction;}
}
