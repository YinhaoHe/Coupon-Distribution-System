CREATE TABLE `merchants` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Merchant Name',
  `logo_url` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'Merchant logo',
  `business_license_url` varchar(256) COLLATE utf8_bin NOT NULL COMMENT 'Merchant License',
  `phone` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Merchant Phone Number',
  `address` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Merchant Address',
  `is_audit` BOOLEAN NOT NULL COMMENT 'Authorization',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;