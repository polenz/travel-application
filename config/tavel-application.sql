-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 26. Sep 2013 um 20:15
-- Server Version: 5.5.32
-- PHP-Version: 5.3.10-1ubuntu3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `process-engine`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Absence`
--

CREATE TABLE IF NOT EXISTS `Absence` (
  `number` bigint(20) NOT NULL,
  `employee` varchar(255) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `until` datetime DEFAULT NULL,
  `project_number` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK1C341A1DA5B26982` (`project_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Absence`
--

INSERT INTO `Absence` (`number`, `employee`, `start`, `until`, `project_number`) VALUES
(1, 'Petra Schneider', '2013-10-01 00:00:00', '2013-10-01 00:00:00', 4711),
(2, 'Anja Maurer', '2013-10-01 00:00:00', '2013-10-01 00:00:00', 4711),
(3, 'Anja Maurer', '2013-10-09 00:00:00', '2013-10-10 00:00:00', 4712),
(4, 'Bertram Heintz', '2013-10-07 08:00:00', '2013-10-11 18:00:00', 4711),
(5, 'Max Mustermann', '2013-09-24 16:56:48', '2013-09-27 16:56:48', 4711),
(6, 'Max Mustermann', '2013-09-24 16:57:48', '2013-09-27 16:57:48', 4711),
(7, 'Christian Klein', '2013-10-02 13:00:00', '2013-10-02 16:00:00', 4711),
(8, 'Max Mustermann', '2013-09-26 07:23:01', '2013-09-29 07:23:01', 4711),
(9, 'Max Mustermann', '2013-09-26 07:24:46', '2013-09-29 07:24:46', 4711),
(10, 'Norman Schmitt', '2013-10-03 00:00:00', '2013-10-04 00:00:00', 4711),
(11, 'Norman Schmitt', '2013-09-29 00:00:00', '2013-09-30 00:00:00', 4711);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Project`
--

CREATE TABLE IF NOT EXISTS `Project` (
  `number` bigint(20) NOT NULL,
  `projectLeader` varchar(255) DEFAULT NULL,
  `projectName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Project`
--

INSERT INTO `Project` (`number`, `projectLeader`, `projectName`) VALUES
(4711, 'Christian Klein', 'A-Projekt'),
(4712, 'Anne Maier', 'B-Projekt'),
(4713, 'Andrea Groß', 'C-Projekt');

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `Absence`
--
ALTER TABLE `Absence`
  ADD CONSTRAINT `FK1C341A1DA5B26982` FOREIGN KEY (`project_number`) REFERENCES `Project` (`number`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
