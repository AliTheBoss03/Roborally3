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



//Heading er en enum-klasse, der repræsenterer mulige retninger eller hovedpunkter.
// De definerede retninger er: SOUTH, WEST, NORTH og EAST.

public enum Heading {

    SOUTH, WEST, NORTH, EAST;

   //next(): Returnerer den næste retning i rækkefølgen af retninger.
   // Hvis den aktuelle retning er EAST, vil næste retning være SOUTH.
    public Heading next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
//prev(): Returnerer den foregående retning i rækkefølgen af retninger.
// Hvis den aktuelle retning er EAST, vil den foregående retning være NORTH.
    public Heading prev() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
    }
}
//Disse metoder gør det muligt at skifte til den næste eller foregående retning i en sekvens af retninger
// uden at skulle arbejde direkte med rækkefølgen af retninger.