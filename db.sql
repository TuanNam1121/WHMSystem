CREATE DATABASE  IF NOT EXISTS `wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `wms`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: wms
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `brands` (
  `brandid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `img_url` varchar(500) DEFAULT NULL,
  `description` text,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`brandid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Dell','assets/img/brands/1780372969174_dell.png','Dell products','2026-05-27 11:27:22','2026-06-02 11:02:49'),(2,'Asus','assets/img/brands/1780372932909_asus.jpg','Asus products','2026-05-27 11:27:22','2026-06-02 11:02:13'),(3,'Lenovo','assets/img/brands/1780373160690_lenovo.webp','Lenovo products','2026-05-27 11:27:22','2026-06-02 11:06:01'),(4,'Kingston','assets/img/brands/1780373105062_kingston.svg','RAM','2026-05-27 11:27:22','2026-06-02 11:05:05'),(5,'Samsung','assets/img/brands/1780373242971_samsung.png','SSD','2026-05-27 11:27:22','2026-06-02 11:07:23'),(6,'MSI','assets/img/brands/1780373210201_msi.jpg','MSI (Micro-Star International) là thương hiệu hàng đầu thế giới về giải pháp chơi game','2026-05-31 00:00:00','2026-06-02 11:06:50'),(7,'Acer','assets/img/brands/1780373060442_acer.svg','MSI (Micro-Star International) là thương hiệu hàng đầu ','2026-06-02 00:00:00','2026-06-02 11:04:20'),(8,'prada',NULL,'gucci','2026-06-02 00:00:00','2026-06-02 00:00:00');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `categories` (
  `categoryid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `isactive` tinyint(1) DEFAULT '1',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Laptop','Laptop devices',1,'2026-05-27 11:27:22','2026-05-27 11:27:22'),(2,'RAM','Memory module',1,'2026-05-27 11:27:22','2026-05-27 11:27:22'),(3,'ROM','Storage',0,'2026-05-27 11:27:22','2026-06-02 12:30:23'),(4,'Laptop Gaming','Bao gồm các sản phẩm laptop liên quan chuyên cho gaming ',1,'2026-06-01 20:32:50','2026-06-01 20:32:50'),(6,'Chip',' ',1,'2026-06-01 23:59:57','2026-06-01 23:59:57'),(7,'Laptop Work','',1,'2026-06-02 11:17:34','2026-06-02 11:17:52'),(8,'Laptop A','a',1,'2026-06-02 12:30:12','2026-06-02 12:34:10');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chips`
--

DROP TABLE IF EXISTS `chips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chips` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chips`
--

LOCK TABLES `chips` WRITE;
/*!40000 ALTER TABLE `chips` DISABLE KEYS */;
INSERT INTO `chips` VALUES (1,'Intel Core i5 12450H',0),(2,'Intel Core i7 13620H',0),(3,'AMD Ryzen 5 7530U',1),(4,'AMD Ryzen 7 8845HS',1),(5,'s',0);
/*!40000 ALTER TABLE `chips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Nguyen Van A','0988888888'),(2,'Tran Thi B','0977777777'),(3,'Le Van C','0966666666'),(4,'Pham Thi D','0955555555'),(5,'Hoang Van E','0944444444'),(6,'CellphoneS Thai Ha','0123321123');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `export_receipt_details`
--

