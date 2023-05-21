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
package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import java.util.ArrayList;
import java.util.List;

//BoardTemplate-klassen repræsenterer skabelonen for et spillebræt.
// Den indeholder bredde og højde for brættet og en liste af SpaceTemplate-objekter,
// der repræsenterer de individuelle rum på brættet.
public class BoardTemplate {

    //Attributterne width og height angiver henholdsvis bredden og højden på brættet.
    public int width;
    public int height;
//Attributten spaces er en liste, der indeholder SpaceTemplate-objekter.
// Hvert SpaceTemplate-objekt repræsenterer et enkelt rum på brættet og beskriver egenskaberne for det pågældende rum.
    public List<SpaceTemplate> spaces = new ArrayList<>();
//Denne klasse bruges sandsynligvis til at definere og gemme oplysninger om et bræt,
// der kan bruges til at oprette en faktisk Board-instans i spillet.
// BoardTemplate fungerer som en skabelon eller en plan for, hvordan brættet skal se ud,
// og indeholder de nødvendige oplysninger til at oprette brættet i spillet.


}
