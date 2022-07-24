CREATE TABLE `company` (
  `id` INTEGER NOT NULL,
  `parent_company_id` INTEGER DEFAULT NULL,
  `name` varchar(45)   DEFAULT NULL,
  `CHARGING_TECHNOLOGY` varchar(45)  DEFAULT NULL,
  `company_code` varchar(20)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_company_id` (`parent_company_id`),
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`parent_company_id`) REFERENCES `company` (`id`)
) ;



CREATE TABLE `station` (
  `id` INTEGER NOT NULL,
  `name` varchar(45)   DEFAULT NULL,
  `latitude` decimal(11,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `company_id` INTEGER DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `station_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
);