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

public class CommandCardField extends Subject {

    final public Player player;

    private CommandCard card;

    //visible: En boolsk værdi, der angiver om kortet er synligt eller ej.
    private boolean visible;

    //Konstruktøren tager imod en reference til en spiller og tildeler den til player-variablen.
    // Kortet (card) initialiseres som null, og visible sættes som true.
    public CommandCardField(Player player) {
        this.player = player;
        this. card = null;
        this.visible = true;
    }
//getCard(): Returnerer kommandokortet (card) på feltet.
    public CommandCard getCard() {
        return card;
    }

    //setCard(CommandCard card): Tildeler et nyt kommandokort til feltet og notifierer ændringer
    public void setCard(CommandCard card) {
        if (card != this.card) {
            this.card = card;
            notifyChange();
        }
    }

    //isVisible(): Returnerer en boolsk værdi, der angiver om kortet er synligt eller ej.
    public boolean isVisible() {
        return visible;
    }

    //setVisible(boolean visible): Sætter synligheden af kortet og notifierer ændringer.
    public void setVisible(boolean visible) {
        if (visible != this.visible) {
            this.visible = visible;
            notifyChange();
        }
    }

}
