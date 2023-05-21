package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;
//Denne klasse, ConveyorBelt, er en underklasse af Fieldaction.
// Den repræsenterer sandsynligvis et transportbåndfelt på brættet i RoboRally-spillet, der påvirker hvordan en robot bevæger sig.

public class ConveyorBelt extends Fieldaction {
//Private variabel - heading: Dette er sandsynligvis en variabel, der angiver retningen, som transportbåndet fører til.
// Heading kunne være en enumeration eller en klasse, der repræsenterer forskellige retninger.
    private Heading heading;

//getHeading: Denne metode er en getter for heading variablen. Den returnerer den aktuelle værdi af heading.
    public Heading getHeading() {
        return heading;
    }

    //setHeading: Denne metode er en setter for heading variablen.
    // Den modtager en Heading parameter og sætter heading variablen til denne værdi.
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    //doAction: Denne metode er en override fra overklassen Fieldaction.
    // Den vil sandsynligvis udføre en handling baseret på transportbåndets egenskaber i spillet.
    // I det her tilfælde returnerer den bare false, så det virker som om implementeringen af denne handling endnu ikke er færdig.
    //Denne metode modtager to parametre: en instans af GameController og en instans af Space.
    // GameController er sandsynligvis klassen, der styrer spillets tilstand, og Space repræsenterer et bestemt sted på brættet.
    //Denne klasse er sandsynligvis designet til at blive udvidet med mere specifik funktionalitet.
    // F.eks. kunne doAction metoden senere implementeres til at flytte en robot, der står på dette felt, i den retning, som heading variablen angiver.
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        return false;
    }

}

