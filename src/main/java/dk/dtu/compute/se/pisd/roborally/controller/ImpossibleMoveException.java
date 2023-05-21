package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
//Din ImpossibleMoveException klasse er en brugerdefineret undtagelse, som du har oprettet til dit Roborally-spil.
// Den udvider Throwable, som er overklassen til alle fejl og undtagelser i Java.
public class ImpossibleMoveException extends Throwable {

    //I dette tilfælde konstrueres din ImpossibleMoveException med tre parametre:
    // Player player, Space space og Heading heading.
    // I øjeblikket gemmes eller bruges disse parametre ikke udover konstruktøren.
    // Det kunne være nyttigt at bruge disse parametre til at give mere specifik information om undtagelsen,
    // for eksempel ved at gemme dem som instansvariabler og/eller inkludere dem i en fejlbesked.
    public ImpossibleMoveException(Player player, Space space, Heading heading) {

    }
}
