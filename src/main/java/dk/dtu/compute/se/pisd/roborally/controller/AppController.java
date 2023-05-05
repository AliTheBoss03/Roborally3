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

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);

    final private List<String> BOARD_OPTIONS = Arrays.asList("defaultbord","defaultboard2");

    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");



    final private RoboRally roboRally;

    private GameController gameController;


    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        ChoiceDialog<String> dialog2 = new ChoiceDialog<>(BOARD_OPTIONS.get(0), BOARD_OPTIONS);
        dialog2.setTitle("Choice of board");
        dialog2.setHeaderText("Select board");
        Optional<String> result2 = dialog2.showAndWait();


        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            //Board board = new Board(8,5);
            Board board = LoadBoard.loadBoard("defaultboard");

            Board board2 = LoadBoard.loadBoard("defaultboard2");


            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));


// Json fil 2
            }
            gameController = new GameController(board2);
            int no2 = result.get();
            for (int i = 0; i < no2; i++) {
                Player player = new Player(board2, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board2.addPlayer(player);
                player.setSpace(board2.getSpace(i % board2.width, i));
            }




            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /*
     * Metoden savegame bruges som et knap ind i spillet til at oprette spillet ind i databasen og dermed gemmes spillet i databasen så den kan loades senere
     * I metoden oprettes et IRepository som bruges til at kunne kalde metoden createGameInDB
     * Ind i metoden tages udgangspunkt i den gameboard som spillet foregår på
     */
    public void saveGame() {
        IRepository repo = RepositoryAccess.getRepository();
        repo.createGameInDB(gameController.board);
    }
/*
Metoden loadGame startes ud med et if statement der tjekker om der er et gameboard i gang, og dermed udføres koden ikke medmindre der ikke er en gameboard ignag
Der oprettes et IRepository som forrige metode for at kunne hente metoden loadGameFromDB og metoden getGames
Der oprettes en liste med typen GameInDB og den repository bruges til at kalde metoden getGames som returnerer alle de gemte spil
I den næste linje oprettes en Choice dialog som er et valgs vindue som popper op når der bliver trykket på loadgame, i den tages udgangspunkt i gameList som er den liste med de gemte games
Der sættes en titel for valgs vinduet og dermed også en overskrift
Resultaterne bliver fremvist og der ventes på et valg fra brugeren
Nedenunder bliver en if-statement udført hvis der er valgt et valg ved brug af isPresent som returnerer en boolean
ind i if statement oprettes et board med metoden loadGameFromDB som tager en id på det valgte board
nedenunder oprettes et gamecontroller med den board er blevet loadet i linjen over
Og i sidste linje optrettes spillets view med den oprettede gamecontroller i linjen over.
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

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
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

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
