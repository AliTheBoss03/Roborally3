/* Need to switch off FK check for MySQL since there are crosswise FK references */
SET FOREIGN_KEY_CHECKS = 0;;

CREATE TABLE IF NOT EXISTS Game (
  gameID int NOT NULL UNIQUE AUTO_INCREMENT,
  
  name varchar(255),

  phase tinyint,
  step tinyint,
  currentPlayer tinyint NULL,
  
  PRIMARY KEY (gameID),
  FOREIGN KEY (gameID, currentPlayer) REFERENCES Player(gameID, playerID)
);;
  
CREATE TABLE IF NOT EXISTS Player (
  gameID int NOT NULL,
  playerID tinyint NOT NULL,

  name varchar(255),
  colour varchar(31),
  
  positionX int,
  positionY int,
  heading tinyint,
  
  PRIMARY KEY (gameID, playerID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);;

SET FOREIGN_KEY_CHECKS = 1;;

CREATE TABLE IF NOT EXISTS CardField (
    gameID int NOT NULL,
    playerID tinyint NOT NULL,
    type tinyint NOT NULL,
    position tinyint NOT NULL,
    visible BIT NOT NULL,
    command tinyint,

    PRIMARY KEY (gameID, playerID, type, position),
    FOREIGN KEY (gameID) REFERENCES Game(gameID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (gameID, playerID) REFERENCES Player(gameID, playerID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
    )