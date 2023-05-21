package dk.dtu.compute.se.pisd.roborally.fileaccess.model;
import dk.dtu.compute.se.pisd.roborally.controller.Fieldaction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

import java.util.ArrayList;
import java.util.List;


//SpaceTemplate-klassen repræsenterer skabelonen for et enkelt rum på et spillebræt.
// Den indeholder information om rummets placering (x og y koordinater) samt lister over væggene (walls)
// og handlingerne (actions) forbundet med rummet.
public class SpaceTemplate {

//Attributten walls er en liste af Heading-objekter, der angiver hvilke retninger der har en væg i rummet.
// En Heading repræsenterer en retning som f.eks. nord, syd, øst eller vest.
    public List<Heading> walls = new ArrayList<>();

    //Attributten actions er en liste af Fieldaction-objekter, der repræsenterer de handlinger, der er knyttet til rummet.
    // En Fieldaction kan være en handling, der udføres, når en spiller lander på rummet.
    public List<Fieldaction> actions = new ArrayList<>();

    //Attributterne x og y angiver koordinaterne for rummets placering på brættet.
    public int x;
    public int y;

}
//Denne klasse bruges sandsynligvis til at definere og gemme oplysninger om hvert enkelt rum på et bræt i form af en skabelon.
// Når et bræt oprettes i spillet, vil SpaceTemplate-objekterne blive brugt til at oprette de faktiske Space-instanser
// på brættet med de korrekte vægge og handlinger.