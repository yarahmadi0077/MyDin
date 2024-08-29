testtestACCOUNT-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table projectdb.connectioninfos
CREATE TABLE IF NOT EXISTS `connectioninfos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userLink` varchar(40) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `phoneNumber` varchar(40) DEFAULT NULL,
  `phoneType` varchar(40) DEFAULT NULL,
  `address` varchar(220) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `birthdayAccess` varchar(40) DEFAULT NULL,
  `otherWay` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projectdb.connectioninfos: ~2 rows (approximately)
INSERT INTO `connectioninfos` (`id`, `userLink`, `email`, `phoneNumber`, `phoneType`, `address`, `birthday`, `birthdayAccess`, `otherWay`) VALUES
	(1, 'alinkdia.com/abbasss', 'batman@gmail.com', '09303026140', 'phone', 'mashad moghaddas , ye jash', '2006-07-03', 'all user', 'Telegram : @A4f_ss');

-- Dumping structure for table projectdb.educations
CREATE TABLE IF NOT EXISTS `educations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolName` varchar(40) DEFAULT NULL,
  `fieldOfStudy` varchar(40) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `grade` double DEFAULT NULL,
  `activitiesAndSocieties` varchar(500) DEFAULT NULL,
  `profileChanges` varchar(40) DEFAULT NULL,
  `descriptions` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projectdb.educations: ~0 rows (approximately)
INSERT INTO `educations` (`id`, `schoolName`, `fieldOfStudy`, `startDate`, `endDate`, `grade`, `activitiesAndSocieties`, `profileChanges`, `descriptions`) VALUES
	(1, 'Amirkabir University of Technology', 'Computer Engineering', '2023-10-01', '2027-10-01', 18.03, 'nothing', NULL, 'poly thechnic tehran');

-- Dumping structure for table projectdb.jobs
CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `employmentType` varchar(50) DEFAULT NULL,
  `companyName` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `locationType` varchar(50) DEFAULT NULL,
  `activity` int(11) DEFAULT NULL,
  `startToWork` date DEFAULT NULL,
  `endToWork` date DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projectdb.jobs: ~3 rows (approximately)
INSERT INTO `jobs` (`id`, `title`, `employmentType`, `companyName`, `location`, `locationType`, `activity`, `startToWork`, `endToWork`, `description`) VALUES
	(1, 'Assistant Professor', 'Full-time', 'Amirkabir University of Technology', 'Tehran , Iran', 'On-site', 1, '2023-10-01', '2024-06-02', 'Natural Language Processing'),
	(2, 'Social media ', 'Full-time', 'SnappTrip', 'Tehran  Iran', 'On-site', 1, '2022-10-02', '2025-09-10', 'Develop social media strategy Social media Content calendar planning Create engaging content (social channels - SMS - landings)'),
	(8, 'delivery kh', 'Full-time', 'SnappFood', 'Tehran  Iran', 'On-site', 1, '2022-10-02', '2025-09-10', 'peyk motory');

-- Dumping structure for table projectdb.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(40) NOT NULL,
  `additionalName` varchar(40) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  `title` varchar(220) DEFAULT NULL,
  `imagePathProfile` varchar(220) DEFAULT NULL,
  `imagePathBackground` varchar(220) DEFAULT NULL,
  `jobId` int(11) DEFAULT NULL,
  `educationId` int(11) DEFAULT NULL,
  `connectionInfoId` int(11) DEFAULT NULL,
  `country` varchar(220) DEFAULT NULL,
  `city` varchar(220) DEFAULT NULL,
  `profession` varchar(220) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`email`) USING BTREE,
  KEY `FK_users_job` (`jobId`) USING BTREE,
  KEY `FK_users_educations` (`educationId`),
  KEY `FK_users_connectioninfos` (`connectionInfoId`),
  CONSTRAINT `FK_users_connectioninfos` FOREIGN KEY (`connectionInfoId`) REFERENCES `connectioninfos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_users_educations` FOREIGN KEY (`educationId`) REFERENCES `educations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_users_job` FOREIGN KEY (`jobId`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table projectdb.users: ~9 rows (approximately)
INSERT INTO `users` (`id`, `firstName`, `lastName`, `additionalName`, `email`, `title`, `imagePathProfile`, `imagePathBackground`, `jobId`, `educationId`, `connectionInfoId`, `country`, `city`, `profession`, `password`) VALUES
	(1, 'Pouria', 'fahimi', 'momo', 'porito@aut.ac.ir', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ''),
	(2, 'Amirhossein', 'Aghighi', 'Duck', 'fedeveloper@aut.ac.ir', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'po13ce58d'),
	(3, 'mammad', 'yara', 'khadem', 'yarr@aut.ac.ir', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ''),
	(27, 'hessam', 'hosii', 'zizo', 'hessi@aut.ac.ir', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'password'),
	(29, 'parsa', 'exir', 'topolo', 'parsa@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'paxir1247'),
	(30, 'komeil', 'mirivahid', 'jizzzzzzzz', 'koko@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1385komeil12'),
	(32, 'alireza', 'saadi', 'nazii', 'hitlerza@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sa84hegfjbd'),
	(33, 'sadra', 'shadmehri', 'Djxoooo', 'djaut@gmail.com', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 'sh/F3mJ5bSsvKZmchbHErg=='),
	(34, 'abbas', 'entezari', 'batmann', 'batman@gmail.com', NULL, NULL, NULL, 8, 1, 1, NULL, NULL, NULL, 'o/VLSlAuQZ/NOAhkoix9aA==');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
