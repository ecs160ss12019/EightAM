package EightAM.asteroids;

public class ObjectID {
    private static int currID = 0;
    private Integer id;
    private Faction faction;

    // Used only internally
    private ObjectID(Integer id, Faction faction) {
        this.id = id;
        this.faction = faction;
    }

    // Distributes a unique ID, for the purposes of the game anyway
    ObjectID getNewID(Faction type) {
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
