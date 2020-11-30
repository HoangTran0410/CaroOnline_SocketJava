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

-- Dumping structure for table carodb.gamematch
CREATE TABLE IF NOT EXISTS `gamematch` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PlayerID1` int(11) NOT NULL,
  `PlayerID2` int(11) NOT NULL,
  `WinnerID` int(11) DEFAULT NULL,
  `PlayTime` int(11) NOT NULL,
  `TotalMove` int(11) NOT NULL,
  `StartedTime` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Chat` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table carodb.gamematch: ~6 rows (approximately)
/*!40000 ALTER TABLE `gamematch` DISABLE KEYS */;
REPLACE INTO `gamematch` (`ID`, `PlayerID1`, `PlayerID2`, `WinnerID`, `PlayTime`, `TotalMove`, `StartedTime`, `Chat`) VALUES
	(1, 1, 2, 1, 10, 15, '2020-11-23T17:22:06.081', NULL),
	(2, 2, 3, 2, 20, 25, '2020-11-23T17:22:06.081', NULL),
	(3, 3, 4, 4, 30, 35, '2020-11-23T17:22:06.081', NULL),
	(4, 1, 4, 4, 40, 45, '2020-11-23T17:22:06.081', NULL),
	(5, 3, 2, 3, 50, 55, '2020-11-23T17:22:06.081', NULL),
	(6, 4, 5, 5, 90, 50, '2020-11-23T17:22:06.081', NULL);
/*!40000 ALTER TABLE `gamematch` ENABLE KEYS */;

-- Dumping structure for table carodb.player
CREATE TABLE IF NOT EXISTS `player` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Avatar` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Nam',
  `YearOfBirth` int(4) NOT NULL DEFAULT 2000,
  `Score` int(11) NOT NULL DEFAULT 0,
  `MatchCount` int(11) NOT NULL DEFAULT 0,
  `WinCount` int(11) NOT NULL DEFAULT 0,
  `LoseCount` int(11) NOT NULL DEFAULT 0,
  `CurrentStreak` int(11) NOT NULL DEFAULT 0,
  `Rank` int(11) NOT NULL DEFAULT -1,
  `Blocked` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNIQUE` (`Email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table carodb.player: ~10 rows (approximately)
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
REPLACE INTO `player` (`ID`, `Email`, `Password`, `Avatar`, `Name`, `Gender`, `YearOfBirth`, `Score`, `MatchCount`, `WinCount`, `LoseCount`, `CurrentStreak`, `Rank`, `Blocked`) VALUES
	(1, '99.hoangtran@gmail.com', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_alien_96px.png', 'hoang tran', 'nam', 1999, 0, 2, 1, 1, 0, 0, 0),
	(2, 'hientran@gmail.com', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'thu hien', 'nữ', 1988, 0, 3, 1, 2, 0, 0, 0),
	(3, 'nguyenthienhuu@gmail.com', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'huu', 'nữ', 1999, 0, 2, 2, 0, 0, 0, 0),
	(4, 'dinhkhoa@gmail.com', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_angry_face_meme_96px.png', 'khoa', 'nữ', 1987, 0, 3, 0, 3, 0, 0, 1),
	(5, 'admin@manager.com', 'df10ef8509dc176d733d59549e7dbfaf', 'icons8_circled_user_male_skin_type_7_96px.png', 'admin', 'Nam', 1999, 0, 1, 1, 0, 0, 0, 0),
	(6, 'anh@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'anhanh', 'Nam', 1987, 0, 0, 0, 0, 0, -1, 0),
	(7, 'anhhai@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'heo', 'Nam', 1987, 0, 0, 0, 0, 0, -1, 0),
	(8, 'ecec@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'heo', 'Nam', 1997, 0, 0, 0, 0, 0, -1, 0),
	(9, 'superman@yahoo.com', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'man', 'Nam', 2000, 0, 0, 0, 0, 0, -1, 0),
	(10, 'yes@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 'icons8_circled_user_male_skin_type_7_96px.png', 'nonono', 'Nam', 1987, 0, 0, 0, 0, 0, -1, 0);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
