package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
//Denne klasse, Walls, udvider Fieldaction, hvilket betyder, at det repræsenterer en form for handling,
// der kan finde sted på et felt på spilbrættet.
// Specifikt ser det ud til, at Walls repræsenterer en handling, der har noget at gøre med vægge på spilbrættet.
//Walls klassen implementerer metoden doAction(GameController gameController, Space space),
// som er et krav for enhver klasse, der udvider Fieldaction.
// Denne metode udfører en handling baseret på et givet GameController objekt og et Space objekt.
public class Walls extends Fieldaction{




    //I denne implementering henter doAction metoden først den spiller,
    // der er til stede på den givne space. Hvis der er en spiller på space (dvs. player er ikke null),
    // returnerer metoden true.
    // Hvis der ikke er en spiller på space (dvs. player er null), returnerer metoden false.
    //Uden mere kontekst, er det svært at afgøre, hvad denne true eller false værdi repræsenterer i forhold til "væggene".
    // Det kan være, om en spiller kan bevæge sig ind på et felt med en væg, om en væg blev ramt af en spiller,
    // eller noget helt andet. Derfor er det vigtigt at dokumentere, hvad metoder gør, især i abstrakte eller overordnede klasser som Fieldaction
    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
