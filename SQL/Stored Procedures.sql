USE cardclub_db;

/*##################################################
				## Drop Procedures ##
####################################################*/

DROP PROCEDURE IF EXISTS SP_GetLatestPlayerId;
DROP PROCEDURE IF EXISTS SP_GetPlayerID;
DROP PROCEDURE IF EXISTS SP_GetUsername;
DROP PROCEDURE IF EXISTS SP_GetPassword;
DROP PROCEDURE IF EXISTS SP_GetStats;
DROP PROCEDURE IF EXISTS SP_CreatePlayer;
DROP PROCEDURE IF EXISTS SP_UpdatePlayer;
DROP PROCEDURE IF EXISTS SP_DeletePlayer;
DROP PROCEDURE IF EXISTS SP_AddGamePlayed;
DROP PROCEDURE IF EXISTS SP_AddGameWon;


/*##################################################
				## Drop Procedures ##
####################################################*/

DELIMITER //

CREATE PROCEDURE SP_GetLatestPlayerId()
BEGIN
	 SELECT MAX(Player_Id) FROM Players;
END//

CREATE PROCEDURE SP_GetPlayerID(IN username VARCHAR(50))
BEGIN

	SELECT Player_Id 
    FROM Logins 
    WHERE Logins.UserName=username;    
    
END//

CREATE PROCEDURE SP_GetUsername(IN input VARCHAR(50))
BEGIN

	SELECT IF(UserName IS NULL,0,1) 
    FROM Logins
    WHERE Logins.UserName=input;
    
END //

CREATE PROCEDURE SP_GetPassword(IN input VARCHAR(1000))
BEGIN

	SELECT IF(Password IS NULL,0,1) 
    FROM Logins 
    WHERE Logins.Password=input;
    
END //

CREATE PROCEDURE SP_GetStats(IN input VARCHAR(50))
BEGIN

	SET @ChosenPlayer = SP_GetPlayerID(input);
    
	SELECT Games_Played, Games_Won 
    FROM Stats 
    WHERE Stats.Player_Id=@ChosenPlayer; 
    
END//

CREATE PROCEDURE SP_CreatePlayer(IN newName VARCHAR(50), IN newUsrName VARCHAR(50), IN newPass VARCHAR(1000))
BEGIN

	INSERT INTO Players
    VALUES(Players.Name=newName);
    
    SET @NewID =  SP_GetLatestPlayerId();
    
    INSERT INTO Logins
    VALUES(Player_Id=@NewID, UserName=newUsrName, Password=newPass);
    
    
END//

CREATE PROCEDURE SP_UpdatePlayer(IN oldName VARCHAR(50), IN newName VARCHAR(50), IN newPass VARCHAR(1000))
BEGIN

	SET @ChosenPlayerId = SP_GetPlayerID(oldName);
    
    UPDATE Logins
	SET 
		UserName = newName,
		Password = newPass
	WHERE Logins.Player_Id = @ChosenPlayerId;
            
END//

CREATE PROCEDURE SP_DeletePlayer(IN delName VARCHAR(50))
BEGIN

	SET @ChosenPlayerId = SP_GetPlayerID(delName);
    
    DELETE FROM Players
    WHERE Players.Player_Id = @ChosenPlayerId;
    
END//

CREATE PROCEDURE SP_AddGamePlayed(IN usrName VARCHAR(50))
BEGIN

	SET @ChosenPlayer = SP_GetPlayerID(usrName);
    
    UPDATE Stats
	SET
		Games_Played = Games_Played + 1
	WHERE Stats.Player_Id = @ChosenPlayer;
    
END//

CREATE PROCEDURE SP_AddGameWon(IN usrName VARCHAR(50))
BEGIN

	SET @ChosenPlayer = SP_GetPlayerID(usrName);
    
    UPDATE Stats
	SET
		Games_Won = Games_Won + 1
	WHERE Stats.Player_Id = @ChosenPlayer;
    
END//


DELIMITER ;