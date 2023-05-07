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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
/**
 * @author Sofian Benomar
 */

public class Player extends Subject {

   //Her oprettes en privat variabel af typen integer kaldet maxCheckpoints og starter med værdien 0.
   private int maxCheckpoints = 0;
   //Her oprettes offentlige arrays af typen integer kaldet xcheckpoints og ycheckpoints med en længde på 6.
    public int[] xcheckpoints = new int[6];
    public int[] ycheckpoints = new int[6];

//Her oprettes offentlige arrays af typen integer kaldet xcoords og ycoords med en længde på 6.
    public int[] xcoords = new int[6];
    public int[] ycoords = new int[6];

    //Her oprettes en offentlig metode kaldet findvinderen, der tager en liste af Player objekter som parameter og returnerer en Player objekt
    public Player findvinderen (List<Player> players) {
        //Her oprettes en Player objekt kaldet winner og initialiseres til null.
        Player winner = null;
        //Her løber vi igennem hver Player objekt i listen players.
        for (Player player : players) {
           //Her tjekker vi, om den aktuelle Player objekt har en checkpointCount på 6.
            if (player.getCheckpointCount() == 6) {
               /*Hvis Player objektet har en
                checkpointCount på 6, så sættes winner variablen til
                den pågældende Player, og maxCheckpoints variablen sættes til
                Player objektets checkpointCount, og en besked udskrives,
                der siger, at Player objektet har vundet */
                winner = player;
                maxCheckpoints = player.getCheckpointCount();
                System.out.println(player.getName () + " winner");
            }
            /*Hvis Player objektets checkpointCount ikke
             er 6, så tjekker vi, om checkpointCount er lig med
              maxCheckpoints variablen*/
            else if (player.getCheckpointCount() == maxCheckpoints)
            {
                //Hvis Player objektets checkpointCount er lig med maxCheckpoints, så sættes winner variablen til null.
                    winner = null;
            }
        }
        /*Her returneres winner variablen (som vil være null, hvis
        der ikke er nogen vinder, eller en Player objekt, hvis der er en vinder).*/
        return winner;

    }

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;
    private int moveCount;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;

    public Player(@NotNull Board board, String color, @NotNull String name) {
        xcoords[0] = 4;
        ycoords[0] = 1;
        xcoords[1] = 6;
        ycoords[1] = 2;
        xcoords[2] = 3;
        ycoords[2] = 2;
        xcoords[3] = 1;
        ycoords[3] = 4;
        xcoords[4] = 3;
        ycoords[4] = 3;
        xcoords[5] = 2;
        ycoords[5] = 4;
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public int getMoveCount(){
        return this.moveCount;
    }

    public void setMoveCount() {
        this.moveCount = this.moveCount+1;
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }


    /*Denne metode inkrementerer maxCheckpoints-variablen med en,
     hvilket betyder, at antallet af checkpoint,
    som spilleren har passeret, øges med 1.*/
    public void incrementCheckpointCount() {
        maxCheckpoints++;
    }
    /*Denne metode returnerer værdien af maxCheckpoints-variablen,
    som er antallet af checkpoints, som spilleren har passeret.*/
    public int getCheckpointCount() {
        return maxCheckpoints;


    }
}

