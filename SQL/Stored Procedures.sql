USE cardclub_db;

/*##################################################
				## Drop Procedures ##
####################################################*/

DROP PROCEDURE IF EXISTS SP_GetLatestPlayerId;
DROP PROCEDURE IF EXISTS SP_GetPlayerID;
DROP PROCEDURE IF EXISTS SP_CheckLogin;
DROP PROCEDURE IF EXISTS SP_GetStats;
DROP PROCEDURE IF EXISTS SP_CreatePlayer;
DROP PROCEDURE IF EXISTS SP_UpdatePlayer;
DROP PROCEDURE IF EXISTS SP_DeletePlayer;
DROP PROCEDURE IF EXISTS SP_AddGamePlayed;
DROP PROCEDURE IF EXISTS SP_AddGameWon;
DROP PROCEDURE IF EXISTS SP_CreateLog;
DROP PROCEDURE IF EXISTS SP_GetLogs;
DROP PROCEDURE IF EXISTS SP_GetLogById;


/*##################################################
				## Create Procedures ##
####################################################*/

DELIMITER //

CREATE PROCEDURE SP_GetLatestPlayerId(
OUT result INT)
BEGIN
	SELECT MAX(Player_Id) INTO result FROM Players;
END//

CREATE PROCEDURE SP_GetPlayerID(
IN username VARCHAR(50), 
OUT result INT)
BEGIN

	SELECT Player_Id 
    INTO result
    FROM Logins 
    WHERE Logins.UserName=username;    
    
END//


CREATE PROCEDURE SP_CheckLogin(
IN usrName VARCHAR(50),
IN pswrd VARCHAR(10000),
OUT returnResult BIT)
BEGIN

	SET returnResult =
		CASE 
			WHEN EXISTS (
				SELECT * FROM Logins
				WHERE 
					UserName = usrName AND
					Password = pswrd) 
			THEN 1
			ELSE 0
		END;
    
END //

CREATE PROCEDURE SP_GetStats(
IN input VARCHAR(50))
BEGIN

	CALL SP_GetPlayerID(input, @ChosenPlayer);
    
	SELECT Games_Played, Games_Won 
    FROM Stats 
    WHERE Stats.Player_Id=@ChosenPlayer; 
    
END//

CREATE PROCEDURE SP_CreatePlayer(
IN newName VARCHAR(50), 
IN newUsrName VARCHAR(50), 
IN newPass VARCHAR(10000),
OUT result INT)
BEGIN

	INSERT INTO Players(Name)
    VALUES(newName);
        
    CALL SP_GetLatestPlayerId(@NewId);
    
    INSERT INTO Logins(Player_Id, UserName, Password)
    VALUES(@NewID, newUsrName, newPass);
    
    INSERT INTO Stats(Player_Id, Games_Played, Games_Won)
    VALUES(@NewId, 0, 0);
    
    SET result = ROW_COUNT();
    
END//

CREATE PROCEDURE SP_UpdatePlayer(
IN oldName VARCHAR(50),
IN newName VARCHAR(50),
IN oldUsrName VARCHAR(50), 
IN newUsrName VARCHAR(50), 
IN newPass VARCHAR(10000),
OUT result INT)
BEGIN

	CALL SP_GetPlayerID(oldUsrName, @ChosenPlayerId);
    
	UPDATE Players 
	SET 
		Name = newName
	WHERE
		Players.Player_Id = @ChosenPlayerId;

	UPDATE Logins 
	SET 
		UserName = newUsrName,
		Password = newPass
	WHERE
		Logins.Player_Id = @ChosenPlayerId;
    
    SET result = ROW_COUNT();
            
END//

CREATE PROCEDURE SP_DeletePlayer(
IN delName VARCHAR(50),
OUT result INT)
BEGIN

	CALL SP_GetPlayerID(delName, @ChosenPlayerId);
    
    DELETE FROM Stats
    WHERE Stats.Player_Id = @ChosenPlayerId;
    
    DELETE FROM Logins
    WHERE Logins.Player_Id = @ChosenPlayerId;
    
    DELETE FROM Players
    WHERE Players.Player_Id = @ChosenPlayerId;
    
    SET result = ROW_COUNT();
    
END//

CREATE PROCEDURE SP_AddGamePlayed(
IN usrName VARCHAR(50),
OUT result INT)
BEGIN

	CALL SP_GetPlayerID(usrName, @ChosdenPlayer);
    
    UPDATE Stats
	SET
		Games_Played = Games_Played + 1
	WHERE Stats.Player_Id = @ChosenPlayer;
    
    SET result = ROW_COUNT();
    
END//

CREATE PROCEDURE SP_AddGameWon(
IN usrName VARCHAR(50),
OUT result INT)
BEGIN

	CALL SP_GetPlayerID(usrName, @ChosenPlayer);
    
	UPDATE Stats 
	SET 
		Games_Won = Games_Won + 1
	WHERE
		Stats.Player_Id = @ChosenPlayer;
    
	SET result = ROW_COUNT();
    
END//

CREATE PROCEDURE SP_CreateLog(
IN action VARCHAR(250),
IN message VARCHAR(500),
OUT result INT)
BEGIN
	
    INSERT INTO Logs(Action, Message, CreatedTime)
    VALUES(action, message, NOW());
    
    SET result = ROW_COUNT();
    
END//

CREATE PROCEDURE SP_GetLogs()
BEGIN
	SELECT * FROM Logs;
END//


CREATE PROCEDURE SP_GetLogById(
IN id INT)
BEGIN
	SELECT * FROM Logs WHERE Logs.Id = id;
END//

DELIMITER ;