DROP TABLE IF EXISTS `export_receipt_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `export_receipt_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `export_receipt_id` int(11) NOT NULL,
  `order_item_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(15,2) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_export_receipt_order_item` (`export_receipt_id`,`order_item_id`),
  KEY `order_item_id` (`order_item_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `fk_export_receipt_details_order_item` FOREIGN KEY (`order_item_id`) REFERENCES `order_items` (`id`),
  CONSTRAINT `fk_export_receipt_details_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`),
  CONSTRAINT `fk_export_receipt_details_receipt` FOREIGN KEY (`export_receipt_id`) REFERENCES `export_receipts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipt_details`
--

LOCK TABLES `export_receipt_details` WRITE;
/*!40000 ALTER TABLE `export_receipt_details` DISABLE KEYS */;
INSERT INTO `export_receipt_details` VALUES (1,1,4,23,5,1000000.00,'2026-06-30 20:55:27'),(2,1,3,22,5,2000000.00,'2026-06-30 20:55:27'),(3,2,6,22,5,5000000.00,'2026-06-30 21:08:51'),(4,3,8,3,3,30000000.00,'2026-07-02 18:02:34'),(5,3,7,2,2,30000000.00,'2026-07-02 18:02:34'),(6,4,10,34,2,25000000.00,'2026-07-03 14:16:56'),(7,4,9,38,5,20000000.00,'2026-07-03 14:16:56'),(8,5,13,3,2,20000000.00,'2026-07-03 17:18:40'),(9,5,12,2,2,10000000.00,'2026-07-03 17:18:40');
/*!40000 ALTER TABLE `export_receipt_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `export_receipt_serials`
--

DROP TABLE IF EXISTS `export_receipt_serials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `export_receipt_serials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `export_receipt_detail_id` int(11) NOT NULL,
  `product_item_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uk_export_receipt_serial_once` (`product_item_id`),
  KEY `export_receipt_detail_id` (`export_receipt_detail_id`),
  CONSTRAINT `fk_export_receipt_serials_detail` FOREIGN KEY (`export_receipt_detail_id`) REFERENCES `export_receipt_details` (`id`),
  CONSTRAINT `fk_export_receipt_serials_product_item` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipt_serials`
--

LOCK TABLES `export_receipt_serials` WRITE;
/*!40000 ALTER TABLE `export_receipt_serials` DISABLE KEYS */;
INSERT INTO `export_receipt_serials` VALUES (1,1,26,'2026-06-30 20:55:27'),(2,1,27,'2026-06-30 20:55:27'),(3,1,28,'2026-06-30 20:55:27'),(4,1,29,'2026-06-30 20:55:27'),(5,1,30,'2026-06-30 20:55:27'),(6,2,40,'2026-06-30 20:55:27'),(7,2,25,'2026-06-30 20:55:27'),(8,2,23,'2026-06-30 20:55:27'),(9,2,24,'2026-06-30 20:55:27'),(10,2,21,'2026-06-30 20:55:27'),(11,3,39,'2026-06-30 21:08:51'),(12,3,38,'2026-06-30 21:08:51'),(13,3,37,'2026-06-30 21:08:51'),(14,3,36,'2026-06-30 21:08:51'),(15,3,22,'2026-06-30 21:08:51'),(16,4,65,'2026-07-02 18:02:34'),(17,4,64,'2026-07-02 18:02:34'),(18,4,63,'2026-07-02 18:02:34'),(19,5,62,'2026-07-02 18:02:34'),(20,5,58,'2026-07-02 18:02:34'),(21,6,15,'2026-07-03 14:16:56'),(22,6,12,'2026-07-03 14:16:56'),(23,7,80,'2026-07-03 14:16:56'),(24,7,6,'2026-07-03 14:16:56'),(25,7,81,'2026-07-03 14:16:56'),(26,7,68,'2026-07-03 14:16:56'),(27,7,2,'2026-07-03 14:16:56'),(28,8,66,'2026-07-03 17:18:40'),(29,8,67,'2026-07-03 17:18:40'),(30,9,60,'2026-07-03 17:18:40'),(31,9,59,'2026-07-03 17:18:40');
/*!40000 ALTER TABLE `export_receipt_serials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `export_receipts`
--

DROP TABLE IF EXISTS `export_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `export_receipts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `status` enum('DRAFT','COMPLETED') NOT NULL DEFAULT 'DRAFT',
  `note` text,
  `created_by` int(11) NOT NULL,
  `exported_by` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `exported_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_export_receipts_order` (`order_id`),
  UNIQUE KEY `code` (`code`),
  KEY `created_by` (`created_by`),
  KEY `exported_by` (`exported_by`),
  CONSTRAINT `fk_export_receipts_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`userid`),
  CONSTRAINT `fk_export_receipts_exported_by` FOREIGN KEY (`exported_by`) REFERENCES `users` (`userid`),
  CONSTRAINT `fk_export_receipts_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipts`
--

LOCK TABLES `export_receipts` WRITE;
/*!40000 ALTER TABLE `export_receipts` DISABLE KEYS */;
INSERT INTO `export_receipts` VALUES (1,2,'COMPLETED',NULL,16,16,'2026-06-30 20:55:27','2026-06-30 20:55:27','2026-07-06 14:35:51','ER-1'),(2,1,'COMPLETED',NULL,16,16,'2026-06-30 21:08:51','2026-06-30 21:08:51','2026-07-06 14:35:51','ER-2'),(3,3,'COMPLETED',NULL,16,16,'2026-07-02 18:02:34','2026-07-02 18:02:34','2026-07-06 14:35:51','ER-3'),(4,4,'COMPLETED',NULL,15,15,'2026-07-03 14:16:56','2026-07-03 14:16:56','2026-07-06 14:35:51','ER-4'),(5,6,'COMPLETED',NULL,15,15,'2026-07-03 17:18:40','2026-07-03 17:18:40','2026-07-06 14:35:51','ER-5');
/*!40000 ALTER TABLE `export_receipts` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_export_receipts_code` AFTER INSERT ON `export_receipts` FOR EACH ROW BEGIN
    UPDATE export_receipts
    SET code = CONCAT('ER-', NEW.id)
    WHERE id = NEW.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `good_receipts`
--

DROP TABLE IF EXISTS `good_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `good_receipts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchaserequestid` int(11) NOT NULL,
  `processedby` int(11) NOT NULL,
  `status` enum('NEW','INCOMPLETED','COMPLETED') DEFAULT 'NEW',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `note` text,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `purchaserequestid` (`purchaserequestid`),
  KEY `processedby` (`processedby`),
  CONSTRAINT `good_receipts_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
  CONSTRAINT `good_receipts_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts` DISABLE KEYS */;
INSERT INTO `good_receipts` VALUES (1,1,16,'COMPLETED','2026-06-30 20:37:22','2026-06-30 20:37:22',NULL,'GR-1'),(2,3,16,'COMPLETED','2026-06-30 20:38:27','2026-06-30 20:38:27',NULL,'GR-2'),(3,2,16,'COMPLETED','2026-06-30 20:40:11','2026-06-30 20:40:11',NULL,'GR-3'),(4,2,16,'COMPLETED','2026-06-30 20:41:15','2026-06-30 20:41:15',NULL,'GR-4'),(5,3,16,'COMPLETED','2026-06-30 21:04:27','2026-06-30 21:04:27',NULL,'GR-5'),(6,6,16,'COMPLETED','2026-06-30 21:07:45','2026-06-30 21:07:45',NULL,'GR-6'),(7,5,15,'COMPLETED','2026-07-03 14:14:49','2026-07-03 14:14:49',NULL,'GR-7');
/*!40000 ALTER TABLE `good_receipts` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_good_receipts_code` AFTER INSERT ON `good_receipts` FOR EACH ROW BEGIN
    UPDATE good_receipts
    SET code = CONCAT('GR-', NEW.id)
    WHERE id = NEW.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `good_receipts_items`
--

DROP TABLE IF EXISTS `good_receipts_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `good_receipts_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodreceiptid` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `actual_quantity` int(11) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `goodreceiptid` (`goodreceiptid`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `good_receipts_items_ibfk_1` FOREIGN KEY (`goodreceiptid`) REFERENCES `good_receipts` (`id`),
  CONSTRAINT `good_receipts_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts_items`
--

LOCK TABLES `good_receipts_items` WRITE;
/*!40000 ALTER TABLE `good_receipts_items` DISABLE KEYS */;
INSERT INTO `good_receipts_items` VALUES (1,1,38,10,'2026-06-30 20:37:22'),(2,2,34,5,'2026-06-30 20:38:28'),(3,2,36,5,'2026-06-30 20:38:28'),(4,3,22,5,'2026-06-30 20:40:11'),(5,3,23,10,'2026-06-30 20:40:11'),(6,4,22,5,'2026-06-30 20:41:15'),(7,5,36,7,'2026-06-30 21:04:27'),(8,6,33,5,'2026-06-30 21:07:45'),(9,6,31,5,'2026-06-30 21:07:45'),(10,7,38,15,'2026-07-03 14:14:49'),(11,7,27,10,'2026-07-03 14:14:50');
/*!40000 ALTER TABLE `good_receipts_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_product` (`product_id`),
  CONSTRAINT `fk_inventory_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,1,0),(2,2,2),(3,3,0),(4,4,0),(5,5,0),(6,8,0),(7,10,0),(8,13,0),(9,14,0),(10,15,0),(11,16,0),(12,17,0),(13,18,0),(14,20,0),(15,22,0),(16,23,5),(17,25,0),(18,26,0),(19,27,10),(20,28,0),(21,29,0),(22,30,0),(23,31,5),(24,32,0),(25,33,5),(26,34,3),(27,35,0),(28,36,12),(29,37,0),(30,38,20);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_audit`
--

DROP TABLE IF EXISTS `inventory_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createdby` int(11) NOT NULL,
  `processedby` int(11) DEFAULT NULL,
  `status` enum('DRAFT','CANCELLED','SUBMITTED','COMPLETED','PENDING','REJECTED') DEFAULT 'DRAFT',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `createdby` (`createdby`),
  KEY `processedby` (`processedby`),
  CONSTRAINT `inventory_audit_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
  CONSTRAINT `inventory_audit_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit`
--

LOCK TABLES `inventory_audit` WRITE;
/*!40000 ALTER TABLE `inventory_audit` DISABLE KEYS */;
INSERT INTO `inventory_audit` VALUES (1,15,14,'COMPLETED','2026-06-30 21:10:01','2026-07-06 14:35:51','IA-1'),(2,14,NULL,'CANCELLED','2026-07-05 09:23:07','2026-07-06 14:35:51','IA-2'),(3,14,14,'COMPLETED','2026-07-05 09:26:07','2026-07-06 14:35:51','IA-3');
/*!40000 ALTER TABLE `inventory_audit` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_inventory_audit_code` AFTER INSERT ON `inventory_audit` FOR EACH ROW BEGIN
    UPDATE inventory_audit
    SET code = CONCAT('IA-', NEW.id)
    WHERE id = NEW.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `inventory_audit_item_serials`
--

DROP TABLE IF EXISTS `inventory_audit_item_serials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory_audit_item_serials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_item_id` int(11) NOT NULL,
  `product_item_id` int(11) NOT NULL,
  `type` enum('ADD','DELETE') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_audit_item_serials_item` (`audit_item_id`),
  KEY `fk_audit_item_serials_product_item` (`product_item_id`),
  CONSTRAINT `fk_audit_item_serials_item` FOREIGN KEY (`audit_item_id`) REFERENCES `inventory_audit_items` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_audit_item_serials_product_item` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit_item_serials`
--

LOCK TABLES `inventory_audit_item_serials` WRITE;
/*!40000 ALTER TABLE `inventory_audit_item_serials` DISABLE KEYS */;
INSERT INTO `inventory_audit_item_serials` VALUES (1,1,58,'ADD'),(2,1,59,'ADD'),(3,1,60,'ADD'),(4,1,61,'ADD'),(5,1,62,'ADD'),(6,2,63,'ADD'),(7,2,64,'ADD'),(8,2,65,'ADD'),(9,2,66,'ADD'),(10,2,67,'ADD'),(11,5,62,'ADD');
/*!40000 ALTER TABLE `inventory_audit_item_serials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_audit_items`
--

DROP TABLE IF EXISTS `inventory_audit_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory_audit_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auditid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `systemquantity` int(11) DEFAULT NULL,
  `physicalquantity` int(11) DEFAULT NULL,
  `discrepancy` int(11) DEFAULT NULL,
  `reasons` text,
  PRIMARY KEY (`id`),
  KEY `auditid` (`auditid`),
  KEY `productid` (`productid`),
  CONSTRAINT `inventory_audit_items_ibfk_1` FOREIGN KEY (`auditid`) REFERENCES `inventory_audit` (`id`),
  CONSTRAINT `inventory_audit_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit_items`
--

LOCK TABLES `inventory_audit_items` WRITE;
/*!40000 ALTER TABLE `inventory_audit_items` DISABLE KEYS */;
INSERT INTO `inventory_audit_items` VALUES (1,1,2,0,5,NULL,'Những hàng tồn trước khi có hệ thống'),(2,1,3,0,5,NULL,'Những hàng tồn trước khi có hệ thống'),(3,2,33,5,0,NULL,''),(4,2,34,3,0,NULL,''),(5,3,2,1,2,NULL,'Thừa một cái do xuất thiếu từ bill ER-3'),(6,3,23,5,5,NULL,'');
/*!40000 ALTER TABLE `inventory_audit_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `models` (
  `modelid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `brandid` int(11) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`modelid`),
  KEY `brandid` (`brandid`),
  CONSTRAINT `models_ibfk_1` FOREIGN KEY (`brandid`) REFERENCES `brands` (`brandid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (1,'Inspiron 15',1,1),(2,'TUF A15',2,1),(3,'Thinkpad E14',3,1),(4,'Fury Beast',4,1),(5,'970 EVO',5,1),(6,'TUF A9',2,1),(7,'TP3402VA',2,1),(8,'RV037W',2,1),(9,'15ARP10E',3,1),(10,'K3605VC',2,1),(11,'ProPanel',7,1),(12,'M1502NAQ',2,1),(13,'Lite 16 GEN 2',7,1);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderid` (`orderid`),
  KEY `productid` (`productid`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (3,2,22,5,2000000.00),(4,2,23,5,1000000.00),(6,1,22,5,5000000.00),(7,3,2,2,30000000.00),(8,3,3,3,30000000.00),(9,4,38,5,20000000.00),(10,4,34,2,25000000.00),(11,5,38,2,20000000.00),(12,6,2,2,10000000.00),(13,6,3,2,20000000.00),(14,7,31,2,500000.00),(15,8,2,1,1.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items_product_items`
--

DROP TABLE IF EXISTS `order_items_product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_items_product_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderitemid` int(11) NOT NULL,
  `productitemid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderitemid` (`orderitemid`,`productitemid`),
  KEY `productitemid` (`productitemid`),
  CONSTRAINT `order_items_product_items_ibfk_1` FOREIGN KEY (`orderitemid`) REFERENCES `order_items` (`id`),
  CONSTRAINT `order_items_product_items_ibfk_2` FOREIGN KEY (`productitemid`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items_product_items`
--

LOCK TABLES `order_items_product_items` WRITE;
/*!40000 ALTER TABLE `order_items_product_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items_product_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` enum('NEW','DOING','COMPLETED','CANCELLED') DEFAULT 'NEW',
  `total_price` decimal(15,2) DEFAULT NULL,
  `note` text,
  `orderdate` datetime DEFAULT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completedat` datetime DEFAULT NULL,
  `createdby` int(11) NOT NULL,
  `processedby` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `createdby` (`createdby`),
  KEY `processedby` (`processedby`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'COMPLETED',25000000.00,'','2026-06-30 20:46:34','2026-06-30 20:46:34','2026-07-06 14:35:51','2026-06-30 21:08:51',16,16,2,'SO-1'),(2,'COMPLETED',15000000.00,'','2026-06-30 20:47:25','2026-06-30 20:47:25','2026-07-06 14:35:51','2026-06-30 20:55:27',16,16,4,'SO-2'),(3,'COMPLETED',150000000.00,'','2026-07-01 13:52:55','2026-07-01 13:52:55','2026-07-06 14:35:51','2026-07-02 18:02:34',16,16,3,'SO-3'),(4,'COMPLETED',150000000.00,'','2026-07-03 14:12:02','2026-07-03 14:12:02','2026-07-06 14:35:51','2026-07-03 14:16:56',16,15,1,'SO-4'),(5,'NEW',40000000.00,'','2026-07-03 16:30:27','2026-07-03 16:30:27','2026-07-06 14:35:51',NULL,16,NULL,1,'SO-5'),(6,'COMPLETED',60000000.00,'','2026-07-03 17:13:12','2026-07-03 17:13:12','2026-07-06 14:35:51','2026-07-03 17:18:40',16,15,1,'SO-6'),(7,'NEW',1000000.00,'','2026-07-04 21:55:48','2026-07-04 21:55:48','2026-07-06 14:35:51',NULL,16,NULL,1,'SO-7'),(8,'NEW',1.00,'Xuất bù vì lần trước xuất bị thiếu (Đã kiểm kê với phiếu AU-3) với mã serial H5MT8ZN3QK','2026-07-05 15:05:52','2026-07-05 15:05:52','2026-07-06 14:35:51',NULL,16,NULL,3,'SO-8');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_orders_code` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
    UPDATE orders
    SET code = CONCAT('SO-', NEW.id)
    WHERE id = NEW.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `password_resets` (
  `requestid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `status` enum('NEW','COMPLETED') DEFAULT 'NEW',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `completedat` datetime DEFAULT NULL,
  PRIMARY KEY (`requestid`),
  KEY `userid` (`userid`),
  CONSTRAINT `password_resets_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_resets`
--

LOCK TABLES `password_resets` WRITE;
/*!40000 ALTER TABLE `password_resets` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_resets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `permissions` (
  `permissionid` int(11) NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'VIEW_INVENTORY_AUDIT','Can view inventory audits'),(2,'CREATE_INVENTORY_AUDIT','Can create inventory audits'),(3,'PERFORM_INVENTORY_AUDIT','Can perform inventory audits'),(4,'APPROVE_INVENTORY_AUDIT','Can approve or decline inventory audits'),(5,'VIEW_INVENTORY_TRANSACTION','Can view inventory transactions'),(6,'VIEW_PRODUCT','View product list'),(7,'CREATE_PRODUCT','Create new product'),(8,'UPDATE_PRODUCT','Update product'),(9,'VIEW_CATEGORY','View category list'),(10,'CREATE_CATEGORY','Create new category'),(11,'UPDATE_CATEGORY','Update category'),(12,'VIEW_BRAND','View brand list'),(13,'CREATE_BRAND','Create new brand'),(14,'UPDATE_BRAND','Update brand'),(15,'VIEW_SPECIFICATION','View specification list'),(16,'CREATE_SPECIFICATION','Create specification'),(17,'UPDATE_SPECIFICATION','Update specification'),(18,'VIEW_SALE_ORDER','View sale order list'),(19,'CREATE_SALE_ORDER','Create sale order'),(20,'UPDATE_SALE_ORDER','Update sale order'),(21,'VIEW_PURCHASE_ORDER','View purchase order list'),(22,'CREATE_PURCHASE_ORDER','Create purchase order'),(23,'UPDATE_PURCHASE_ORDER','Update purchase order'),(24,'APPROVE_REJECT_PURCHASE_REQUEST','Approve or Reject purchase request'),(25,'VIEW_IMPORT_REQUEST','View import request list'),(26,'VIEW_IMPORT_HISTORY','View import history'),(27,'PROCESS_IMPORT','Process import'),(28,'VIEW_EXPORT_PRODUCT','View export product list'),(29,'VIEW_EXPORT_HISTORY','View export history'),(30,'PROCESS_EXPORT','Process export'),(31,'VIEW_INVENTORY','View inventory'),(32,'VIEW_REPORT','View reports'),(33,'VIEW_CUSTOMER','View customer list'),(34,'CREATE_CUSTOMER','Create customer'),(35,'UPDATE_CUSTOMER','Update customer'),(36,'VIEW_SUPPLIER','View supplier list'),(37,'CREATE_SUPPLIER','Create supplier'),(38,'UPDATE_SUPPLIER','Update supplier');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_items`
--

DROP TABLE IF EXISTS `product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(100) DEFAULT NULL,
  `imported_price` decimal(15,2) DEFAULT NULL,
  `export_price` decimal(15,2) DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  `imported_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `product_id` int(11) NOT NULL,
  `goodreceiptsitemid` int(11) DEFAULT NULL,
  `status` enum('AVAILABLE','UNAVAILABLE','SOLD') DEFAULT 'AVAILABLE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `serial` (`serial`),
  KEY `product_id` (`product_id`),
  KEY `goodreceiptsitemid` (`goodreceiptsitemid`),
  CONSTRAINT `product_items_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`),
  CONSTRAINT `product_items_ibfk_2` FOREIGN KEY (`goodreceiptsitemid`) REFERENCES `good_receipts_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_items`
--

LOCK TABLES `product_items` WRITE;
/*!40000 ALTER TABLE `product_items` DISABLE KEYS */;
INSERT INTO `product_items` VALUES (1,'X7M2K9Q4PL',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(2,'8NTR5VY2AZ',15000000.00,20000000.00,1,'2026-06-30 20:37:22',38,1,'SOLD'),(3,'Q1LW8JH6XC',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(4,'P9F2RMT7BK',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(5,'H4ZN8QW1VE',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(6,'C6TY3LXP9M',15000000.00,20000000.00,1,'2026-06-30 20:37:22',38,1,'SOLD'),(7,'J8AK5NQ2RD',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(8,'W2PV7HMX6L',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(9,'M9QC4TZ8FN',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(10,'R5XJ1KVL7P',15000000.00,NULL,1,'2026-06-30 20:37:22',38,1,'AVAILABLE'),(11,'G5LP8YQ3MX',20000000.00,NULL,1,'2026-06-30 20:38:28',34,2,'AVAILABLE'),(12,'D7KR2VN6JF',20000000.00,25000000.00,1,'2026-06-30 20:38:28',34,2,'SOLD'),(13,'X1MT9QW5BZ',20000000.00,NULL,1,'2026-06-30 20:38:28',34,2,'AVAILABLE'),(14,'R8YL4PH2NC',20000000.00,NULL,1,'2026-06-30 20:38:28',34,2,'AVAILABLE'),(15,'F3QV7KX9TD',20000000.00,25000000.00,1,'2026-06-30 20:38:28',34,2,'SOLD'),(16,'K8WQ3MZ7RT',11150000.00,NULL,1,'2026-06-30 20:38:28',36,3,'AVAILABLE'),(17,'P4XN9LJ2VC',11150000.00,NULL,1,'2026-06-30 20:38:28',36,3,'AVAILABLE'),(18,'T7HB5QK8YA',11150000.00,NULL,1,'2026-06-30 20:38:28',36,3,'AVAILABLE'),(19,'N2RM6XF9PL',11150000.00,NULL,1,'2026-06-30 20:38:28',36,3,'AVAILABLE'),(20,'V9CZ4TW1KH',11150000.00,NULL,1,'2026-06-30 20:38:28',36,3,'AVAILABLE'),(21,'C9PV3QK7LX',250000.00,2000000.00,1,'2026-06-30 20:40:11',22,4,'SOLD'),(22,'Y5RM8HN2WD',250000.00,5000000.00,1,'2026-06-30 20:40:11',22,4,'SOLD'),(23,'L1XT6ZQ4PF',250000.00,2000000.00,1,'2026-06-30 20:40:11',22,4,'SOLD'),(24,'V7KC9MJ3RA',250000.00,2000000.00,1,'2026-06-30 20:40:11',22,4,'SOLD'),(25,'F2NW5YP8TH',250000.00,2000000.00,1,'2026-06-30 20:40:11',22,4,'SOLD'),(26,'A7MX9QK2LP',150000.00,1000000.00,1,'2026-06-30 20:40:11',23,5,'SOLD'),(27,'R5VT8NWC3H',150000.00,1000000.00,1,'2026-06-30 20:40:11',23,5,'SOLD'),(28,'K1JZ6PY4XM',150000.00,1000000.00,1,'2026-06-30 20:40:11',23,5,'SOLD'),(29,'T9LQ2RB7VF',150000.00,1000000.00,1,'2026-06-30 20:40:11',23,5,'SOLD'),(30,'H3CN8MW5PK',150000.00,1000000.00,1,'2026-06-30 20:40:11',23,5,'SOLD'),(31,'X6YD1KT9QJ',150000.00,NULL,1,'2026-06-30 20:40:11',23,5,'AVAILABLE'),(32,'P4ZG7HV2RL',150000.00,NULL,1,'2026-06-30 20:40:11',23,5,'AVAILABLE'),(33,'N8WF5XM1TC',150000.00,NULL,1,'2026-06-30 20:40:11',23,5,'AVAILABLE'),(34,'Q2KR9LP6YV',150000.00,NULL,1,'2026-06-30 20:40:11',23,5,'AVAILABLE'),(35,'M7BX4TJ8NH',150000.00,NULL,1,'2026-06-30 20:40:11',23,5,'AVAILABLE'),(36,'U8QK3MXL7P',250000.00,5000000.00,1,'2026-06-30 20:41:15',22,6,'SOLD'),(37,'E5VN9TR2HC',250000.00,5000000.00,1,'2026-06-30 20:41:15',22,6,'SOLD'),(38,'S1YP6JW4KZ',250000.00,5000000.00,1,'2026-06-30 20:41:15',22,6,'SOLD'),(39,'L7XF2QM8RV',250000.00,5000000.00,1,'2026-06-30 20:41:15',22,6,'SOLD'),(40,'A9HC5TN1MW',250000.00,2000000.00,1,'2026-06-30 20:41:15',22,6,'SOLD'),(41,'A7X9K2M4QP',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(42,'N5R8T1W6ZC',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(43,'Q3L7V9B2HJ',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(44,'X8M4P6K1RY',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(45,'C2F9N7D5TW',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(46,'H6J1Q8Z4LX',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(47,'P9W3G5R7MK',11150000.00,NULL,1,'2026-06-30 21:04:27',36,7,'AVAILABLE'),(48,'A7K9-X2QM-8P4L-N5TR',15000000.00,NULL,1,'2026-06-30 21:07:45',33,8,'AVAILABLE'),(49,'M4DZ-7QWX-J8CN-2VPL',15000000.00,NULL,1,'2026-06-30 21:07:45',33,8,'AVAILABLE'),(50,'T9FR-K6PA-X3WM-8QNZ',15000000.00,NULL,1,'2026-06-30 21:07:45',33,8,'AVAILABLE'),(51,'H2LV-9XQC-M7RT-P4KD',15000000.00,NULL,1,'2026-06-30 21:07:45',33,8,'AVAILABLE'),(52,'W8NJ-3PKM-T6QX-R9CV',15000000.00,NULL,1,'2026-06-30 21:07:45',33,8,'AVAILABLE'),(53,'Q5ZT-L8RW-N2XP-K7MF',20000000.00,NULL,1,'2026-06-30 21:07:46',31,9,'AVAILABLE'),(54,'C4YP-V9TK-H6QW-X2RN',20000000.00,NULL,1,'2026-06-30 21:07:46',31,9,'AVAILABLE'),(55,'R7MX-2LQV-P8KC-T5ZW',20000000.00,NULL,1,'2026-06-30 21:07:46',31,9,'AVAILABLE'),(56,'N3QH-W7XP-K4RM-V8TL',20000000.00,NULL,1,'2026-06-30 21:07:46',31,9,'AVAILABLE'),(57,'X6PV-T2KN-R9QM-H5WC',20000000.00,NULL,1,'2026-06-30 21:07:46',31,9,'AVAILABLE'),(58,'X7K9M2P4LT',0.00,30000000.00,1,'2026-06-30 21:10:59',2,NULL,'SOLD'),(59,'Q8W3N5RZ1H',0.00,10000000.00,1,'2026-06-30 21:10:59',2,NULL,'SOLD'),(60,'T4YP8K6MNC',0.00,10000000.00,1,'2026-06-30 21:11:00',2,NULL,'SOLD'),(61,'L9VX2Q7RWP',0.00,NULL,1,'2026-06-30 21:11:00',2,NULL,'AVAILABLE'),(62,'H5MT8ZN3QK',0.00,30000000.00,1,'2026-06-30 21:11:00',2,NULL,'AVAILABLE'),(63,'R2CW9PX6TL',0.00,30000000.00,1,'2026-06-30 21:11:00',3,NULL,'SOLD'),(64,'N7KF4VQ8MY',0.00,30000000.00,1,'2026-06-30 21:11:00',3,NULL,'SOLD'),(65,'P3ZH6TR9WX',0.00,30000000.00,1,'2026-06-30 21:11:00',3,NULL,'SOLD'),(66,'M8QL2YV5KC',0.00,20000000.00,1,'2026-06-30 21:11:00',3,NULL,'SOLD'),(67,'W4RN7XP9TH',0.00,20000000.00,1,'2026-06-30 21:11:00',3,NULL,'SOLD'),(68,'A7K9X2M4QP',15000000.00,20000000.00,1,'2026-07-03 14:14:49',38,10,'SOLD'),(69,'N8D5L1Z7TR',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(70,'Q3W9E6R2TY',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(71,'M5V8C1B7NJ',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(72,'H2P9L4X6KA',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(73,'T7Y3U8I1OP',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(74,'F4G9H2J6KL',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(75,'Z1X7C5V8BN',15000000.00,NULL,1,'2026-07-03 14:14:49',38,10,'AVAILABLE'),(76,'R6T2Y9U4IO',15000000.00,NULL,1,'2026-07-03 14:14:50',38,10,'AVAILABLE'),(77,'P8A3S7D1FG',15000000.00,NULL,1,'2026-07-03 14:14:50',38,10,'AVAILABLE'),(78,'L5K9J2H6GF',15000000.00,NULL,1,'2026-07-03 14:14:50',38,10,'AVAILABLE'),(79,'W4E8R1T7YU',15000000.00,NULL,1,'2026-07-03 14:14:50',38,10,'AVAILABLE'),(80,'C9N3M6Q2ZX',15000000.00,20000000.00,1,'2026-07-03 14:14:50',38,10,'SOLD'),(81,'B7V4P8L1KH',15000000.00,20000000.00,1,'2026-07-03 14:14:50',38,10,'SOLD'),(82,'X2Z5A9S6DF',15000000.00,NULL,1,'2026-07-03 14:14:50',38,10,'AVAILABLE'),(83,'K7M2Q9XP',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(84,'A5T8L3ZW',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(85,'N1R6Y4HC',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(86,'P9V2B7JK',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(87,'D3F8X1MN',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(88,'Q6W4E9RT',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(89,'H2L7Z5CV',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(90,'Y8U1I3OP',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(91,'G4J9K2LS',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE'),(92,'X7C5N8BV',12000000.00,NULL,1,'2026-07-03 14:14:50',27,11,'AVAILABLE');
/*!40000 ALTER TABLE `product_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
  `productid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` text,
  `img_url` varchar(500) DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ramid` int(11) DEFAULT NULL,
  `romid` int(11) DEFAULT NULL,
  `chipid` int(11) DEFAULT NULL,
  `unitid` int(11) NOT NULL,
  `categoryid` int(11) NOT NULL,
  `brandid` int(11) NOT NULL,
  `modelid` int(11) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`productid`),
  KEY `ramid` (`ramid`),
  KEY `romid` (`romid`),
  KEY `chipid` (`chipid`),
  KEY `unitid` (`unitid`),
  KEY `categoryid` (`categoryid`),
  KEY `brandid` (`brandid`),
  KEY `modelid` (`modelid`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`ramid`) REFERENCES `rams` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`romid`) REFERENCES `roms` (`id`),
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`chipid`) REFERENCES `chips` (`id`),
  CONSTRAINT `products_ibfk_4` FOREIGN KEY (`unitid`) REFERENCES `units` (`id`),
  CONSTRAINT `products_ibfk_5` FOREIGN KEY (`categoryid`) REFERENCES `categories` (`categoryid`),
  CONSTRAINT `products_ibfk_6` FOREIGN KEY (`brandid`) REFERENCES `brands` (`brandid`),
  CONSTRAINT `products_ibfk_7` FOREIGN KEY (`modelid`) REFERENCES `models` (`modelid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Laptop Dell 14 DC14250','Office ','assets/img/product/1780336108147_ssss_1_125.webp',0,'2026-05-27 11:27:22','2026-06-22 08:41:06',2,2,1,1,1,1,1,'D15-23'),(2,'Asus TUF Gaming T12','Gaming updated','assets/img/product/1780327162854_laptopleveno.jpg',1,'2026-05-27 11:27:22','2026-06-23 10:45:11',4,3,2,1,1,2,2,'A12-53'),(3,'ThinkPad E14 A12-54','Business laptop updated','assets/img/product/1780327242054_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg',1,'2026-05-27 11:27:22','2026-06-22 22:08:45',3,2,3,1,1,3,3,'A12-54'),(4,'Kingston Fury 16GB 32000MHZ','RAM DDR4 Tesst update','assets/img/product/1780327214479_ktc-hero-ddr5-overview-lg.jpg',1,'2026-05-27 11:27:22','2026-06-23 10:41:18',3,NULL,NULL,2,2,4,NULL,'B12-423'),(5,'Samsung 970 EVO 512GB','SSD ass','assets/img/product/1780365511731_ssss_1_125.webp',1,'2026-05-27 11:27:22','2026-06-22 22:24:09',NULL,2,NULL,3,3,5,NULL,'B12-45'),(8,'Asus TUF 7 ','con mèo kêu làm sao','assets/img/product/1780327319228_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg',1,'2026-05-28 23:29:54','2026-06-01 22:21:59',2,2,2,1,1,2,2,'B32-12'),(10,'Asus TUF 13','meomeo aa','assets/img/product/1780327333216_text_ng_n_2__9_31.webp',1,'2026-05-29 13:32:34','2026-06-02 11:50:05',1,2,2,1,1,7,11,'C21-23'),(13,'Laptop HP 15-fd1289TU Ultra 7','','assets/img/product/1780327353097_laptopleveno.jpg',1,'2026-05-30 16:12:45','2026-06-02 11:24:31',3,2,1,1,4,2,8,'Generatorc412'),(14,'Laptop ASUS Vivobook S 14 FLIP','Laptop ASUS Vivobook S 14 Flip TP3402VA-LZ632W sở hữu bộ CPU Intel Core i5-13420H đi cùng RAM 16GB chuẩn DDR4, cộng thêm ổ cứng 512GB M.2 PCIe 4.0. Mẫu laptop ASUS Vivobook này được trang bị màn hình có độ phân giải WUXGA với kích thước 14 inch. Bên cạnh đó, thiết kế Flip còn cho phép người dùng chuyển đổi laptop và tablet theo nhu cầu.','assets/img/product/1780334374996_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg',1,'2026-05-31 00:18:47','2026-06-02 08:55:59',2,3,3,1,1,2,7,'LZ632W'),(15,'Laptop ASUS TUF Gaming F16 FX608JHR','Laptop ASUS TUF Gaming F16 FX608JHR-RV037W được tích hợp một CPU Intel Core i7-14650HX 5.2 GHz, cùng với RAM 16GB và có bộ nhớ SSD lớn lên đến 1TB. Máy còn sở hữu VGA NVIDIA GeForce RTX 8GB kết hợp màn hình FHD+ 16 inch và có tần số quét 165Hz. Máy có màu Jaeger Gray, chỉ nặng 2.2 kg, với pin 90WHrs, phù hợp cho chơi game và làm.','assets/img/product/1780299967530_laptopleveno.jpg',1,'2026-05-31 11:20:01','2026-06-01 14:46:07',4,2,2,1,1,2,8,'RV037W'),(16,'Kingston Fury 16GB','RAM DDR4','assets/img/product/1780322903782_kingston_8g_3200_compressed_1.webp',1,'2026-05-31 22:00:03','2026-06-02 09:31:47',NULL,3,NULL,2,3,4,NULL,'KVR32S22S6/4'),(17,'SSD Samsung 123 120000MHz','RAM DDR4 SSD Samsung 123 120000MHz','assets/img/product/1780361796964_ssss_1_125.webp',1,'2026-05-31 22:24:24','2026-06-22 22:25:41',3,NULL,NULL,2,2,4,NULL,'KVR32S22S'),(18,'RAM Laptop Kingston 4 GB-DDR4-3200 MHz','con mèo kêu','assets/img/product/1780247214567_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg',1,'2026-05-31 22:39:51','2026-06-02 08:23:16',2,2,NULL,1,3,4,NULL,'KVR32S22S6'),(20,'RAM Laptop Kingston Sodimm 1.2V','RAM Laptop Kingston Sodimm 1.2V 16GB 3200MHz CL22','assets/img/product/1780244875864_kingston_8g_3200_compressed_1.webp',1,'2026-05-31 23:27:55','2026-06-02 09:22:02',2,3,NULL,1,3,4,NULL,'CL22'),(22,'SSD Samsung 123','RAM DDR4','assets/img/product/1780246656870_text_ng_n_15_21_1.webp',1,'2026-05-31 23:57:36','2026-05-31 23:57:36',3,NULL,NULL,2,2,4,NULL,'KVR32S2'),(23,'Ổ cứng HDD Enterprise WD Ultrastar DC HC330 10TB 3.5','Ổ cứng HDD WD Ultrastar DC HC330 10TB là một ổ cứng dành cho doanh nghiệp với nhiều tính năng nổi bật. Ổ cứng này đáp ứng nhu cầu lưu trữ, tốc độ truy cập dữ liệu, độ bền và ổn định cao của các doanh nghiệp.\r\n\r\n','assets/img/product/1780246828025_05-hdd-enterprise-wd-ultrastar-dc-hc330-10tb-35-01.jpg',1,'2026-06-01 00:00:28','2026-06-02 09:01:58',NULL,3,NULL,3,3,5,NULL,'WUS721010ALE6L4'),(25,'Laptop ASUS Gaming Vivobook 16X K3605VC-RP431W','Laptop ASUS Vivobook 16X K3605VC-RP431W được trang bị vi xử lý Intel Core i5-13420H cho ra hiệu suất ổn định từ công việc cho đến nhu cầu giải trí. Mẫu ASUS Vivobook Gaming này có màn hình lên đến 16 inch cùng tần số quét 144Hz cung cấp hình ảnh sắc nét không bị vỡ. Card đồ hoạ NVIDIA GeForce RTX 3050 4GB GDDR6 là một điểm cộng lớn của dòng laptop này.','assets/img/product/1780333162727_text_ng_n_5__9_130.webp',1,'2026-06-01 23:59:22','2026-06-02 12:16:17',3,2,2,1,4,1,1,'RP431W'),(26,'CPU AMD Ryzen 7 7800X3D (Tray)','asa','assets/img/product/1780333276897_cpu-amd-ryzen-7-7800x3d_2__3.webp',1,'2026-06-02 00:01:16','2026-06-02 11:10:33',3,2,1,3,2,7,11,'7800X3D '),(27,'RAM Laptop Kingston 4-3200 MHz','asss','assets/img/product/1780333845552_text_ng_n_5__9_130.webp',1,'2026-06-02 00:10:35','2026-06-02 00:10:45',3,3,2,1,4,2,6,'R2341D'),(28,'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR','Laptop Acer Gaming Nitro V 15 ProPanel ANV15-41-R7CR sở hữu cấu hình mạnh mẽ với CPU AMD Ryzen 5 7535HS and card đồ họa NVIDIA GeForce RTX 4050 6GB GDDR6. Màn hình 15.6 inch FHD IPS and tần số quét 180Hz đem lại hình ảnh sắc nét. Ổ cứng 512GB PCIe NVMe SSD and RAM 16GB DDR5, với hệ thống tản nhiệt Dual-fan đảm bảo hiệu suất tối ưu.\r\n\r\n','assets/img/product/1780334956211_sssxs_26.png',1,'2026-06-02 00:29:05','2026-06-02 00:45:15',2,2,2,1,1,7,11,'ANV15-41'),(29,'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR','','assets/img/product/1780336413334_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png',1,'2026-06-02 00:31:32','2026-06-02 00:53:33',2,2,2,1,1,7,11,'ANV15-41-R5AS'),(30,'RAM Laptop Transcend DDR5 4800MHz 16GB','Ram laptop','assets/img/product/1780335486900_ram-transcend-ddr5-4800mhz-16gb_1_.webp',1,'2026-06-02 00:38:06','2026-07-05 15:11:35',3,NULL,NULL,1,2,5,NULL,'Q3QWE2'),(31,'RAM Laptop Transcend DDR5 4800MHz 16GB','','assets/img/product/1780335748931_ram-transcend-ddr5-4800mhz-16gb_1_.webp',1,'2026-06-02 00:42:28','2026-06-02 00:42:28',NULL,2,NULL,2,3,2,NULL,'R.TC.'),(32,'CPU AMD Ryzen 5 5500','','assets/img/product/1780336054684_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png',0,'2026-06-02 00:47:00','2026-06-02 00:47:34',3,2,3,1,6,7,11,'CPU.AM.09'),(33,'Laptop ASUS VivoBook 15','aa','assets/img/product/1780364201177_ssss_2_42.png',1,'2026-06-02 08:36:41','2026-06-02 12:16:35',2,1,3,1,1,1,1,'BQ021W'),(34,'Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU','Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU sở hữu màn hình 16 inch Full HD+, RAM 16GB DDR5 tốc độ 4800MHz (hỗ trợ nâng cấp tối đa 64GB). Chiếc laptop Acer Aspire được trang bị Intel Core i7-1355U, card đồ họa Intel Iris Xe, cùng loa Stereo and webcam Full HD. Thiết kế gọn nhẹ 1.7kg, tích hợp đầy đủ cổng kết nối giúp sử dụng linh hoạt.','assets/img/product/1780375469602_text_ng_n_6__2_234.png',1,'2026-06-02 11:44:29','2026-06-02 12:17:03',3,2,2,1,7,7,13,'AL16-52P-76DU'),(35,'Laptop ASUS Vivobook S14 S3407VA','Laptop ASUS Vivobook S14 S3407VA-LY146W trang bị vi xử lý Intel Core 5 210H, RAM 16GB DDR5, SSD 512GB cùng với màn hình 14 inch WUXGA sắc nét, chân thực. Máy có thiết kế mỏng nhẹ chỉ 1.4kg, vỏ kim loại bền bỉ, pin lớn 70Wh cho thời gian sử dụng dài. Hỗ trợ Wi-Fi 6, camera IR nhận diện khuôn mặt and bàn phím có đèn nền tích hợp phím Copilot.\r\n','assets/img/product/1780378095512_text_ng_n_4__8_52.webp',0,'2026-06-02 12:28:15','2026-06-02 12:28:55',3,4,4,1,1,2,8,'LY146W'),(36,'Laptop Acer Gaming Aspire 7 A715-59G-57TU','Laptop Acer Gaming Aspire 7 A715-59G-57TU được trang bị vi xử lý Intel Core i5-12450H cân trơn tru mọi tác vụ từ văn phòng cho đến chơi game nặng. Hỗ trợ cho vi xử lý là card đồ hoạ RTX 3050 6GB giúp chơi game nặng mượt mà hơn. Người dùng có thể mở nhiều nội dung hiển thị cùng một lúc với màn hình lên đến 15.6 inch.\r\n\r\n','assets/img/product/1780379484406_text_ng_n_14__9_26.webp',1,'2026-06-02 12:51:24','2026-06-22 22:18:53',3,2,3,1,4,7,13,'A715-59G-57TU'),(37,'Laptop Acer Aspire Lite 15 AL15-46P-R73C','Laptop Acer Aspire Lite 15 AL15-46P-R73C sở hữu hiệu năng ấn tượng nhờ chip AMD Ryzen 3 5400U, RAM 8GB DDR4, cùng bộ nhớ trong SSD 512GB rộng rãi. Máy có màn hình Full HD 15.6 inch, tần số quét 60Hz trong thân máy chỉ 1.45kg. Laptop có pin 53Wh and hệ thống cổng đa dạng gồm: USB-C, USB-A and HDMI.\r\n\r\n','assets/img/product/1780379586909_sssxs_1__9.webp',1,'2026-06-02 12:53:06','2026-06-23 14:58:54',3,2,3,1,4,7,13,'AL15-46P-R73C'),(38,'Laptop ASUS Vivobook 14','Máy này là máy mới','assets/img/product/1782826321003_laptop_asus_vivobook_14_x1404va-eb509w_-_1.png',1,'2026-06-30 20:32:01','2026-06-30 20:32:21',3,2,3,1,1,2,7,'EB509W');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_product_insert` AFTER INSERT ON `products` FOR EACH ROW BEGIN
    INSERT INTO inventory (product_id, quantity) VALUES (NEW.productid, 0);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `purchase_request_items`
--

DROP TABLE IF EXISTS `purchase_request_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `purchase_request_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchaserequestid` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `purchaserequestid` (`purchaserequestid`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `purchase_request_items_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
  CONSTRAINT `purchase_request_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_request_items`
--

LOCK TABLES `purchase_request_items` WRITE;
/*!40000 ALTER TABLE `purchase_request_items` DISABLE KEYS */;
INSERT INTO `purchase_request_items` VALUES (1,1,38,10,15000000.00,0),(2,2,23,10,150000.00,0),(3,2,22,10,250000.00,0),(4,3,36,12,11150000.00,0),(5,3,34,5,20000000.00,0),(6,4,20,10,500000.00,0),(7,4,18,10,250000.00,0),(8,5,38,15,15000000.00,0),(9,5,27,10,12000000.00,0),(10,6,33,5,15000000.00,0),(11,6,31,5,20000000.00,0),(12,7,37,5,12000000.00,0),(13,7,30,5,500000.00,0),(14,8,37,1,10000000.00,0),(15,8,34,1,15000000.00,0);
/*!40000 ALTER TABLE `purchase_request_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_requests`
--

DROP TABLE IF EXISTS `purchase_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `purchase_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createdby` int(11) NOT NULL,
  `approvedby` int(11) DEFAULT NULL,
  `status` enum('NEW','APPROVED','REJECTED','PROCESSING','COMPLETED') DEFAULT 'NEW',
  `note` text,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `supplierid` int(11) NOT NULL,
  `code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `createdby` (`createdby`),
  KEY `approvedby` (`approvedby`),
  KEY `purchase_requests_ibfk_3` (`supplierid`),
  CONSTRAINT `purchase_requests_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
  CONSTRAINT `purchase_requests_ibfk_2` FOREIGN KEY (`approvedby`) REFERENCES `users` (`userid`),
  CONSTRAINT `purchase_requests_ibfk_3` FOREIGN KEY (`supplierid`) REFERENCES `suppliers` (`supplierid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_requests`
--

LOCK TABLES `purchase_requests` WRITE;
/*!40000 ALTER TABLE `purchase_requests` DISABLE KEYS */;
INSERT INTO `purchase_requests` VALUES (1,16,14,'COMPLETED','','2026-06-30 20:33:04','2026-07-06 14:35:51',0,2,'PR-1'),(2,16,14,'COMPLETED','Nhập lô ram mới','2026-06-30 20:33:46','2026-07-06 14:35:51',0,3,'PR-2'),(3,16,14,'COMPLETED','','2026-06-30 20:34:25','2026-07-06 14:35:51',0,1,'PR-3'),(4,16,NULL,'REJECTED','','2026-06-30 20:35:35','2026-07-06 14:35:51',0,1,'PR-4'),(5,16,14,'COMPLETED','','2026-06-30 20:36:17','2026-07-06 14:35:51',0,2,'PR-5'),(6,16,14,'COMPLETED','','2026-06-30 21:06:27','2026-07-06 14:35:51',0,3,'PR-6'),(7,16,14,'APPROVED','','2026-07-03 14:10:37','2026-07-06 14:35:51',0,2,'PR-7'),(8,16,14,'APPROVED','','2026-07-03 16:32:28','2026-07-06 14:35:51',0,3,'PR-8');
/*!40000 ALTER TABLE `purchase_requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_purchase_requests_code` AFTER INSERT ON `purchase_requests` FOR EACH ROW BEGIN
    UPDATE purchase_requests
    SET code = CONCAT('PR-', NEW.id)
    WHERE id = NEW.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rams`
--

DROP TABLE IF EXISTS `rams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `size` varchar(20) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rams`
--

LOCK TABLES `rams` WRITE;
/*!40000 ALTER TABLE `rams` DISABLE KEYS */;
INSERT INTO `rams` VALUES (1,'4GB',1),(2,'8GB',1),(3,'16GB',1),(4,'32GB',1),(5,'64GB',1),(6,'125GB',1);
/*!40000 ALTER TABLE `rams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `permissionid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleid` (`roleid`,`permissionid`),
  KEY `permissionid` (`permissionid`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permissionid`) REFERENCES `permissions` (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (72,2,1),(172,2,2),(115,2,4),(73,2,5),(74,2,6),(75,2,9),(76,2,12),(77,2,15),(78,2,18),(79,2,21),(80,2,24),(81,2,25),(82,2,26),(83,2,28),(84,2,29),(85,2,31),(143,2,32),(87,2,33),(88,2,36),(146,3,1),(173,3,2),(148,3,3),(149,3,4),(150,3,5),(151,3,6),(152,3,7),(153,3,8),(154,3,9),(155,3,10),(156,3,11),(157,3,12),(158,3,13),(159,3,14),(160,3,15),(161,3,16),(162,3,17),(163,3,18),(164,3,25),(165,3,26),(166,3,27),(167,3,28),(168,3,29),(169,3,30),(170,3,31),(171,3,32),(117,4,5),(118,4,6),(119,4,7),(120,4,8),(121,4,9),(122,4,10),(123,4,11),(124,4,12),(125,4,13),(126,4,14),(127,4,15),(128,4,16),(129,4,17),(130,4,18),(131,4,19),(132,4,20),(133,4,21),(134,4,22),(135,4,23),(136,4,31),(145,4,32),(137,4,33),(138,4,34),(139,4,35),(140,4,36),(141,4,37),(142,4,38);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN',1),(2,'MANAGER',1),(3,'WAREHOUSE_STAFF',1),(4,'SALESMAN',1),(5,'WAREHOUSE_PROCESSOR',0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roms`
--

DROP TABLE IF EXISTS `roms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `size` varchar(20) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roms`
--

LOCK TABLES `roms` WRITE;
/*!40000 ALTER TABLE `roms` DISABLE KEYS */;
INSERT INTO `roms` VALUES (1,'256GB',1),(2,'512GB',1),(3,'1TB',1),(4,'125GB',1);
/*!40000 ALTER TABLE `roms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_movement`
--

DROP TABLE IF EXISTS `stock_movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `stock_movement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `type` enum('INCREASED','DECREASED') NOT NULL,
  `reference_type` enum('INVENTORY_AUDIT','IMPORT','EXPORT') NOT NULL,
  `reference_id` int(11) DEFAULT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `productid` (`productid`),
  KEY `idx_stock_movement_reference` (`reference_type`,`reference_id`),
  CONSTRAINT `stock_movement_ibfk_1` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_movement`
--

LOCK TABLES `stock_movement` WRITE;
/*!40000 ALTER TABLE `stock_movement` DISABLE KEYS */;
INSERT INTO `stock_movement` VALUES (1,38,10,'INCREASED','IMPORT',1,'2026-06-30 20:37:22'),(2,34,5,'INCREASED','IMPORT',2,'2026-06-30 20:38:28'),(3,36,5,'INCREASED','IMPORT',2,'2026-06-30 20:38:28'),(4,22,5,'INCREASED','IMPORT',3,'2026-06-30 20:40:11'),(5,23,10,'INCREASED','IMPORT',3,'2026-06-30 20:40:11'),(6,22,5,'INCREASED','IMPORT',4,'2026-06-30 20:41:15'),(7,23,5,'DECREASED','EXPORT',1,'2026-06-30 20:55:27'),(8,22,5,'DECREASED','EXPORT',1,'2026-06-30 20:55:27'),(9,36,7,'INCREASED','IMPORT',5,'2026-06-30 21:04:27'),(10,33,5,'INCREASED','IMPORT',6,'2026-06-30 21:07:45'),(11,31,5,'INCREASED','IMPORT',6,'2026-06-30 21:07:46'),(12,22,5,'DECREASED','EXPORT',2,'2026-06-30 21:08:51'),(13,2,5,'INCREASED','INVENTORY_AUDIT',1,'2026-06-30 21:14:28'),(14,3,5,'INCREASED','INVENTORY_AUDIT',1,'2026-06-30 21:14:28'),(15,3,3,'DECREASED','EXPORT',3,'2026-07-02 18:02:34'),(16,2,2,'DECREASED','EXPORT',3,'2026-07-02 18:02:34'),(17,38,15,'INCREASED','IMPORT',7,'2026-07-03 14:14:50'),(18,27,10,'INCREASED','IMPORT',7,'2026-07-03 14:14:50'),(19,34,2,'DECREASED','EXPORT',4,'2026-07-03 14:16:56'),(20,38,5,'DECREASED','EXPORT',4,'2026-07-03 14:16:56'),(21,3,2,'DECREASED','EXPORT',5,'2026-07-03 17:18:40'),(22,2,2,'DECREASED','EXPORT',5,'2026-07-03 17:18:40'),(23,2,1,'INCREASED','INVENTORY_AUDIT',3,'2026-07-05 09:35:27');
/*!40000 ALTER TABLE `stock_movement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `suppliers` (
  `supplierid` int(11) NOT NULL AUTO_INCREMENT,
  `suppliername` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `address` text,
  `isactive` tinyint(1) DEFAULT '1',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`supplierid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'Công ty TNHH Công nghệ An Phát','0912345678','contact@anphat.com.vn','123 Thái Hà, Hà Nội',1,'2026-06-18 10:00:00','2026-06-18 10:00:00'),(2,'Cửa hàng Điện tử Phong Vũ','0987654321','sales@phongvu.vn','456 Nguyễn Thị Minh Khai, TP.HCM',1,'2026-06-18 10:05:00','2026-06-18 10:05:00'),(3,'Đại lý Phân phối Dell Việt Nam','0901112233','info@dell.com.vn','789 Lê Duẩn, Đà Nẵng',1,'2026-06-18 10:10:00','2026-06-18 10:10:00'),(4,'Công ty Cổ phần Thế Giới Số (Digiworld)','0933334444','partner@digiworld.com.vn','Tòa nhà Etown, Hà Nội',1,'2026-06-18 10:15:00','2026-06-18 10:15:00'),(5,'Công ty TNHH Phụ kiện Viễn thông FPT','0977778888','supplier@fpt.com.vn','KCN Đình Vũ, Hải Phòng',0,'2026-06-18 10:20:00','2026-06-18 10:20:00');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `units` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'Chiếc',1),(2,'Thanh',1),(3,'Ổ',1);
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `fullname` varchar(150) NOT NULL,
  `passwordhash` varchar(255) NOT NULL,
  `roleid` int(11) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`),
  KEY `roleid` (`roleid`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'manager01','Nguyen Thi Manager 1','manager123hash',2,'0900000002','manager@gmail.com','MALE',1,'Nguyen Thi','Manager'),(3,'staff01','Tran Van Staff','staff123hash',3,'0900000003','staff@gmail.com','MALE',1,'Tran Van','Staff'),(4,'customer01','Le Customer','customer123hash',4,'0900000004','customer@gmail.com','OTHER',0,'Le','Customer'),(5,'nam1','Nguyễn Tuấn Nam','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',5,'0982699381','emnam2k5@gmail.com','MALE',1,'Nguyễn Tuấn','Nam'),(6,'admin0','System Admin','$2a$05$ViJOaXoxE8h3Y1XxHZ5O0efAw9flQgH4pkX82AGi3aR3TOGJEiK8.',1,'0982699382','stdsddaff1@gmail.com','MALE',1,'System','Admin'),(7,'linh','Tran Phuong Linh','$2a$05$SSR/XL0QK7SXrPLdL8ki1uWs3IlfkYVuAaK8qDREBxelF7aM4hjoG',2,'0900000005','1243@gmail.com','MALE',0,'Tran Phuong','Linh'),(8,'meomeo123','Quang Hung MasterD','$2a$05$Ghax46XQdit.TPqhSoB.Ee9gYoBWZjvI.VRGEp0HxsNbObXgEUFa2',3,'0900000006','staff1@gmail.com','FEMALE',1,'Quang Hung','MasterD'),(9,'NamNT123','Nguyễn Thành Nam','$2a$12$HVFkUoHr/R2lUW9BHIWacOVC8vHh3AT.rpqU69ObsBHR2avbZKhWW',3,'0900000007','nam2k5@gmail.com','MALE',1,'Nguyễn Thành','Nam'),(11,'nam12','Quang Hung MasterD','$2a$12$nQfTxq2ybyDLlzkqwICpguBnuEKlk.ZQZ1Y4GPI4qoGihW6jB9U/a',3,'0912345678','staff12@gmail.com','MALE',1,'Test','Nguyen'),(12,'po122','Tran Duc Duy','$2a$12$UPOyr4qmHE.MDxfEGyq.aOAHWAUYpBeCa1UCl612dF.KIGa.hrF0y',4,'0900000089','admin12@gmail.com','MALE',1,'Duy','Tran Duc'),(13,'admin','Admin','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',1,'0900000010','audit_admin@gmail.com','MALE',1,'Audit','Admin'),(14,'manager','Manager','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',2,'0900000011','audit_manager@gmail.com','MALE',1,'Audit','Manager'),(15,'warehouse','Warehouse Staff','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',3,'0900000012','audit_staff@gmail.com','MALE',1,'Audit','Staff'),(16,'saleman','Saleman Staff','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',4,'0900000212','audsi_staff@gmail.com','MALE',1,'Nguyen Tuan','Nam'),(17,'tung','Tran Thanh Tung','$2a$12$Jt1ki.DA8zjeyJLU2EDTHeTlHDtd59S0m/sEQtf4h/IgefF4r0wuW',2,'0966244761','trthtung231@gmail.com','MALE',1,'Tung','Tran'),(18,'ducanh','Duc Anh','$2a$12$q3.HklFPX.EVDoGlhfF2EOTI99m/RyXZl4W2Z8hw0J5uN7WAPF7Ie',2,'0988888818','ducanh@gmail.com','MALE',1,'Duc','Anh');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'wms'
--

--
-- Dumping routines for database 'wms'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-11 20:29:36
