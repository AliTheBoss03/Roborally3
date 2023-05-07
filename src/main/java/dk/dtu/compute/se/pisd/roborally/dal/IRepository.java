package dk.dtu.compute.se.pisd.roborally.dal;

import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.util.List;
/**
 * @author Ali Masoud
 */

public interface IRepository {

    boolean createGameInDB(Board game);

    boolean updateGameInDB(Board game);

    Board loadGameFromDB(int id);


    List<GameInDB> getGames();

}
