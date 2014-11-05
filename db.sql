-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 04, 2014 at 07:52 AM
-- Server version: 5.5.38-0ubuntu0.14.04.1
-- PHP Version: 5.5.15RC1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `testing`
--

-- --------------------------------------------------------

--
-- Table structure for table `Acceleration`
--

CREATE TABLE IF NOT EXISTS `Acceleration` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `acceleration` float(8,2) NOT NULL,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `zone_id` int(10) unsigned NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `acceleration_job_id_foreign` (`job_id`),
  KEY `acceleration_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Heat`
--

CREATE TABLE IF NOT EXISTS `Heat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `heat_temperature` float(8,2) NOT NULL,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `zone_id` int(10) unsigned NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `heat_job_id_foreign` (`job_id`),
  KEY `heat_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Job`
--

CREATE TABLE IF NOT EXISTS `Job` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `threshold` float(8,2) DEFAULT NULL,
  `object_id` int(10) unsigned NOT NULL,
  `sensor_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `job_object_id_foreign` (`object_id`),
  KEY `job_sensor_id_foreign` (`sensor_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Job`
--

INSERT INTO `Job` (`id`, `title`, `description`, `threshold`, `object_id`, `sensor_id`, `created_at`, `updated_at`) VALUES
(1, 'Door open', '', NULL, 1, 3, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 'Kettle boiled', '', 40.00, 2, 1, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 'Fridge Light On', '', 10.00, 3, 2, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 'User Passed Pillar', '', NULL, 4, 5, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(5, 'User Passed Pillar', '', NULL, 5, 5, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(6, 'Roaming User', '', NULL, 6, 6, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `Light`
--

CREATE TABLE IF NOT EXISTS `Light` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `light_intensity` int(11) NOT NULL,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `zone_id` int(10) unsigned NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `light_job_id_foreign` (`job_id`),
  KEY `light_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Motion`
--

CREATE TABLE IF NOT EXISTS `Motion` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `motion` int(11) NOT NULL,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `zone_id` int(10) unsigned NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `motion_job_id_foreign` (`job_id`),
  KEY `motion_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Object`
--

CREATE TABLE IF NOT EXISTS `Object` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `spot_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `object_spot_id_foreign` (`spot_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Object`
--

INSERT INTO `Object` (`id`, `title`, `description`, `spot_id`, `created_at`, `updated_at`) VALUES
(1, 'Fridge Door', '', 5, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 'Kettle', '', 6, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 'Fridge Light', '', 4, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 'North Pillar', '', 2, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(5, 'South Pillar', '', 1, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(6, 'Roaming User', '', 3, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `Sensor`
--

CREATE TABLE IF NOT EXISTS `Sensor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `table` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `field` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `port_number` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Sensor`
--

INSERT INTO `Sensor` (`id`, `title`, `table`, `field`, `description`, `port_number`, `created_at`, `updated_at`) VALUES
(1, 'Thermometer', 'Heat', 'heat_temperature', '', 110, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 'Photosensor', 'Light', 'light_intensity', '', 120, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 'Accelerometer', 'Acceleration', 'acceleration', '', 130, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 'Motion Sensor', 'Motion', 'motion', '', 140, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(5, 'Cell Tower', 'ZoneSpot', 'zone_id', '', 150, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(6, 'Roaming Spot', NULL, NULL, '', 160, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `Spot`
--

CREATE TABLE IF NOT EXISTS `Spot` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `spot_spot_address_unique` (`spot_address`),
  KEY `spot_user_id_foreign` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Spot`
--

INSERT INTO `Spot` (`id`, `spot_address`, `user_id`, `created_at`, `updated_at`) VALUES
(1, '0014.4F01.0000.7827', 1, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, '0014.4F01.0000.76FF', 1, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, '0014.4F01.0000.77A7', 2, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, '0014.4F01.0000.77C0', 2, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(5, '0014.4F01.0000.7A12', 3, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(6, '0014.4F01.0000.7AD7', 3, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `first_name`, `last_name`, `email`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Adam', 'Cornforth', 'adam@sunspot.app', '$2y$10$f9Y6NxAvyqWWbQSCw/yazedLh9bX6x8vkiKKE6xZzOEgkIc.okkY6', NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 'Dominic', 'Lindsay', 'dominic@sunspot.app', '$2y$10$PGe2QeJUP1jihYBDNVq0y.b2Bl..FFea2BLf4NhZGOPzJTgTZwQaC', NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 'Vitali', 'Bokov', 'vitali@sunspot.app', '$2y$10$/CBRJK.3/cMPR.diryjT6.RL1TPQx9BKJNQvv76xks8N2gtxHOJNi', NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `Zone`
--

CREATE TABLE IF NOT EXISTS `Zone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Dumping data for table `Zone`
--

INSERT INTO `Zone` (`id`, `title`, `created_at`, `updated_at`) VALUES
(1, 'North End of Lab', '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 'Presentation and Touch Table Area', '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 'South End of Lab', '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 'Lab', '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `zone_object`
--

CREATE TABLE IF NOT EXISTS `zone_object` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zone_id` int(10) unsigned NOT NULL,
  `object_id` int(10) unsigned NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `zone_object_object_id_foreign` (`object_id`),
  KEY `zone_object_zone_id_foreign` (`zone_id`),
  KEY `zone_object_job_id_foreign` (`job_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Dumping data for table `zone_object`
--

INSERT INTO `zone_object` (`id`, `zone_id`, `object_id`, `job_id`, `created_at`, `updated_at`) VALUES
(1, 1, 4, 4, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 2, 4, 4, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 2, 5, 5, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 3, 5, 5, '2014-10-31 19:28:12', '2014-10-31 19:28:12');

-- --------------------------------------------------------

--
-- Table structure for table `zone_spot`
--

CREATE TABLE IF NOT EXISTS `zone_spot` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zone_id` int(10) unsigned NOT NULL,
  `spot_id` int(10) unsigned NOT NULL,
  `job_id` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `zone_spot_spot_id_foreign` (`spot_id`),
  KEY `zone_spot_zone_id_foreign` (`zone_id`),
  KEY `zone_spot_job_id_foreign` (`job_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=39 ;

--
-- Dumping data for table `zone_spot`
--

INSERT INTO `zone_spot` (`id`, `zone_id`, `spot_id`, `job_id`, `created_at`, `updated_at`) VALUES
(1, 1, 1, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(2, 2, 2, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(3, 3, 3, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(4, 4, 4, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(5, 4, 5, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(6, 4, 6, NULL, '2014-10-31 19:28:12', '2014-10-31 19:28:12'),
(7, 2, 3, 4, '2014-10-31 19:28:48', '0000-00-00 00:00:00'),
(8, 3, 3, 5, '2014-10-31 19:28:55', '0000-00-00 00:00:00'),
(9, 2, 3, 5, '2014-10-31 19:29:10', '0000-00-00 00:00:00'),
(10, 1, 3, 4, '2014-10-31 19:29:16', '0000-00-00 00:00:00'),
(11, 2, 3, 4, '2014-11-03 15:46:40', '0000-00-00 00:00:00'),
(12, 1, 3, 4, '2014-11-03 15:51:54', '0000-00-00 00:00:00'),
(13, 2, 3, 4, '2014-11-03 15:52:08', '0000-00-00 00:00:00'),
(14, 1, 3, 4, '2014-11-03 15:52:40', '0000-00-00 00:00:00'),
(15, 2, 3, 4, '2014-11-03 15:53:15', '0000-00-00 00:00:00'),
(16, 3, 3, 5, '2014-11-03 15:53:55', '0000-00-00 00:00:00'),
(17, 2, 3, 4, '2014-11-03 15:54:10', '0000-00-00 00:00:00'),
(18, 1, 3, 4, '2014-11-03 15:55:05', '0000-00-00 00:00:00'),
(19, 2, 3, 4, '2014-11-03 15:59:02', '0000-00-00 00:00:00'),
(20, 1, 3, 4, '2014-11-03 16:01:50', '0000-00-00 00:00:00'),
(21, 2, 3, 4, '2014-11-03 16:02:27', '0000-00-00 00:00:00'),
(22, 1, 3, 4, '2014-11-03 16:02:42', '0000-00-00 00:00:00'),
(23, 2, 3, 4, '2014-11-03 16:04:18', '0000-00-00 00:00:00'),
(24, 3, 3, 5, '2014-11-03 16:05:51', '0000-00-00 00:00:00'),
(25, 2, 3, 4, '2014-11-03 16:06:09', '0000-00-00 00:00:00'),
(26, 1, 3, 4, '2014-11-03 16:07:28', '0000-00-00 00:00:00'),
(27, 2, 3, 4, '2014-11-03 16:10:34', '0000-00-00 00:00:00'),
(28, 1, 3, 4, '2014-11-03 16:11:28', '0000-00-00 00:00:00'),
(29, 2, 3, 4, '2014-11-03 16:11:36', '0000-00-00 00:00:00'),
(30, 1, 3, 4, '2014-11-03 16:13:07', '0000-00-00 00:00:00'),
(31, 2, 3, 4, '2014-11-03 16:13:34', '0000-00-00 00:00:00'),
(32, 1, 3, 4, '2014-11-03 16:20:06', '0000-00-00 00:00:00'),
(33, 2, 3, 4, '2014-11-03 16:21:34', '0000-00-00 00:00:00'),
(34, 1, 3, 4, '2014-11-03 16:22:36', '0000-00-00 00:00:00'),
(35, 2, 3, 4, '2014-11-03 16:22:57', '0000-00-00 00:00:00'),
(36, 1, 3, 4, '2014-11-03 16:23:13', '0000-00-00 00:00:00'),
(37, 2, 3, 4, '2014-11-03 16:23:32', '0000-00-00 00:00:00'),
(38, 1, 3, 4, '2014-11-03 16:23:41', '0000-00-00 00:00:00');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Acceleration`
--
ALTER TABLE `Acceleration`
  ADD CONSTRAINT `acceleration_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`),
  ADD CONSTRAINT `acceleration_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`);

--
-- Constraints for table `Heat`
--
ALTER TABLE `Heat`
  ADD CONSTRAINT `heat_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`),
  ADD CONSTRAINT `heat_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`);

--
-- Constraints for table `Job`
--
ALTER TABLE `Job`
  ADD CONSTRAINT `job_sensor_id_foreign` FOREIGN KEY (`sensor_id`) REFERENCES `Sensor` (`id`),
  ADD CONSTRAINT `job_object_id_foreign` FOREIGN KEY (`object_id`) REFERENCES `Object` (`id`);

--
-- Constraints for table `Light`
--
ALTER TABLE `Light`
  ADD CONSTRAINT `light_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`),
  ADD CONSTRAINT `light_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`);

--
-- Constraints for table `Motion`
--
ALTER TABLE `Motion`
  ADD CONSTRAINT `motion_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`),
  ADD CONSTRAINT `motion_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`);

--
-- Constraints for table `Object`
--
ALTER TABLE `Object`
  ADD CONSTRAINT `object_spot_id_foreign` FOREIGN KEY (`spot_id`) REFERENCES `Spot` (`id`);

--
-- Constraints for table `Spot`
--
ALTER TABLE `Spot`
  ADD CONSTRAINT `spot_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`);

--
-- Constraints for table `zone_object`
--
ALTER TABLE `zone_object`
  ADD CONSTRAINT `zone_object_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`),
  ADD CONSTRAINT `zone_object_object_id_foreign` FOREIGN KEY (`object_id`) REFERENCES `Object` (`id`),
  ADD CONSTRAINT `zone_object_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`);

--
-- Constraints for table `zone_spot`
--
ALTER TABLE `zone_spot`
  ADD CONSTRAINT `zone_spot_job_id_foreign` FOREIGN KEY (`job_id`) REFERENCES `Job` (`id`),
  ADD CONSTRAINT `zone_spot_spot_id_foreign` FOREIGN KEY (`spot_id`) REFERENCES `Spot` (`id`),
  ADD CONSTRAINT `zone_spot_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
