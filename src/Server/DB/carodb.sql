-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 27, 2020 lúc 06:09 PM
-- Phiên bản máy phục vụ: 10.4.16-MariaDB
-- Phiên bản PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `carodb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `gamematch`
--

CREATE TABLE `gamematch` (
  `ID` int(11) NOT NULL,
  `PlayerID1` int(11) NOT NULL,
  `PlayerID2` int(11) NOT NULL,
  `WinnerID` int(11) DEFAULT NULL,
  `PlayTime` int(11) NOT NULL,
  `TotalMove` int(11) NOT NULL,
  `StartedTime` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Chat` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `gamematch`
--

INSERT INTO `gamematch` (`ID`, `PlayerID1`, `PlayerID2`, `WinnerID`, `PlayTime`, `TotalMove`, `StartedTime`, `Chat`) VALUES
(1, 1, 2, 1, 600, 50, '27/11/2020 23:56:01', 'chat here');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `player`
--

CREATE TABLE `player` (
  `ID` int(11) NOT NULL,
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
  `Blocked` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `player`
--

INSERT INTO `player` (`ID`, `Email`, `Password`, `Avatar`, `Name`, `Gender`, `YearOfBirth`, `Score`, `MatchCount`, `WinCount`, `LoseCount`, `CurrentStreak`, `Blocked`) VALUES
(1, '99.hoangtran@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_saitama_96px.png', 'hoang tran', 'Ẩn', 1999, 3, 1, 1, 0, 1, 0),
(2, 'hientran@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_angry_face_meme_96px.png', 'thu hien', 'Nữ', 1999, 0, 0, 0, 0, 0, 1),
(5, 'test@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_circled_user_female_skin_type_7_96px.png', 'test', 'Nam', 1999, 0, 0, 0, 0, 0, 0),
(6, 'test2@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_angry_face_meme_96px.png', 'test2', 'Nữ', 2006, 0, 0, 0, 0, 0, 0),
(7, 'mail@mail.com', '202cb962ac59075b964b07152d234b70', 'icons8_circled_user_female_skin_type_7_96px.png', 'mail', 'Ẩn', 2020, 0, 0, 0, 0, 0, 0),
(8, 'mail2@mail.com', '202cb962ac59075b964b07152d234b70', 'icons8_angry_face_meme_96px.png', 'mail2', 'Nữ', 1999, 0, 0, 0, 0, 0, 0),
(9, 'm@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_pikachu_pokemon_96px.png', 'm', 'Ẩn', 2015, 0, 0, 0, 0, 0, 0),
(10, 'm2@gmail.com', '202cb962ac59075b964b07152d234b70', 'icons8_angry_face_meme_96px.png', 'm2', 'Ẩn', 2016, 0, 0, 0, 0, 0, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `gamematch`
--
ALTER TABLE `gamematch`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `PlayerID` (`PlayerID1`,`PlayerID2`,`WinnerID`) USING BTREE,
  ADD KEY `PlayerID2` (`PlayerID2`),
  ADD KEY `WinnerID` (`WinnerID`);

--
-- Chỉ mục cho bảng `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `UNIQUE` (`Email`) USING BTREE;

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `gamematch`
--
ALTER TABLE `gamematch`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `player`
--
ALTER TABLE `player`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `gamematch`
--
ALTER TABLE `gamematch`
  ADD CONSTRAINT `gamematch_ibfk_1` FOREIGN KEY (`PlayerID1`) REFERENCES `player` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gamematch_ibfk_2` FOREIGN KEY (`PlayerID2`) REFERENCES `player` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gamematch_ibfk_3` FOREIGN KEY (`WinnerID`) REFERENCES `player` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
