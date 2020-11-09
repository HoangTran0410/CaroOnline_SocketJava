-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.5-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for carodb
CREATE DATABASE IF NOT EXISTS `carodb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `carodb`;

-- Dumping structure for table carodb.friend
CREATE TABLE IF NOT EXISTS `friend` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `User1` varchar(50) NOT NULL,
  `User2` varchar(50) NOT NULL,
  `AddedDate` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Dumping data for table carodb.friend: ~4 rows (approximately)
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
REPLACE INTO `friend` (`ID`, `User1`, `User2`, `AddedDate`) VALUES
	(8, 'uwu@yahoo.com', 'meomeo@gmail.com', '2020-10-09'),
	(9, 'hasagi@yahoo.com', 'asayo@skrr.com', '2020-09-09'),
	(10, 'ecec@ecec.com', 'kimochi@jav.con', '2020-10-30'),
	(11, 'poop@shit.com', 'haha@yaha.com', '2020-10-30');
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;

-- Dumping structure for table carodb.gamematch
CREATE TABLE IF NOT EXISTS `gamematch` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Player1` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Player2` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Winner` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PlayTimeInSecond` int(11) NOT NULL,
  `TotalMove` int(11) NOT NULL,
  `StartedTime` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table carodb.gamematch: ~6 rows (approximately)
/*!40000 ALTER TABLE `gamematch` DISABLE KEYS */;
REPLACE INTO `gamematch` (`ID`, `Player1`, `Player2`, `Winner`, `PlayTimeInSecond`, `TotalMove`, `StartedTime`) VALUES
	(47, 'user1', 'bemeovuive', 'user1', 60, 15, '2020-11-09T15:34:58.432'),
	(48, 'bemeovuive', 'BeGauEcEc', 'bemeovuive', 160, 50, '2020-11-09T15:34:58.432'),
	(49, 'BeGauEcEc', 'BeVitMooMoo', 'BeVitMooMoo', 260, 75, '2020-11-09T15:34:58.432'),
	(50, 'ToiletSangChoi', 'user1', 'user1', 630, 35, '2020-11-09T15:34:58.432'),
	(51, 'GaConXamTro', 'ToiletSangChoi', 'GaConXamTro', 601, 45, '2020-11-09T15:34:58.432'),
	(52, 'BeThoQuacQuac', 'BeChoGauGau', 'BeThoQuacQuac', 460, 25, '2020-11-09T15:34:58.432');
/*!40000 ALTER TABLE `gamematch` ENABLE KEYS */;

-- Dumping structure for table carodb.player
CREATE TABLE IF NOT EXISTS `player` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RankID` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DateOfBirth` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Score` int(11) DEFAULT NULL,
  `MatchCount` int(11) DEFAULT NULL,
  `WinRate` float DEFAULT NULL,
  `WinStreak` int(11) DEFAULT NULL,
  `Blocked` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNIQUE` (`Email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table carodb.player: ~8 rows (approximately)
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
REPLACE INTO `player` (`ID`, `Username`, `Password`, `Email`, `Gender`, `RankID`, `DateOfBirth`, `Score`, `MatchCount`, `WinRate`, `WinStreak`, `Blocked`) VALUES
	(48, 'user1', '123abc', 'uwu@yahoo.com', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(49, 'bemeovuive', '123456', 'meomeo@gmail.com', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(50, 'BeChoGauGau', 'abcxyz', 'hasagi@yahoo.com', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(51, 'BeThoQuacQuac', 'carotxanh', 'asayo@skrr.com', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(52, 'BeGauEcEc', 'gaudien', 'ecec@ecec.com', 'Nữ', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(53, 'BeVitMooMoo', 'vittiem', 'haha@yaha.com', 'Nữ', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(54, 'ToiletSangChoi', 'Vimconvit', 'poof@shit.com', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0'),
	(55, 'GaConXamTro', 'talavit', 'kimochi@jav.con', 'Nam', 'none', '2020-11-09', 0, 0, 0, 0, '0');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
