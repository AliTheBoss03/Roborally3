package dk.dtu.compute.se.pisd.roborally.model;

public class Checkpoint extends Space {
    private int checkpointNumber;
    boolean hasPassed;

    public Checkpoint(Board board, int x, int y) {
        super(board, x, y);
        this.checkpointNumber = checkpointNumber;
    }

    public int getCheckpointNumber() {
        return checkpointNumber;
    }

    public boolean playerPassedCheckpoint(Player player) {
        player.incrementCheckpointCount();
        System.out.println(player.getName() + " optjente et checkpoint " + checkpointNumber);
        hasPassed = true;
        return hasPassed;
    }

    public void playPassedCheckpoint(Player checkingPlayer) {

    }

}




