-- MariaDB dump 10.19  Distrib 10.7.3-MariaDB, for osx10.17 (arm64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	10.7.3-MariaDB
create database test;
use test;

CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `postalCode` varchar(15) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


INSERT INTO `customers` VALUES
(1,'madnerd3','Fat','William','07namcartela',NULL,NULL,NULL,NULL),
(2,'billy2','VeryFat','William','07namcartela',NULL,NULL,NULL,NULL),
(3,'billy3','UltraFat','William','07namcartela',NULL,NULL,NULL,NULL),
(4,'madnerd','Toader','Wiliam','','','','',''),
(6,'Anotha one','Anotha one','Anotha one',NULL,'Anotha one','Anotha one','Anotha one','Anotha one');

CREATE TABLE `products` (
                            `code` varchar(15) NOT NULL,
                            `name` varchar(70) DEFAULT NULL,
                            `description` varchar(256) DEFAULT NULL,
                            `stock` smallint(32) DEFAULT NULL,
                            `price` decimal(10,2) DEFAULT NULL,
                            PRIMARY KEY (`code`),
                            UNIQUE KEY `id_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `products` VALUES
                           ('PROD-1','insecticid','mor dusmanii',30,15.00),
                           ('PROD-2','deodorant','mor dusmanii',69,3.00),
                           ('PROD-3','bomba nucleara','mor dusmanii',30,2.50);


CREATE TABLE `orders` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `order_date` date DEFAULT NULL,
                          `shipped_date` date DEFAULT NULL,
                          `status` varchar(15) DEFAULT NULL,
                          `comments` varchar(2000) DEFAULT NULL,
                          `customer_id` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY (`customer_id`),
                          CONSTRAINT `customer_idx` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

CREATE TABLE `orderdetails` (
                                `quantity` int(11) DEFAULT NULL,
                                `priceEach` decimal(10,2) DEFAULT NULL,
                                `id` int(11) NOT NULL,
                                `product_code` varchar(15) DEFAULT NULL,
                                KEY `id_idx` (`id`),
                                KEY `product_key_idx` (`product_code`),
                                CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `orders` (`id`) ,
                                CONSTRAINT `product_key` FOREIGN KEY (`product_code`) REFERENCES `products` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `payments` (
                            `payment_id` int(11) NOT NULL AUTO_INCREMENT,
                            `customer_id` int(11) DEFAULT NULL,
                            `payment_date` date DEFAULT NULL,
                            `ammount` decimal(10,2) DEFAULT NULL,
                            PRIMARY KEY (`payment_id`),
                            KEY `customer_id` (`customer_id`),
                            CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



