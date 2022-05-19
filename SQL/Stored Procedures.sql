USE cardclub_db;

/*##################################################
				## Drop Procedures ##
####################################################*/

DROP PROCEDURE IF EXISTS SP_GetUsername;
DROP PROCEDURE IF EXISTS SP_GetPassword;
DROP PROCEDURE IF EXISTS SP_GetStats;
DROP PROCEDURE IF EXISTS SP_CreatePlayers;
DROP PROCEDURE IF EXISTS SP_UpdatePlayer;
DROP PROCEDURE IF EXISTS SP_DeletePlayer;


/*##################################################
				## Drop Procedures ##
####################################################*/

DELIMITER //

CREATE PROCEDURE SP_GetPlayerID(IN username varchar(50))
BEGIN

	SELECT Player_Id 
    FROM Logins 
    WHERE Logins.UserName=username;    
    
END//

CREATE PROCEDURE SP_GetUsername(IN input varchar(50))
BEGIN

	SELECT IF(UserName IS NULL,0,1) 
    FROM Logins
    WHERE Logins.UserName=input;
    
END //

CREATE PROCEDURE SP_GetPassword(IN input Varchar(1000))
BEGIN

	SELECT IF(Password IS NULL,0,1) 
    FROM Logins 
    WHERE Logins.Password=input;
    
END //

CREATE PROCEDURE SP_GetStats(IN input varchar(50))
BEGIN

	SET @ChosenPlayer = SP_GetPlayerID(input);
    
	SELECT Games_Played, Games_Won 
    FROM Stats 
    WHERE Stats.Player_Id=@ChosenPlayer; 
    
END//

CREATE PROCEDURE SP_CreatePlayer(IN newName varchar(50))
BEGIN

	INSERT INTO Players
    VALUES(Players.Name=newName);
    
END//

CREATE PROCEDURE SP_UpdatePlayer()
BEGIN

END//

CREATE PROCEDURE SP_DeletePlayer(IN delName varchar(50))
BEGIN

	SET @ChosenPlayerId = SP_GetPlayerID(delName);
    
    DELETE FROM Players
    WHERE Players.Player_Id = @ChosenPlayerId;
    
END//

DELIMITER ;