-- =============================================
-- Author:      Jesper Deleurand
-- Create date: 17/05/2022
-- =============================================

DROP DATABASE IF EXISTS cardclub_db;

CREATE DATABASE cardclub_db;

USE cardclub_db;


/*##################################################
				## Drop Tables ##
####################################################*/

DROP TABLE IF EXISTS PLayers;
DROP TABLE IF EXISTS Logins;
DROP TABLE IF EXISTS Stats;
DROP TABLE IF EXISTS Logs;


/*##################################################
				## Create Tables ##
####################################################*/

DELIMITER //

CREATE TABLE Players (
    Player_Id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Player_Id)
);

CREATE TABLE Logins (
    Player_Id INT NOT NULL,
    UserName VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(10000) NOT NULL,
    PRIMARY KEY (Player_Id)
);

CREATE TABLE Stats (
    Player_Id INT NOT NULL,
    Games_Played INT,
    Games_Won INT,
    PRIMARY KEY (Player_Id)
);

CREATE TABLE Logs (
    Id INT NOT NULL AUTO_INCREMENT,
    Action VARCHAR(250) NOT NULL,
    Message VARCHAR(500) NOT NULL,
    CreatedTime DATETIME NOT NULL,
    PRIMARY KEY (Id)
);


/*##################################################
				## Alter Data Tables ##
####################################################*/

ALTER TABLE Logins
     ADD CONSTRAINT fk_P2L
     FOREIGN KEY (Player_Id)
     REFERENCES Players(Player_Id)
     ON DELETE CASCADE;

ALTER TABLE Stats
     ADD CONSTRAINT fk_P2S
     FOREIGN KEY (Player_Id)
     REFERENCES Players(Player_Id)
     ON DELETE CASCADE;
