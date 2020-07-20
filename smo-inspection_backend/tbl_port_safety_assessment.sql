-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.19 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE `swire_safetyapp` /*!40100 COLLATE 'utf8mb4_unicode_ci' */;

use `swire_safetyapp`;

-- Dumping structure for table swire_linerops.tbl_port_safety_assessment_master
CREATE TABLE IF NOT EXISTS `tbl_port_safety_assessment_master` (
  `psam_id` int NOT NULL AUTO_INCREMENT,
  `question_id` int NOT NULL,
  `question` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` int DEFAULT NULL,
  `rpt_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_parent` int DEFAULT NULL,
  `parent_reply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reply_options` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `icon_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`psam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table swire_linerops.tbl_port_safety_assessment_master: ~22 rows (approximately)
/*!40000 ALTER TABLE `tbl_port_safety_assessment_master` DISABLE KEYS */;
INSERT INTO `tbl_port_safety_assessment_master` (`psam_id`, `question_id`, `question`, `parent_id`, `rpt_type`, `is_parent`, `parent_reply`, `reply_options`, `icon_url`, `created_by`, `created_date`) VALUES
	(1, 1, 'Have stevedores and lashing gangs been "Behaving safely. Always. Naturally." by wearing the full set of PPE (hard hat; high-viz vest and boots)', NULL, 'port_safety', 1, NULL, NULL, NULL, NULL, '2020-03-02 03:31:31'),
	(2, 2, 'Have stevedores and lashing gangs been "Behaving safely. Always. Naturally." by agreeing on ship-shore and operations safety before start of operations, including:', NULL, 'pre_safety', 1, NULL, NULL, NULL, NULL, '2020-03-02 03:31:31'),
	(3, 21, 'Designated smoking areas', 2, 'pre_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(4, 22, 'Cargo work plan', 2, 'pre_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(5, 23, 'Lashing plan', 2, 'pre_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(6, 24, 'DG/ Reefer/ Special cargo handling plan', 2, 'pre_safety', 1, '', 'YES#NO#N/A', NULL, NULL, '2020-03-02 03:31:31'),
	(7, 3, 'Have stevedores and lashing gangs been "Behaving safely. Always. Naturally." by following the safe work practices and ensuring control measures are observed for the following tasks:', NULL, 'port_safety', 1, NULL, NULL, NULL, NULL, '2020-03-02 03:31:31'),
	(8, 31, 'Accessing / working on high-tier cargo', 3, 'port_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(9, 32, 'Working with suspended loads on board and on the wharf', 3, 'port_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(10, 33, 'Working around open hatches', 3, 'port_safety', 1, NULL, 'YES#NO#N/A', NULL, NULL, '2020-03-02 03:31:31'),
	(11, 4, 'Have stevedores and lashing gangs been "Behaving safely. Always. Naturally." by smoking only in designated areas', NULL, 'port_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(12, 5, 'Have other port service providers been "Behaving safely. Always. Naturally."?', NULL, 'port_safety', 1, NULL, NULL, NULL, NULL, '2020-03-02 03:31:31'),
	(13, 51, 'Agent', 5, 'port_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(14, 52, 'Cargo surveyor', 5, 'port_safety', 0, NULL, 'YES#NO#N/A', NULL, NULL, '2020-03-02 03:31:31'),
	(15, 53, 'Pilot', 5, 'port_safety', 0, NULL, 'YES#NO#N/A', NULL, NULL, '2020-03-02 03:31:31'),
	(16, 54, 'Tug', 5, 'port_safety', 0, NULL, 'YES#NO#N/A', NULL, NULL, '2020-03-02 03:31:31'),
	(17, 55, 'Mooring gang or linesman', 5, 'port_safety', 0, NULL, 'YES#NO', NULL, NULL, '2020-03-02 03:31:31'),
	(18, 100, '', 24, 'pre_safety', 0, 'NO', 'No information for DG#No information for reefer#No information for Special cargo handling plan#No DG, Reefer or Special cargo', NULL, NULL, '2020-03-02 03:31:31'),
	(19, 101, 'Hard hat', 1, 'port_safety', 0, '', 'YES#NO', 'https://d3vmkka77acdcr.cloudfront.net/assets/linerops/Hard_hat.png', NULL, '2020-03-02 03:31:31'),
	(20, 102, 'High-viz vest', 1, 'port_safety', 0, '', 'YES#NO', 'https://d3vmkka77acdcr.cloudfront.net/assets/linerops/High-viz_vest.png', NULL, '2020-03-02 03:31:31'),
	(21, 103, 'Safety boot', 1, 'port_safety', 0, '', 'YES#NO', 'https://d3vmkka77acdcr.cloudfront.net/assets/linerops/Safety_boot.png', NULL, '2020-03-02 03:31:31'),
	(22, 104, '          ', 33, 'port_safety', 0, 'N/A', 'No open hatches#No cargo work around open hatches', NULL, NULL, '2020-03-02 03:31:31');
/*!40000 ALTER TABLE `tbl_port_safety_assessment_master` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
