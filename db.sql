-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 17, 2014 at 07:03 PM
-- Server version: 5.5.38-0ubuntu0.14.04.1
-- PHP Version: 5.5.15RC1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `homestead`
--

-- --------------------------------------------------------

--
-- Table structure for table `Heat`
--

CREATE TABLE IF NOT EXISTS `Heat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `heat_temperature` float(8,2) NOT NULL,
  `zone_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `heat_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Light`
--

CREATE TABLE IF NOT EXISTS `Light` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `light_intensity` int(11) NOT NULL,
  `zone_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `light_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Spot`
--

CREATE TABLE IF NOT EXISTS `Spot` (
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  UNIQUE KEY `spot_spot_address_unique` (`spot_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Spot_Zone`
--

CREATE TABLE IF NOT EXISTS `Spot_Zone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `spot_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `zone_id` int(10) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `spot_zone_spot_address_foreign` (`spot_address`),
  KEY `spot_zone_zone_id_foreign` (`zone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Heat`
--
ALTER TABLE `Heat`
  ADD CONSTRAINT `heat_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`);

--
-- Constraints for table `Light`
--
ALTER TABLE `Light`
  ADD CONSTRAINT `light_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`);

--
-- Constraints for table `Spot_Zone`
--
ALTER TABLE `Spot_Zone`
  ADD CONSTRAINT `spot_zone_zone_id_foreign` FOREIGN KEY (`zone_id`) REFERENCES `Zone` (`id`),
  ADD CONSTRAINT `spot_zone_spot_address_foreign` FOREIGN KEY (`spot_address`) REFERENCES `Spot` (`spot_address`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
