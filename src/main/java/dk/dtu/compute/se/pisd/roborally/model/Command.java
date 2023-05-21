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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),

    // XXX Assignment V3 (step 1)
    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT);


    //displayName: En tekststreng, der viser kommandoens navn.
    final public String displayName;

    //options: En liste af andre kommandoer, der er tilgængelige som valgmuligheder for denne kommando.
    // Listen er uforanderlig og oprettet ved hjælp af Collections.unmodifiableList.
    final private List<Command> options;

    // Konstruktøren tager imod et navn og en valgfri liste af kommandoer som valgmuligheder.
    // Denne liste af kommandoer bruges kun i OPTION_LEFT_RIGHT-kommandoen. displayName og options-listen tildeles de angivne værdier.
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }
//isInteractive(): Returnerer true, hvis kommandoen har valgmuligheder, ellers returnerer den false.
    public boolean isInteractive() {
        return !options.isEmpty();
    }

//getOptions(): Returnerer listen af valgmuligheder for kommandoen.
    public List<Command> getOptions() {
        return options;
    }

}
