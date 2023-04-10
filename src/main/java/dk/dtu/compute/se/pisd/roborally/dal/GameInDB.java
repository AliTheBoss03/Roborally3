package dk.dtu.compute.se.pisd.roborally.dal;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameInDB {

    public final int id;
    public final String name;

    public GameInDB(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }

}
