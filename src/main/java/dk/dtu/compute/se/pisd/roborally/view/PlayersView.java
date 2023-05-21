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
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.scene.control.TabPane;

/**
 * @author Sofian Benomar
 */

//Klassen PlayersView repræsenterer visningen af spillerne i spillet. Den viser en fane for hver spiller og viser deres aktuelle position og antallet af checkpoints, de har passeret.
public class PlayersView extends TabPane implements ViewObserver {

    //board er en reference til Board-objektet, der indeholder spillerne.
    private Board board;

    //playerViews er et array af PlayerView-objekter, der repræsenterer visningerne for hver spiller.
    private PlayerView[] playerViews;


/*I konstruktøren oprettes en fane for hver spiller i board. Hver fane er en PlayerView, der viser spillerens navn, position og antallet af checkpoints. PlayerView-objekterne tilføjes til fanevisningen (TabPane).

Metoden updateView kaldes, når der sker en ændring i board-objektet. Den opdaterer visningen af spillerne baseret på den aktuelle spiller og deres positioner og checkpoints. Den aktuelle spiller markeres ved at vælge den tilhørende fane.

PlayersView er en del af brugergrænsefladen og er ansvarlig for at vise spillerne og opdatere deres visninger baseret på ændringer i spillet.*/
    public PlayersView(GameController gameController) {
        board = gameController.board;

        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        playerViews = new PlayerView[board.getPlayersNumber()];
        for (int i = 0; i < board.getPlayersNumber();  i++) {
            playerViews[i] = new PlayerView(gameController, board.getPlayer(i));
            this.getTabs().add(playerViews[i]);
        }
        board.attach(this);
        update(board);
    }

    @Override
    //Denne metode tager et Subject objekt som inputparameter og kaldes, når Subject objektet opdateres
    public void updateView(Subject subject) {

        //Denne betingelse tjekker, om det opdaterede Subject objekt er et Board objekt.
        if (subject == board)
        {
            /*Her oprettes en variabel current og sættes til den nuværende spiller på brættet.
             Derefter opdateres visningen, så den valgte spiller er markeret i brugergrænsefladen.*/
            Player current = board.getCurrentPlayer();
            this.getSelectionModel().select(board.getPlayerNumber(current));
            /*Her udskrives en besked i konsollen, der viser den aktuelle spillers navn,
            deres nuværende position på brættet og antallet af checkpoints, som spilleren har passeret.*/
            System.out.println("player: " + current.getName () + ": (" + current.getSpace ().x + ", " + current.getSpace ().y + ") has checkpoints: " + current.getCheckpointCount ());
            /*Dette er en løkke, der gennemgår spillerens checkpoint-koordinater
            og sammenligner dem med spillerens nuværende koordinater på brættet.
            Hvis spilleren er nået til et checkpoint, der ikke tidligere er blevet passeret,
            opdateres spillerens checkpoint-tæller og checkpoint-koordinaterne opdateres,
            så de nu er markeret som passeret.*/
            for (int i = 0; i < 6; i++){
                if ((current.getSpace().x == current.xcoords[i]) && (current.getSpace ().y == current.ycoords[i])){
                    if(!(current.xcheckpoints[i] == current.xcoords[i]) && !(current.ycheckpoints[i] == current.ycoords[i]))
                            current.xcheckpoints[i] = current.xcoords[i];
                            current.ycheckpoints[i] = current.ycoords[i];
                            current.incrementCheckpointCount ();
                            current.xcoords[i] = -1;
                            current.ycoords[i] = -1;
                    }
                }
            /*Her tjekkes det, om spilleren har passeret alle checkpoints,
            og hvis de har, udskrives en besked i konsollen, der viser, at spilleren har vundet.*/
            if(current.getCheckpointCount ()==6)
                System.out.println("Player " + current.getName () + " wins!");


        }
        }


}



