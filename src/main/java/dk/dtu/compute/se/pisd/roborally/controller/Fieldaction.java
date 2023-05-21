package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

//Denne klasse, Fieldaction, er en abstrakt klasse, hvilket betyder, at den ikke kan instantieres direkte.
// Den tjener til at definere en generel struktur for andre klasser (såsom ConveyorBelt klassen, du tidligere delte),
// der arver fra den.
public abstract class Fieldaction {

      //doAction: Denne metode er erklæret som abstrakt, hvilket betyder, at den skal implementeres af enhver klasse, der arver fra Fieldaction.
      // Den tager to parametre: en instans af GameController og en instans af Space. GameController er sandsynligvis klassen,
      // der styrer spillets tilstand, og Space repræsenterer et bestemt sted på brættet.
      //Formålet med denne metode er sandsynligvis at definere en handling, der skal udføres i forhold til et bestemt felt på spillebrættet,
      // For eksempel i ConveyorBelt klassen kunne denne metode være ansvarlig for at flytte en robot hen over brættet.
      //Overordnet set er Fieldaction en abstrakt baseklasse, der definerer et interface for klasser, der repræsenterer handlinger,
      // der kan udføres på felter på spillebrættet i RoboRally-spillet.
        public abstract boolean doAction(GameController gameController, Space space);

    }

