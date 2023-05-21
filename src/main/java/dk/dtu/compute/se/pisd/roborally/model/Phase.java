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

//Phase er en enum-klasse, der repræsenterer forskellige faser eller stadier af spillet. De definerede faser er:
//INITIALISATION: Fasen hvor spillet bliver initialiseret eller opsat.
//PROGRAMMING: Fasen hvor spillerne planlægger og programmerer deres robotter.
//ACTIVATION: Fasen hvor robotterne udfører deres programmerede handlinger.
//PLAYER_INTERACTION: Fasen hvor der er interaktion mellem spillerne, f.eks. handel eller kommunikation.
//SETUP: Fasen hvor spillet bliver sat op, f.eks. opsætning af brættet eller placering af objekter.
//END_OF_GAME: Fasen hvor spillet er afsluttet eller ved at afslutte.
public enum Phase {
    INITIALISATION, PROGRAMMING, ACTIVATION, PLAYER_INTERACTION, SETUP, END_OF_GAME

}
//Disse faser beskriver de forskellige stadier, som spillet kan være i,
// og kan bruges til at styre spillogikken eller informere spillere om, hvilken fase spillet befinder sig i.