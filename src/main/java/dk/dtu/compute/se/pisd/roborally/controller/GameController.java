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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    /*
    Denne metode tager udgangspunkt i den nuværende spiller og flytter spilleren til den valgte space hvis den er ledig
    Metoden sætter også movecounter op hver gang spilleren bevæges.
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        if (space.getPlayer() == null) {
            Player currentplayer = board.getCurrentPlayer();
            space.setPlayer(currentplayer);
            currentplayer.setMoveCount();


            int currentPlayerNumber = board.getPlayerNumber(currentplayer);
            int nextPlayerNumber = (currentPlayerNumber + 1);
            Player nextPlayer = board.getPlayer(nextPlayerNumber);

            if (board.getPlayer(nextPlayerNumber) != null){
                board.setCurrentPlayer(nextPlayer);}
            else{
                nextPlayerNumber = 0;
                nextPlayer = board.getPlayer(nextPlayerNumber);
                board.setCurrentPlayer(nextPlayer);
            }
            // TODO Assignment V1: method should be implemented by the students:
            //   - the current player should be moved to the given space
            //     (if it is free()
            //   - and the current player should be set to the player
            //     following the current player
            //   - the counter of moves in the game should be increased by one
            //     if and when the player is moved (the counter and the status line
            //     message needs to be implemented at another place)
        }
    }


    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                Command command = null;

                for (int i = 0; i < 2; i++) {
                    Player checkingPlayer = board.getPlayer(i);
                    if (checkingPlayer.getSpace() instanceof Checkpoint) {
                        ((Checkpoint) checkingPlayer.getSpace()).playPassedCheckpoint(checkingPlayer);
                    }
                    if (checkingPlayer.getCheckpointCount() == board.finaleCheckpoint) {
                        if (checkingPlayer.getCheckpointCount() == 6) {
                            System.out.println(checkingPlayer.getName() + " har vundet, TILLYKKE!");

                            checkingPlayer.setColor("yellow");
                        }
                    }

        // Check if the game has ended
        if (board.getPhase() == Phase.END_OF_GAME) {
            System.out.println("Game has ended.");
            // Perform end game actions here, e.g. print result and prompt for new game
        }
    }


                if (card!=null) {
                    command = card.command;
                    executeCommand(currentPlayer, command);
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                }

                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }
    /*
    Metoden executeCommandOptionAndContinue eksekverer metoden executeCommand som bliver beskrevet nedenunder
    Phase på spillet sættes til Activation Phase og dermed fortsættes det
    Efter at har erklæret hvad currentplayer er, udføres if statement hvis fasen er aktivations fasen og hvis der er en spiller i spillet
     */
    public void executeCommandOptionAndContinue(Command option){
        executeCommand(board.getCurrentPlayer(), option);
        board.setPhase(Phase.ACTIVATION);

        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                Command command = null;
                if (card!=null) {
                    command = card.command;
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }
/*
executeCommand er en metode der bruges til at eksekverer de forskellige Commands som programmeringskortene indeholder
Metoden har 2 parametre som er en player og den command som skal udføres
Der oprettes et switch statement som tager en command og fortæller hvad systemet skal gøre i de forskellige commands
For eksempel hvis det er FORWARD command kaldes metoden moveForward.
 */
    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }


    /*
    Metoden moveForward tager en spiller som parmeter og starter med at sammenligne hvis playerBoard er den samme som board
   Hvis det er tilfældet findes den space som spilleren står på samt den heading eller retning som spilleren har
   Der oprettes et felt som finder det felt der er ved siden af spillers felt og samme retning på spilleren
   Hvis det felt ikke er tom så skal den flytte den nuværende spiller til den givne felt som er target og spillers heading
     */
    public void moveForward(@NotNull Player player) {
        if (player.board == board) {
            Space space = player.getSpace();
            Heading heading = player.getHeading();

            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {
                    // we don't do anything here  for now; we just catch the
                    // exception so that we do no pass it on to the caller
                    // (which would be very bad style).
                }
            }
        }
    }

    void moveToSpace(@NotNull Player player, @NotNull Space space, @NotNull Heading heading) throws ImpossibleMoveException {
        assert board.getNeighbour(player.getSpace(), heading) == space; // make sure the move to here is possible in principle
        Player other = space.getPlayer();
        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                // XXX Note that there might be additional problems with
                //     infinite recursion here (in some special cases)!
                //     We will come back to that!
                moveToSpace(other, target, heading);

                // Note that we do NOT embed the above statement in a try catch block, since
                // the thrown exception is supposed to be passed on to the caller

                assert target.getPlayer() == null : target; // make sure target is free now
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
    }

    class ImpossibleMoveException extends Exception {

        private Player player;
        private Space space;
        private Heading heading;

        public ImpossibleMoveException(Player player, Space space, Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }


    /*
    I metoden fastForward deklareres 2 forskellige Spaces
    Den første space ved navnet currentplayerSpace giver sig selv, det er den nuværende spillers felt
    den anden space ved navnet nextspace er den space som er ved siden af currentplayerSpace og i samme retning som den nuværende spiller
    I den sidste linje bruges metoden moveCurrentPlayerToSpace som tager en space som parameter.
    Den space er det felt der er ved siden af nextSpace og i samme retning som den nuværende spiller
     */
    public void fastForward(@NotNull Player player) {
        Space currentPlayerSpace = board.getCurrentPlayer().getSpace();
        Space nextSpace = board.getNeighbour(currentPlayerSpace, board.getCurrentPlayer().getHeading());
        moveCurrentPlayerToSpace(board.getNeighbour(nextSpace, board.getCurrentPlayer().getHeading()));
    }

    /*
    I metoden turnRight findes til at starte med den nuværende spillers retning og gemmes i currentPlayerHeading
    Derefter laves et switch statement som tager currentPlayerHeading som parameter og tjekker hvis retning på spilleren er nord for eksempel så sætter den retningen til at være øst i stedet
    Efter switch statement sættes retning på spilleren til den nye retning ved brug af setHeading metoden under player klassen
     */
    public void turnRight(@NotNull Player player) {
        Heading currentPlayerHeading = board.getCurrentPlayer().getHeading();
        switch (currentPlayerHeading) {
            case NORTH: currentPlayerHeading =  Heading.EAST;
                break;
            case EAST: currentPlayerHeading = Heading.SOUTH;
                break;
            case SOUTH: currentPlayerHeading = Heading.WEST;
                break;
            case WEST: currentPlayerHeading = Heading.NORTH;
                break;
        }
        player.setHeading(currentPlayerHeading);
    }

    /*
    I turnLeft metoden udføres præcis det samme som turnRight men modsat i stedet
     */
    public void turnLeft(@NotNull Player player) {
        Heading currentPlayerHeading = board.getCurrentPlayer().getHeading();
        switch (currentPlayerHeading) {
            case NORTH: currentPlayerHeading =  Heading.WEST;
                break;
            case EAST: currentPlayerHeading = Heading.NORTH;
                break;
            case SOUTH: currentPlayerHeading = Heading.EAST;
                break;
            case WEST: currentPlayerHeading = Heading.SOUTH;
                break;
        }
        player.setHeading(currentPlayerHeading);
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }




    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */



}
