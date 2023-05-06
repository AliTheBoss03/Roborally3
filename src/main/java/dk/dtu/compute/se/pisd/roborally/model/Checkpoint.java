package dk.dtu.compute.se.pisd.roborally.model;

/*Her oprettes en klasse kaldet Checkpoint
 som nedarver fra klassen Space. Der oprettes
 også en privat integer variabel
 kaldet checkpointNumber og en boolean variabel kaldet hasPassed*/
public class Checkpoint extends Space {
    private int checkpointNumber;
    boolean hasPassed;

    /*Dette er konstruktøren for Checkpoint-klassen,
     som tager et Board objekt og to integer værdier (x og y) som inputparametre.
      Den kaldes, når et nyt Checkpoint objekt oprettes,
       og det sætter checkpointNumber variablen til en værdi.*/
    public Checkpoint(Board board, int x, int y) {
        super(board, x, y);
        this.checkpointNumber = checkpointNumber;

    }
//Denne metode returnerer værdien af checkpointNumber variablen, som er nummeret på det aktuelle checkpoint.
    public int getCheckpointNumber() {
        return checkpointNumber;
    }
/*Denne metode tager en Player objekt
 som inputparameter og øger Player objektets checkpointCount med 1, og
  sætter også hasPassed-variablen til true.
   Derefter udskrives en besked, der viser, at Player objektet har passeret checkpointet og
   hvilket checkpoint-nummer det var. Denne metode returnerer også hasPassed-variablen.
 */
    public boolean playerPassedCheckpoint(Player player) {
        player.incrementCheckpointCount();
        System.out.println(player.getName() + " optjente et checkpoint " + checkpointNumber);
        hasPassed = true;
        return hasPassed;
    }
/*Denne metode tager en Player objekt som inputparameter, men den gør ikke noget i øjeblikket.
Det ser ud til, at den er beregnet til at blive udvidet senere med yderligere funktioner.*/
    public void playPassedCheckpoint(Player checkingPlayer) {

    }

}




