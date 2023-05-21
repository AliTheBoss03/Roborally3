/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.dal.*;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/*appcontrolleren er tilknyttet  applikation, som er Roboally

Denne klasse er AppController, som tilsyneladende håndterer hovedstyringen af RoboRally-applikationen.
Den implementerer Observer-interfacet, hvilket betyder,
 at den er designet til at reagere på opdateringer fra andre dele af applikationen.*/
public class AppController implements Observer {
//final er konstnter der ikk ændres
    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);

    final private List<String> BOARD_OPTIONS = Arrays.asList("Board 1","Board 2");

    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");



    final private RoboRally roboRally;
//gamecontrolleren viser om der er et spil startet eller ikke. hvis den er null er der ikk et spil startet
    private GameController gameController;


    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }
    /**
     * @author Ali Masoud
     * @author Amaan Ahmed
     */
/* her fortæller den hvor mange spiller der skal være.
    Denne metode bruges til at starte et nyt spil.
    Den viser brugerne valgmulighederne for antallet af spillere og bordvalget. Derefter opretter den et nyt GameController objekt og tilføjer de valgte antal spillere til spillet, hver med deres egen farve og navn.
     Derefter starter den programmeringsfasen af spillet og opretter brætvisningen.
     */
    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        ChoiceDialog<String> dialog2 = new ChoiceDialog<>(BOARD_OPTIONS.get(0), BOARD_OPTIONS);
        dialog2.setTitle("Choice of board");
        dialog2.setHeaderText("Select board");
        Optional<String> result2 = dialog2.showAndWait();

        String boardResult= String.valueOf(result2);


        if (result.isPresent()) {
            if (gameController != null) {

                if (!stopGame()) {
                    return;
                }
            }


            Board board = LoadBoard.loadBoard(boardResult);
            System.out.println(boardResult);





            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));


// Json fil 2
            }
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /*
     * Metoden savegame bruges som et knap ind i spillet til at oprette spillet ind i databasen og dermed gemmes spillet i databasen så den kan loades senere
     * I metoden oprettes et IRepository som bruges til at kunne kalde metoden createGameInDB
     * Ind i metoden tages udgangspunkt i den gameboard som spillet foregår på
     */
    /**
     * @author Ali Masoud
     */

    // Denne metode gemmer det aktuelle spil til databasen via et objekt af IRepository typen.
    public void saveGame() {
        IRepository repo = RepositoryAccess.getRepository();
        repo.createGameInDB(gameController.board);
    }
/*
loadGame: Denne metode henter en liste over spil fra databasen og viser brugeren en dialog,
der giver dem mulighed for at vælge, hvilket spil de vil indlæse.
Hvis brugeren vælger et spil, henter det spillet fra databasen, opretter et nyt GameController objekt,
og opretter brætvisningen.
     */
    public void loadGame() {
        if ( gameController == null) {
            IRepository repo = RepositoryAccess.getRepository();
            List<GameInDB> gameList = repo.getGames();
            ChoiceDialog<GameInDB> dialog = new ChoiceDialog<>(gameList.get(gameList.size() - 1), gameList);
            dialog.setTitle("Games options");
            dialog.setHeaderText("Select the game to load it");
            Optional<GameInDB> result1 = dialog.showAndWait();

            if (result1.isPresent()) {
               Board board = repo.loadGameFromDB(result1.get().id);
                gameController = new GameController(board);
                roboRally.createBoardView(gameController);
            }
        }
    }

//stopGame: Denne metode gemmer det aktuelle spil og sætter gameController til null, hvilket i praksis stopper spillet.
// Den opretter derefter brætvisningen med null, hvilket sikkert fjerner brætvisningen.
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }
//exit: Denne metode håndterer afslutningen af applikationen.
// Den viser en dialogboks, der spørger brugeren, om de er sikre på, at de vil afslutte RoboRally.
// Hvis brugeren bekræfter, at de vil afslutte, eller hvis der ikke er noget igangværende spil, afslutter den applikationen.
    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }
//isGameRunning: Denne metode returnerer en boolean, der angiver, om der i øjeblikket er et spil i gang.
// Hvis gameController ikke er null, betyder det, at der er et spil i gang.
    public boolean isGameRunning() {
        return gameController != null;
    }

///update: Da denne klasse implementerer Observer interfacet, er der en update metode, som skal implementeres.
// Denne metode kaldes, når objektet, som denne klasse observerer
// (dvs. et objekt af en klasse, der implementerer Subject interfacet), opdateres.
// I dette tilfælde ser det ud til, at denne metode endnu ikke er implementeret.
    @Override
    public void update(Subject subject) {

    }

}
