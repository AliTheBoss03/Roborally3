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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.Fieldaction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {

    public final Board board;

    public final int x;
    public final int y;
    private Player player;
    private Wall wall;
    private Coneyerbelt conveyerbelt;
    private boolean wallbool;
    private Checkpoint checkpoint;
    private boolean CheckpointBool;





    public Checkpoint getCheckpoint(){
        return checkpoint;
    }

    public Wall getWall() {
        return wall;
    }
    public void movePlayer(Space space, GameController gameController, Board board){

    }
    public Coneyerbelt getConveyerbelt() {
        return conveyerbelt;
    }



    public void setConveyerbelt(Coneyerbelt conveyerbelt) {
        this.conveyerbelt = conveyerbelt;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }


    public void setWall(Wall wall) {
        Wall oldwall = this.wall;

        if (wall != oldwall &&
                ( wall == null || board == wall.board))  {
            this.wall = wall ;
            if (oldwall != null) {
                // this should actually not happen
                oldwall.setSpace(null);
            }
            if (wall != null) {
                wall.setSpace(this);
            }
            notifyChange();
        }

    }

    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
        wall = null;
        wallbool = false;
        checkpoint = null;
        CheckpointBool = false;

    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }


    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }
    // En liste for at holde Fieldaction objekter.
// Fieldaction er en abstrakt repræsentation af en handling, der skal udføres på et Space.
    private List<Fieldaction> fieldActions = new ArrayList<>();

    // Getter metode til at hente listen af fieldActions.
// Dette giver andre klasser mulighed for at se, hvilke actions der er tilknyttet til dette Space.
    public List<Fieldaction> getFieldActions() {
        return fieldActions;
    }

    // En metode til at tilføje en ny Fieldaction til listen.
// Metoden sikrer, at den action, der skal tilføjes, ikke allerede findes i listen (for at undgå duplikater)
// og at den ikke er null, før den bliver tilføjet.
    public void addFieldAction(Fieldaction action) {
        if (action != null && !fieldActions.contains(action)) {
            fieldActions.add(action);
        }
    }

    // En metode til at fjerne en Fieldaction fra listen.
// Returnerer true, hvis action blev fjernet succesfuldt; ellers returnerer false.
// Det kan være false, hvis action ikke findes i listen.
    public boolean removeFieldAction(Fieldaction action) {
        return fieldActions.remove(action);
    }

//måske ændres
    public Collection<Fieldaction> getActions() {
        return fieldActions;
    }
}



