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
import dk.dtu.compute.se.pisd.roborally.controller.Fieldaction;
import dk.dtu.compute.se.pisd.roborally.controller.Walls;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

//Klassen SpaceView repræsenterer visningen af et enkelt felt (space) på brættet i RoboRally-spillet.
// Den er ansvarlig for at vise feltets udseende, herunder farven og eventuelle vægge eller spillere på feltet
public class SpaceView extends StackPane implements ViewObserver {

//SPACE_HEIGHT og SPACE_WIDTH angiver højden og bredden af feltet.
    final public static int SPACE_HEIGHT = 60; // 60; // 75;
    final public static int SPACE_WIDTH = 60;  // 60; // 75;

    //space er en reference til det Space-objekt, som feltet repræsenterer.
    public final Space space;

    /*Konstruktøren opsætter visningen af feltet ved at indstille størrelsen og baggrundsfarven baseret på feltets koordinater.
     Hvis koordinaternes sum er et lige tal, får feltet en hvid baggrundsfarve, ellers får det en sort baggrundsfarve.
     Derudover kan visse specifikke koordinater give feltet en lyserød baggrundsfarve.*/

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }
        if (space.x == 4  && space.y == 1) {
            this.setStyle("-fx-background-color: pink;");

        }
        if (space.x == 6  && space.y == 2) {
            this.setStyle("-fx-background-color: pink;");
        }
        if (space.x == 3  && space.y == 2) {
            this.setStyle("-fx-background-color: pink;");
        }
        if (space.x == 1  && space.y == 4) {
            this.setStyle("-fx-background-color: pink;");
        }
        if (space.x == 3  && space.y == 3) {
            this.setStyle("-fx-background-color: pink;");
        }
        if (space.x == 2  && space.y == 4) {
            this.setStyle("-fx-background-color: pink;");
        }

        space.attach(this);
        update(space);
    }

    //updatePlayer()-metoden opdaterer visningen af eventuelle spillere på feltet ved at tilføje en pil (Polygon) med spillerens farve og retning.
    private void updatePlayer() {
        this.getChildren().clear();

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }


//updateView()-metoden bliver kaldt, når feltet eller tilknyttede objekter ændrer sig.
// Den opdaterer visningen af feltet ved at tegne eventuelle vægge og opdatere spillernes visning.
    @Override
    public void updateView(Subject subject) {
        this.getChildren().clear();
        if (subject == this.space) {
            for (Fieldaction action:space.getActions() ) {
                if (action instanceof Walls) {
                    drawWalls();
                }
            }
            drawWalls();
            updatePlayer();
        }

    }
    /**
     * @author Ali Masoud
     */

    //drawWalls()-metode, der bruges til at tegne væggene på feltet.
    // Denne metode kaldes fra updateView() for at opdatere visningen af væggene.
    public void drawWalls() {
        Pane wallsPane = new Pane();
        Rectangle rectangle =
                new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
        rectangle.setFill(Color.RED);
        wallsPane.getChildren().add(rectangle);
        Line line = new Line();
        line.setStroke(Color.RED);
        line.setStrokeWidth(4);
        for (Heading heading : space.getWalls()) {
            switch (heading) {
                case SOUTH:
                    line = new Line(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                    break;
                case WEST:
                    line = new Line(2, 2, 2, SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    line = new Line(2, 2, SPACE_WIDTH - 2, 2);
                    break;
                case EAST:
                    line = new Line(SPACE_WIDTH - 2, 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                    break;
            }

            wallsPane.getChildren().add(line);
        }
        this.getChildren().add(wallsPane);
    }
}
