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
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */

//Klassen PlayerView repræsenterer visningen af en enkelt spiller i spillet. Den viser spillerens program (kommandokort) og kommandokort, samt knapper til at afslutte programmeringsfasen, udføre programmet og udføre det aktuelle kommandokort
public class PlayerView extends Tab implements ViewObserver {


    private Player player;

//top er en VBox, der indeholder visningselementerne for spilleren.
    private VBox top;

//programLabel er en etiket, der viser teksten "Program".
    private Label programLabel;

    //programPane er en GridPane, der indeholder visningerne af spillerens program (kommandokort).
    private GridPane programPane;

//cardsLabel er en etiket, der viser teksten "Command Cards".
    private Label cardsLabel;

    //cardsPane er en GridPane, der indeholder visningerne af spillerens kommandokort.
    private GridPane cardsPane;

    private CardFieldView[] programCardViews;
    private CardFieldView[] cardViews;

    //buttonPanel er en VBox, der indeholder knapperne til afslutning, udførelse og trinvis udførelse af programmet.
    private VBox buttonPanel;

    private Button finishButton;
    private Button executeButton;
    private Button stepButton;

    //playerInteractionPanel er en VBox, der indeholder visningselementerne til spillerens interaktion under udførelsen af programmet.
    private VBox playerInteractionPanel;

    //gameController er en reference til GameController-objektet, der styrer spillet.
    private GameController gameController;

    /*I konstruktøren oprettes og opsættes visningselementerne for spilleren. Knapperne og deres handlinger tilknyttes, og visningerne af spillerens program og kommandokort opdateres.

Metoden updateView kaldes, når der sker en ændring i player.board-objektet (brættet, som spilleren er tilknyttet). Metoden opdaterer visningen af spillerens program baseret på den aktuelle fase i spillet og den aktuelle spiller. Knapperne og deres tilstande opdateres også baseret på den aktuelle fase.

PlayerView er en del af brugergrænsefladen og er ansvarlig for at vise en enkelt spiller og opdatere visningen baseret på ændringer i spillet og spillerens tilstand.*/

    public PlayerView(@NotNull GameController gameController, @NotNull Player player) {
        super(player.getName());
        this.setStyle("-fx-text-base-color: " + player.getColor() + ";");

        top = new VBox();
        this.setContent(top);

        this.gameController = gameController;
        this.player = player;

        programLabel = new Label("Program");

        programPane = new GridPane();
        programPane.setVgap(2.0);
        programPane.setHgap(2.0);
        programCardViews = new CardFieldView[Player.NO_REGISTERS];
        for (int i = 0; i < Player.NO_REGISTERS; i++) {
            CommandCardField cardField = player.getProgramField(i);
            if (cardField != null) {
                programCardViews[i] = new CardFieldView(gameController, cardField);
                programPane.add(programCardViews[i], i, 0);
            }
        }


        finishButton = new Button("Finish Programming");
        finishButton.setOnAction( e -> gameController.finishProgrammingPhase());

        executeButton = new Button("Execute Program");
        executeButton.setOnAction( e-> gameController.executePrograms());

        stepButton = new Button("Execute Current Register");
        stepButton.setOnAction( e-> gameController.executeStep());

        buttonPanel = new VBox(finishButton, executeButton, stepButton);
        buttonPanel.setAlignment(Pos.CENTER_LEFT);
        buttonPanel.setSpacing(3.0);
        // programPane.add(buttonPanel, Player.NO_REGISTERS, 0); done in update now

        playerInteractionPanel = new VBox();
        playerInteractionPanel.setAlignment(Pos.CENTER_LEFT);
        playerInteractionPanel.setSpacing(3.0);

        cardsLabel = new Label("Command Cards");
        cardsPane = new GridPane();
        cardsPane.setVgap(2.0);
        cardsPane.setHgap(2.0);
        cardViews = new CardFieldView[Player.NO_CARDS];
        for (int i = 0; i < Player.NO_CARDS; i++) {
            CommandCardField cardField = player.getCardField(i);
            if (cardField != null) {
                cardViews[i] = new CardFieldView(gameController, cardField);
                cardsPane.add(cardViews[i], i, 0);
            }
        }

        top.getChildren().add(programLabel);
        top.getChildren().add(programPane);
        top.getChildren().add(cardsLabel);
        top.getChildren().add(cardsPane);

        if (player.board != null) {
            player.board.attach(this);
            update(player.board);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == player.board) {
            for (int i = 0; i < Player.NO_REGISTERS; i++) {
                CardFieldView cardFieldView = programCardViews[i];
                if (cardFieldView != null) {
                    if (player.board.getPhase() == Phase.PROGRAMMING ) {
                        cardFieldView.setBackground(CardFieldView.BG_DEFAULT);
                    } else {
                        if (i < player.board.getStep()) {
                            cardFieldView.setBackground(CardFieldView.BG_DONE);
                        } else if (i == player.board.getStep()) {
                            if (player.board.getCurrentPlayer() == player) {
                                cardFieldView.setBackground(CardFieldView.BG_ACTIVE);
                            } else if (player.board.getPlayerNumber(player.board.getCurrentPlayer()) > player.board.getPlayerNumber(player)) {
                                cardFieldView.setBackground(CardFieldView.BG_DONE);
                            } else {
                                cardFieldView.setBackground(CardFieldView.BG_DEFAULT);
                            }
                        } else {
                            cardFieldView.setBackground(CardFieldView.BG_DEFAULT);
                        }
                    }
                }
            }

            if (player.board.getPhase() != Phase.PLAYER_INTERACTION) {
                if (!programPane.getChildren().contains(buttonPanel)) {
                    programPane.getChildren().remove(playerInteractionPanel);
                    programPane.add(buttonPanel, Player.NO_REGISTERS, 0);
                }
                switch (player.board.getPhase()) {
                    case INITIALISATION:
                        finishButton.setDisable(true);
                        // XXX just to make sure that there is a way for the player to get
                        //     from the initialization phase to the programming phase somehow!
                        executeButton.setDisable(false);
                        stepButton.setDisable(true);
                        break;

                    case PROGRAMMING:
                        finishButton.setDisable(false);
                        executeButton.setDisable(true);
                        stepButton.setDisable(true);
                        break;

                    case ACTIVATION:
                        finishButton.setDisable(true);
                        executeButton.setDisable(false);
                        stepButton.setDisable(false);
                        break;

                    default:
                        finishButton.setDisable(true);
                        executeButton.setDisable(true);
                        stepButton.setDisable(true);
                }


            } else {
                if (!programPane.getChildren().contains(playerInteractionPanel)) {
                    programPane.getChildren().remove(buttonPanel);
                    programPane.add(playerInteractionPanel, Player.NO_REGISTERS, 0);
                }
                playerInteractionPanel.getChildren().clear();

                if (player.board.getCurrentPlayer() == player) {
                    Board board = player.board;
                    CommandCardField currentField = player.getProgramField(board.getStep());
                    CommandCard currentCard = currentField.getCard();
                    Command currentCommand = currentCard.command;
                    List<Command> options = currentCommand.getOptions();
                    Button optionButton = new Button("Left");
                    optionButton.setOnAction( e -> gameController.executeCommandOptionAndContinue(Command.LEFT));
                    optionButton.setDisable(false);
                    playerInteractionPanel.getChildren().add(optionButton);

                    optionButton = new Button("Right");
                    optionButton.setOnAction( e -> gameController.executeCommandOptionAndContinue(Command.RIGHT));
                    optionButton.setDisable(false);
                    playerInteractionPanel.getChildren().add(optionButton);

                }
            }
        }
    }

}
