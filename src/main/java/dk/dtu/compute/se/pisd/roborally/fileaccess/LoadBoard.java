package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.SpaceTemplate;
import dk.dtu.compute.se.pisd.roborally.controller.Fieldaction;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.io.*;



/**
 * @author Amaan Ahemd, Mohammad Haashir Khan
 */
//LoadBoard-klassen indeholder metoder til indlæsning og gemmning af spillebræt i JSON-format.
public class LoadBoard {

    //loadBoard-metoden indlæser et spillebræt fra en JSON-fil. Hvis boardname er null,
    // bruges standardbrættet ("defaultboard"). Metoden bruger Gson-biblioteket til at deserialisere JSON-data til en BoardTemplate-objekt.
    // Derefter oprettes et nyt Board-objekt baseret på BoardTemplate, og alle rum, handlinger og vægge tilføjes i overensstemmelse med BoardTemplate.
    // Til sidst returneres det resulterende Board-objekt.
    private static final String BOARDSFOLDER = "boards";
    private static final String DEFAULTBOARD = "defaultboard" +"defaultboard2";


    private static final String JSON_EXT = "json";

    public static Board loadBoard(String boardname) {
        if (boardname == null) {
            boardname = DEFAULTBOARD;
        }
        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(Fieldaction.class, new Adapter<Fieldaction>());
        Gson gson = simpleBuilder.create();

        ClassLoader classLoader = LoadBoard.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(BOARDSFOLDER + "/" + boardname + "." + JSON_EXT);
        if (inputStream == null) {
            // TODO these constants should be defined somewhere
            return new Board(8,8);
        }
        Board result;
        JsonReader reader = null;
        try {
            reader = gson.newJsonReader(new InputStreamReader(inputStream));
            BoardTemplate template = gson.fromJson(reader, BoardTemplate.class);

            result = new Board(template.width, template.height);
            for (SpaceTemplate spaceTemplate: template.spaces) {
                Space space = result.getSpace(spaceTemplate.x, spaceTemplate.y);
                if (space != null) {
                    space.getActions().addAll(spaceTemplate.actions);
                    for (Heading wall: spaceTemplate.walls) {
                        space.addWall(wall);
                    }
                }
            }
            reader.close();
            return result;
        } catch (IOException e1) {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream = null;
                } catch (IOException e2) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {}
            }
        }
        return null;
    }
    //saveBoard-metoden gemmer et spillebræt som en JSON-fil med det angivne navn.
// Metoden opretter en BoardTemplate baseret på det givne Board-objekt ved at kopiere bredde,
// højde og rum med tilhørende handlinger og vægge.
// Derefter bruges Gson-biblioteket til at serialisere BoardTemplate-objektet til
// JSON-format og skrive det til en fil med det angivne navn.
    public static void saveBoard(Board board, String name) {
        BoardTemplate template = new BoardTemplate();
        template.width = board.width;
        template.height = board.height;

        for (int i=0; i<board.width; i++) {
            for (int j=0; j<board.height; j++) {
                Space space = board.getSpace(i,j);
                if (!space.getWalls().isEmpty() || !space.getActions().isEmpty()) {
                    SpaceTemplate spaceTemplate = new SpaceTemplate();
                    spaceTemplate.x = space.x;
                    spaceTemplate.y = space.y;
                    spaceTemplate.actions.addAll(space.getActions());
                    spaceTemplate.walls.addAll(space.getWalls());
                    template.spaces.add(spaceTemplate);
                }
            }
        }

        ClassLoader classLoader = LoadBoard.class.getClassLoader();

        String filename =
                classLoader.getResource(BOARDSFOLDER).getPath() + "/" + name + "." + JSON_EXT;


        GsonBuilder simpleBuilder = new GsonBuilder().
                registerTypeAdapter(Fieldaction.class, new Adapter<Fieldaction>()).
                setPrettyPrinting();
        Gson gson = simpleBuilder.create();

        FileWriter fileWriter = null;
        JsonWriter writer = null;
        try {
            fileWriter = new FileWriter(filename);
            writer = gson.newJsonWriter(fileWriter);
            gson.toJson(template, template.getClass(), writer);
            writer.close();
        } catch (IOException e1) {
            if (writer != null) {
                try {
                    writer.close();
                    fileWriter = null;
                } catch (IOException e2) {}
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {}


            }
        }
    }
//Begge metoder bruger en Adapter-klasse til at håndtere serialisering og deserialisering af objekter af typen Fieldaction.
// Klasserne BoardTemplate og SpaceTemplate er brugt til midlertidig opbevaring af spillebrættets data under indlæsning og gemmning.
}
