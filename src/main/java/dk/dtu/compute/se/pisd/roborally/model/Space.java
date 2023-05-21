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

import java.util.ArrayList;
import java.util.List;


//Klassen Space repræsenterer en plads på brættet.
public class Space extends Subject {

//walls er en liste af Heading-objekter, der angiver væggene omkring pladsen.
    private List<Heading> walls = new ArrayList<>();

    //actions er en liste af Fieldaction-objekter, der repræsenterer de handlinger, der kan udføres på pladsen.
    private List<Fieldaction> actions = new ArrayList<>();

    //board er referencen til det bræt, som pladsen tilhører.
    public final Board board;

    public final int x;
    public final int y;


    private Player player;


    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }
    //addWall tilføjer en væg i en bestemt retning til pladsen.
    public void addWall(Heading heading) {
        if (!walls.contains(heading)) {
            walls.add(heading);
        }
    }
    //getPlayer og setPlayer giver adgang til at læse og ændre den spiller, der befinder sig på pladsen.
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
    //getWalls og getActions giver adgang til at læse væggene og handlingerne for pladsen.
    public List<Heading> getWalls() {
        return walls;
    }

    public List<Fieldaction> getActions() {
        return actions;
    }

    //playerChanged er en metode, der bliver kaldt af spilleren for
    // at informere pladsen om ændringer i spillerens attributter.
    void playerChanged() {

        notifyChange();
    }
//Klassen arver fra Subject, hvilket betyder, at den kan registrere
// og underrette observere om ændringer i sine attributter.
}
