package dk.dtu.compute.se.pisd.roborally.model;

public class Checkpoint {

    String[] Checkpoints = new String[5];

    {
        Checkpoints[0] = "Checkpoint 1";
        Checkpoints[1] = "Checkpoint 2";
        Checkpoints[2] = "Checkpoint 3";
        Checkpoints[3] = "Checkpoint 4";
        Checkpoints[4] = "Checkpoint 5";
        Checkpoints[5] = "Checkpoint 6";

        System.out.println(Checkpoints[0]);
        System.out.println(Checkpoints[1]);
        System.out.println(Checkpoints[2]);
        System.out.println(Checkpoints[3]);
        System.out.println(Checkpoints[4]);
        System.out.println(Checkpoints[5]);
    }
    // Definer en robot
    private static int robotX = 0;
    private static int robotY = 0;

    // Definer en funktion til at flytte robotten
    private static void moveRobot(int dx, int dy) {
        robotX += dx;
        robotY += dy;




    }

}

