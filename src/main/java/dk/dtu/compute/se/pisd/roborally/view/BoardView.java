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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

//Klassen BoardView repræsenterer visningen af brættet i spillet
public class BoardView extends VBox implements ViewObserver {

    //board er referencen til brættet, som visningen er knyttet til.
    private Board board;

    //mainBoardPane er en GridPane, der indeholder visningen af selve brættet.
    private GridPane mainBoardPane;

    //spaces er en todimensional array af SpaceView-objekter, der repræsenterer visningen af hver enkelt plads på brættet.
    private SpaceView[][] spaces;

    //playersView er en PlayersView-objekt, der repræsenterer visningen af spillerne.
    private PlayersView playersView;

    //statusLabel er en Label, der viser den aktuelle statusbesked.
    private Label statusLabel;

    //spaceEventHandler er en SpaceEventHandler, der håndterer museklik på pladserne på brættet
    private SpaceEventHandler spaceEventHandler;

    /*I konstruktøren oprettes visningen af brættet ved at oprette SpaceView-objekter for hver plads på brættet og tilføje dem til mainBoardPane. Der oprettes også en SpaceEventHandler og knyttes til hver SpaceView for at håndtere museklik.

Metoden updateView kaldes, når der sker en ændring i board-objektet. Den opdaterer statusbeskeden på statusLabel baseret på den aktuelle fase i spillet.

Den indre klasse SpaceEventHandler er ansvarlig for at håndtere museklik på pladserne på brættet. Når der klikkes på en plads, opdateres spillet ved at flytte den aktuelle spiller til den valgte plads.

Klassen implementerer også ViewObserver-grænsefladen for at kunne modtage opdateringer fra board-objektet og reagere på dem.

BoardView er en del af brugergrænsefladen og er ansvarlig for at vise brættet og håndtere interaktioner med brættet.*/
    public BoardView(@NotNull GameController gameController) {
        board = gameController.board;

        mainBoardPane = new GridPane();
        playersView = new PlayersView(gameController);
        statusLabel = new Label("<no status>");

        this.getChildren().add(mainBoardPane);
        this.getChildren().add(playersView);
        this.getChildren().add(statusLabel);

        spaces = new SpaceView[board.width][board.height];
        System.out.println(board.width);
        System.out.println(board.height);

        spaceEventHandler = new SpaceEventHandler(gameController);

        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Space space = board.getSpace(x, y);
                SpaceView spaceView = new SpaceView(space);
                spaces[x][y] = spaceView;
                mainBoardPane.add(spaceView, x, y);
                spaceView.setOnMouseClicked(spaceEventHandler);
            }
        }

        board.attach(this);
        update(board);
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == board) {
            Phase phase = board.getPhase();
            statusLabel.setText(board.getStatusMessage());
        }
    }


    private class SpaceEventHandler implements EventHandler<MouseEvent> {

        final public GameController gameController;

        public SpaceEventHandler(@NotNull GameController gameController) {
            this.gameController = gameController;
        }

        @Override
        public void handle(MouseEvent event) {
            Object source = event.getSource();
            if (source instanceof SpaceView) {
                SpaceView spaceView = (SpaceView) source;
                Space space = spaceView.space;
                Board board = space.board;

                if (board == gameController.board) {
                    gameController.moveCurrentPlayerToSpace(space);
                    event.consume();
                }
            }
        }

    }

}
