CREATE TABLE `company` (
  `id` binary(16) NOT NULL,
  `parent_company_id` binary(16) DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `charging_Technology` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `company_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_company_id` (`parent_company_id`),
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`parent_company_id`) REFERENCES `company` (`id`)
) ;



CREATE TABLE `station` (
  `id` binary(16) NOT NULL,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `latitude` decimal(11,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `company_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `station_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ;