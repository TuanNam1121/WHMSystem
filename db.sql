CREATE DATABASE  IF NOT EXISTS `wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wms`;
-- MySQL dump 10.13  Distrib 8.0.46, for macos15 (arm64)
--
-- Host: localhost    Database: wms
-- ------------------------------------------------------
-- Server version	9.7.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '502e57a0-4e8e-11f1-9679-6cc69cbcda0f:1-2292';

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brandid` int NOT NULL AUTO_INCREMENT,
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

LOCK
TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands`
VALUES (1, 'Dell', 'assets/img/brands/1780372969174_dell.png', 'Dell products', '2026-05-27 11:27:22',
        '2026-06-02 11:02:49'),
       (2, 'Asus', 'assets/img/brands/1780372932909_asus.jpg', 'Asus products', '2026-05-27 11:27:22',
        '2026-06-02 11:02:13'),
       (3, 'Lenovo', 'assets/img/brands/1780373160690_lenovo.webp', 'Lenovo products', '2026-05-27 11:27:22',
        '2026-06-02 11:06:01'),
       (4, 'Kingston', 'assets/img/brands/1780373105062_kingston.svg', 'RAM', '2026-05-27 11:27:22',
        '2026-06-02 11:05:05'),
       (5, 'Samsung', 'assets/img/brands/1780373242971_samsung.png', 'SSD', '2026-05-27 11:27:22',
        '2026-06-02 11:07:23'),
       (6, 'MSI', 'assets/img/brands/1780373210201_msi.jpg',
        'MSI (Micro-Star International) là thương hiệu hàng đầu thế giới về giải pháp chơi game', '2026-05-31 00:00:00',
        '2026-06-02 11:06:50'),
       (7, 'Acer', 'assets/img/brands/1780373060442_acer.svg',
        'MSI (Micro-Star International) là thương hiệu hàng đầu ', '2026-06-02 00:00:00', '2026-06-02 11:04:20'),
       (8, 'prada', NULL, 'gucci', '2026-06-02 00:00:00', '2026-06-02 00:00:00');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `categoryid` int NOT NULL AUTO_INCREMENT,
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

LOCK
TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories`
VALUES (1, 'Laptop', 'Laptop devices', 1, '2026-05-27 11:27:22', '2026-05-27 11:27:22'),
       (2, 'RAM', 'Memory module', 1, '2026-05-27 11:27:22', '2026-05-27 11:27:22'),
       (3, 'ROM', 'Storage', 0, '2026-05-27 11:27:22', '2026-06-02 12:30:23'),
       (4, 'Laptop Gaming', 'Bao gồm các sản phẩm laptop liên quan chuyên cho gaming ', 1, '2026-06-01 20:32:50',
        '2026-06-01 20:32:50'),
       (6, 'Chip', ' ', 1, '2026-06-01 23:59:57', '2026-06-01 23:59:57'),
       (7, 'Laptop Work', '', 1, '2026-06-02 11:17:34', '2026-06-02 11:17:52'),
       (8, 'Laptop A', 'a', 1, '2026-06-02 12:30:12', '2026-06-02 12:34:10');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `chips`
--

DROP TABLE IF EXISTS `chips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chips` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chips`
--

LOCK
TABLES `chips` WRITE;
/*!40000 ALTER TABLE `chips` DISABLE KEYS */;
INSERT INTO `chips`
VALUES (1, 'Intel Core i5 12450H', 0),
       (2, 'Intel Core i7 13620H', 0),
       (3, 'AMD Ryzen 5 7530U', 1),
       (4, 'AMD Ryzen 7 8845HS', 1),
       (5, 's', 0);
/*!40000 ALTER TABLE `chips` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK
TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers`
VALUES (1, 'Nguyen Van A', '0988888888'),
       (2, 'Tran Thi B', '0977777777'),
       (3, 'Le Van C', '0966666666'),
       (4, 'Pham Thi D', '0955555555'),
       (5, 'Hoang Van E', '0944444444');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `good_receipts`
--

DROP TABLE IF EXISTS `good_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `purchaserequestid` int NOT NULL,
  `processedby` int NOT NULL,
  `status` enum('NEW','INCOMPLETED','COMPLETED') DEFAULT 'NEW',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `note` text,
  `invoice_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `purchaserequestid` (`purchaserequestid`),
  KEY `processedby` (`processedby`),
  CONSTRAINT `good_receipts_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
  CONSTRAINT `good_receipts_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK
TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts` DISABLE KEYS */;
INSERT INTO `good_receipts` VALUES (1,1,15,'COMPLETED','2026-06-22 16:56:50','2026-06-22 16:56:50',NULL,'0262206'),(3,3,15,'COMPLETED','2026-06-22 17:07:43','2026-06-22 17:07:43',NULL,'0234234');
/*!40000 ALTER TABLE `good_receipts` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `good_receipts_items`
--

DROP TABLE IF EXISTS `good_receipts_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goodreceiptid` int NOT NULL,
  `product_id` int NOT NULL,
  `actual_quantity` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `goodreceiptid` (`goodreceiptid`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `good_receipts_items_ibfk_1` FOREIGN KEY (`goodreceiptid`) REFERENCES `good_receipts` (`id`),
  CONSTRAINT `good_receipts_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts_items`
--

LOCK
TABLES `good_receipts_items` WRITE;
/*!40000 ALTER TABLE `good_receipts_items` DISABLE KEYS */;
INSERT INTO `good_receipts_items` VALUES (1,1,2,5,'2026-06-22 16:56:50'),(2,1,3,5,'2026-06-22 16:56:50'),(4,3,36,10,'2026-06-22 17:07:43'),(5,3,37,2,'2026-06-22 17:07:44');
/*!40000 ALTER TABLE `good_receipts_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `inventory_audit`
--

DROP TABLE IF EXISTS `inventory_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_audit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `createdby` int NOT NULL,
  `status` enum('DRAFT','CANCELLED','SUBMITTED','COMPLETED','PENDING','REJECTED') DEFAULT 'DRAFT',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `createdby` (`createdby`),
  CONSTRAINT `inventory_audit_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit`
--

LOCK
TABLES `inventory_audit` WRITE;
/*!40000 ALTER TABLE `inventory_audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory_audit` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `inventory_audit_item_serials`
--

DROP TABLE IF EXISTS `inventory_audit_item_serials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_audit_item_serials` (
  `id` int NOT NULL AUTO_INCREMENT,
  `audit_item_id` int NOT NULL,
  `serial` varchar(100) NOT NULL,
  `type` enum('ADD','DELETE') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_audit_item_serials_item` (`audit_item_id`),
  CONSTRAINT `fk_audit_item_serials_item` FOREIGN KEY (`audit_item_id`) REFERENCES `inventory_audit_items` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit_item_serials`
--

LOCK
TABLES `inventory_audit_item_serials` WRITE;
/*!40000 ALTER TABLE `inventory_audit_item_serials` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory_audit_item_serials` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `inventory_audit_items`
--

DROP TABLE IF EXISTS `inventory_audit_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_audit_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `auditid` int NOT NULL,
  `productid` int NOT NULL,
  `systemquantity` int DEFAULT NULL,
  `physicalquantity` int DEFAULT NULL,
  `discrepancy` int DEFAULT NULL,
  `reasons` text,
  PRIMARY KEY (`id`),
  KEY `auditid` (`auditid`),
  KEY `productid` (`productid`),
  CONSTRAINT `inventory_audit_items_ibfk_1` FOREIGN KEY (`auditid`) REFERENCES `inventory_audit` (`id`),
  CONSTRAINT `inventory_audit_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_audit_items`
--

LOCK
TABLES `inventory_audit_items` WRITE;
/*!40000 ALTER TABLE `inventory_audit_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory_audit_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `models` (
  `modelid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `brandid` int NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`modelid`),
  KEY `brandid` (`brandid`),
  CONSTRAINT `models_ibfk_1` FOREIGN KEY (`brandid`) REFERENCES `brands` (`brandid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK
TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models`
VALUES (1, 'Inspiron 15', 1, 1),
       (2, 'TUF A15', 2, 1),
       (3, 'Thinkpad E14', 3, 1),
       (4, 'Fury Beast', 4, 1),
       (5, '970 EVO', 5, 1),
       (6, 'TUF A9', 2, 1),
       (7, 'TP3402VA', 2, 1),
       (8, 'RV037W', 2, 1),
       (9, '15ARP10E', 3, 1),
       (10, 'K3605VC', 2, 1),
       (11, 'ProPanel', 7, 1),
       (12, 'M1502NAQ', 2, 1),
       (13, 'Lite 16 GEN 2', 7, 1);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderid` int NOT NULL,
  `productid` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderid` (`orderid`),
  KEY `productid` (`productid`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK
TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `order_items_product_items`
--

DROP TABLE IF EXISTS `order_items_product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items_product_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderitemid` int NOT NULL,
  `productitemid` int NOT NULL,
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

LOCK
TABLES `order_items_product_items` WRITE;
/*!40000 ALTER TABLE `order_items_product_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items_product_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` enum('NEW','DOING','COMPLETED','CANCELLED') DEFAULT 'NEW',
  `total_price` decimal(15,2) DEFAULT NULL,
  `note` text,
  `orderdate` datetime DEFAULT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completedat` datetime DEFAULT NULL,
  `createdby` int NOT NULL,
  `processedby` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `createdby` (`createdby`),
  KEY `processedby` (`processedby`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK
TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_resets` (
  `requestid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `status` enum('NEW','COMPLETED') DEFAULT 'NEW',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `completedat` datetime DEFAULT NULL,
  PRIMARY KEY (`requestid`),
  KEY `userid` (`userid`),
  CONSTRAINT `password_resets_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_resets`
--

LOCK
TABLES `password_resets` WRITE;
/*!40000 ALTER TABLE `password_resets` DISABLE KEYS */;
INSERT INTO `password_resets`
VALUES (1, 2, 'COMPLETED', '2026-05-16 22:29:21', NULL),
       (2, 4, 'COMPLETED', '2026-05-14 22:29:21', '2026-05-16 22:29:21'),
       (3, 5, 'COMPLETED', '2026-05-17 21:56:57', '2026-05-17 21:57:18'),
       (4, 5, 'COMPLETED', '2026-05-18 19:19:15', '2026-05-18 19:19:53'),
       (5, 5, 'COMPLETED', '2026-05-18 22:07:08', '2026-05-18 22:08:02'),
       (6, 5, 'COMPLETED', '2026-05-21 09:56:32', '2026-05-21 09:57:27'),
       (7, 5, 'COMPLETED', '2026-05-22 12:52:55', '2026-05-22 12:53:45'),
       (8, 5, 'COMPLETED', '2026-05-29 14:19:56', '2026-05-29 14:20:57'),
       (9, 5, 'NEW', '2026-06-02 11:21:16', NULL);
/*!40000 ALTER TABLE `password_resets` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `permissionid` int NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK
TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions`
VALUES (1, 'VIEWUSER', 'Xem thong tin user'),
       (2, 'CREATE_USER', 'Can create new users'),
       (3, 'UPDATE_USER', 'Can update user information'),
       (4, 'DELETE_USER', 'Can delete users'),
       (5, 'VIEW_REQUEST', 'Can view requests'),
       (6, 'HANDLE_REQUEST', 'Can complete requests'),
       (7, 'test_first', 'tet tet tet'),
       (8, 'IMPORT_PRODUCT', 'Can import products'),
       (9, 'EXPORT_PRODUCT', 'Can export products'),
       (10, 'AUDIT_INVENTORY', 'Can audit warehouse'),
       (11, 'CREATE_PURCHASE_REQUEST', 'Create purchase request'),
       (12, 'APPROVE_PURCHASE_REQUEST', 'Approve purchase request'),
       (13, 'VIEW_STOCK', 'View stock'),
       (14, 'PROCESS_GOODS_RECEIPT', 'Process goods receipt'),
       (15, 'VIEW_INVENTORY_AUDIT', 'Can view inventory audits'),
       (16, 'CREATE_INVENTORY_AUDIT', 'Can create inventory audits'),
       (17, 'PERFORM_INVENTORY_AUDIT', 'Can perform inventory audits'),
       (19, 'APPROVE_INVENTORY_AUDIT', 'Can approve or decline inventory audits');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `product_items`
--

DROP TABLE IF EXISTS `product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `serial` varchar(100) DEFAULT NULL,
  `imported_price` decimal(15,2) DEFAULT NULL,
  `export_price` decimal(15,2) DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  `imported_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `product_id` int NOT NULL,
  `goodreceiptsitemid` int DEFAULT NULL,
  `status` enum('AVAILABLE','UNAVAILABLE','SOLD') DEFAULT 'AVAILABLE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `serial` (`serial`),
  KEY `product_id` (`product_id`),
  KEY `goodreceiptsitemid` (`goodreceiptsitemid`),
  CONSTRAINT `product_items_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`),
  CONSTRAINT `product_items_ibfk_2` FOREIGN KEY (`goodreceiptsitemid`) REFERENCES `good_receipts_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_items`
--

LOCK
TABLES `product_items` WRITE;
/*!40000 ALTER TABLE `product_items` DISABLE KEYS */;
INSERT INTO `product_items` VALUES (1,'7K9B2X4W1P',15000000.00,NULL,1,'2026-06-22 16:56:50',2,1,'AVAILABLE'),(2,'M3R8V6N2Q5',15000000.00,NULL,1,'2026-06-22 16:56:50',2,1,'AVAILABLE'),(3,'Z5X1C9V7B3',15000000.00,NULL,1,'2026-06-22 16:56:50',2,1,'AVAILABLE'),(4,'P9L2K8M4J6',15000000.00,NULL,1,'2026-06-22 16:56:50',2,1,'AVAILABLE'),(5,'F3D7S1A9H2',15000000.00,NULL,1,'2026-06-22 16:56:50',2,1,'AVAILABLE'),(6,'9X3V7B2N',12000000.00,NULL,1,'2026-06-22 16:56:50',3,2,'AVAILABLE'),(7,'M4C8Z1P6',12000000.00,NULL,1,'2026-06-22 16:56:50',3,2,'AVAILABLE'),(8,'L2K7J3H9',12000000.00,NULL,1,'2026-06-22 16:56:50',3,2,'AVAILABLE'),(9,'G5F1D8S4',12000000.00,NULL,1,'2026-06-22 16:56:50',3,2,'AVAILABLE'),(10,'A9Q3W7E1',12000000.00,NULL,1,'2026-06-22 16:56:50',3,2,'AVAILABLE'),(12,'K9B7X2W4M1',15000000.00,NULL,1,'2026-06-22 17:07:43',36,4,'AVAILABLE'),(13,'3Z6P9R1V5Q',15000000.00,NULL,1,'2026-06-22 17:07:43',36,4,'AVAILABLE'),(14,'H8N2Y7L4F3',15000000.00,NULL,1,'2026-06-22 17:07:43',36,4,'AVAILABLE'),(15,'V5D1K8M9P2',15000000.00,NULL,1,'2026-06-22 17:07:43',36,4,'AVAILABLE'),(16,'7X3W6B2R1T',15000000.00,NULL,1,'2026-06-22 17:07:43',36,4,'AVAILABLE'),(17,'L4F9N3S7Q8',15000000.00,NULL,1,'2026-06-22 17:07:44',36,4,'AVAILABLE'),(18,'2M5P1V8K9Z',15000000.00,NULL,1,'2026-06-22 17:07:44',36,4,'AVAILABLE'),(19,'G7Y3H6L2F4',15000000.00,NULL,1,'2026-06-22 17:07:44',36,4,'AVAILABLE'),(20,'9R1T5W8B2X',15000000.00,NULL,1,'2026-06-22 17:07:44',36,4,'AVAILABLE'),(21,'S4Q8N3M7P1',15000000.00,NULL,1,'2026-06-22 17:07:44',36,4,'AVAILABLE'),(22,'6V2K9Z5D1R',16000000.00,NULL,1,'2026-06-22 17:07:44',37,5,'AVAILABLE'),(23,'F3L7H4Y2G8',16000000.00,NULL,1,'2026-06-22 17:07:44',37,5,'AVAILABLE');
/*!40000 ALTER TABLE `product_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` text,
  `img_url` varchar(500) DEFAULT NULL,
  `total_quantity` int DEFAULT '0',
  `isactive` tinyint(1) DEFAULT '1',
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ramid` int DEFAULT NULL,
  `romid` int DEFAULT NULL,
  `chipid` int DEFAULT NULL,
  `unitid` int NOT NULL,
  `categoryid` int NOT NULL,
  `brandid` int NOT NULL,
  `modelid` int DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK
TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Laptop Dell 14 DC14250','Office ','assets/img/product/1780336108147_ssss_1_125.webp',0,0,'2026-05-27 11:27:22','2026-06-22 08:41:06',2,2,1,1,1,1,1,'D15-23'),(2,'Asus TUF Gaming T12','Gaming updated','assets/img/product/1780327162854_laptopleveno.jpg',5,1,'2026-05-27 11:27:22','2026-06-22 17:19:55',4,3,2,1,1,2,2,'A12-53'),(3,'ThinkPad E14 A12-54','Business laptop updated','assets/img/product/1780327242054_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg',5,1,'2026-05-27 11:27:22','2026-06-22 17:19:55',3,2,3,1,1,3,3,'A12-54'),(4,'Kingston Fury 16GB 32000MHZ','RAM DDR4 Tesst update','assets/img/product/1780327214479_ktc-hero-ddr5-overview-lg.jpg',0,1,'2026-05-27 11:27:22','2026-06-22 08:41:06',3,NULL,NULL,2,2,4,NULL,'B12-423'),(5,'Samsung 970 EVO 512GB','SSD ass','assets/img/product/1780365511731_ssss_1_125.webp',0,1,'2026-05-27 11:27:22','2026-06-22 08:41:06',NULL,2,NULL,3,3,5,NULL,'B12-45'),(8,'Asus TUF 7 ','con mèo kêu làm sao','assets/img/product/1780327319228_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg',0,1,'2026-05-28 23:29:54','2026-06-01 22:21:59',2,2,2,1,1,2,2,'B32-12'),(10,'Asus TUF 13','meomeo aa','assets/img/product/1780327333216_text_ng_n_2__9_31.webp',0,1,'2026-05-29 13:32:34','2026-06-02 11:50:05',1,2,2,1,1,7,11,'C21-23'),(13,'Laptop HP 15-fd1289TU Ultra 7','','assets/img/product/1780327353097_laptopleveno.jpg',0,1,'2026-05-30 16:12:45','2026-06-02 11:24:31',3,2,1,1,4,2,8,'Generatorc412'),(14,'Laptop ASUS Vivobook S 14 FLIP','Laptop ASUS Vivobook S 14 Flip TP3402VA-LZ632W sở hữu bộ CPU Intel Core i5-13420H đi cùng RAM 16GB chuẩn DDR4, cộng thêm ổ cứng 512GB M.2 PCIe 4.0. Mẫu laptop ASUS Vivobook này được trang bị màn hình có độ phân giải WUXGA với kích thước 14 inch. Bên cạnh đó, thiết kế Flip còn cho phép người dùng chuyển đổi laptop và tablet theo nhu cầu.','assets/img/product/1780334374996_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg',0,1,'2026-05-31 00:18:47','2026-06-02 08:55:59',2,3,3,1,1,2,7,'LZ632W'),(15,'Laptop ASUS TUF Gaming F16 FX608JHR','Laptop ASUS TUF Gaming F16 FX608JHR-RV037W được tích hợp một CPU Intel Core i7-14650HX 5.2 GHz, cùng với RAM 16GB và có bộ nhớ SSD lớn lên đến 1TB. Máy còn sở hữu VGA NVIDIA GeForce RTX 8GB kết hợp màn hình FHD+ 16 inch và có tần số quét 165Hz. Máy có màu Jaeger Gray, chỉ nặng 2.2 kg, với pin 90WHrs, phù hợp cho chơi game và làm.','assets/img/product/1780299967530_laptopleveno.jpg',0,1,'2026-05-31 11:20:01','2026-06-01 14:46:07',4,2,2,1,1,2,8,'RV037W'),(16,'Kingston Fury 16GB','RAM DDR4','assets/img/product/1780322903782_kingston_8g_3200_compressed_1.webp',0,1,'2026-05-31 22:00:03','2026-06-02 09:31:47',NULL,3,NULL,2,3,4,NULL,'KVR32S22S6/4'),(17,'SSD Samsung 123 120000MHz','RAM DDR4 SSD Samsung 123 120000MHz','assets/img/product/1780361796964_ssss_1_125.webp',0,1,'2026-05-31 22:24:24','2026-06-22 08:41:06',3,NULL,NULL,2,2,4,NULL,'KVR32S22S'),(18,'RAM Laptop Kingston 4 GB-DDR4-3200 MHz','con mèo kêu','assets/img/product/1780247214567_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg',0,1,'2026-05-31 22:39:51','2026-06-02 08:23:16',2,2,NULL,1,3,4,NULL,'KVR32S22S6'),(20,'RAM Laptop Kingston Sodimm 1.2V','RAM Laptop Kingston Sodimm 1.2V 16GB 3200MHz CL22','assets/img/product/1780244875864_kingston_8g_3200_compressed_1.webp',0,1,'2026-05-31 23:27:55','2026-06-02 09:22:02',2,3,NULL,1,3,4,NULL,'CL22'),(22,'SSD Samsung 123','RAM DDR4','assets/img/product/1780246656870_text_ng_n_15_21_1.webp',0,1,'2026-05-31 23:57:36','2026-05-31 23:57:36',3,NULL,NULL,2,2,4,NULL,'KVR32S2'),(23,'Ổ cứng HDD Enterprise WD Ultrastar DC HC330 10TB 3.5','Ổ cứng HDD WD Ultrastar DC HC330 10TB là một ổ cứng dành cho doanh nghiệp với nhiều tính năng nổi bật. Ổ cứng này đáp ứng nhu cầu lưu trữ, tốc độ truy cập dữ liệu, độ bền và ổn định cao của các doanh nghiệp.\r\n\r\n','assets/img/product/1780246828025_05-hdd-enterprise-wd-ultrastar-dc-hc330-10tb-35-01.jpg',0,1,'2026-06-01 00:00:28','2026-06-02 09:01:58',NULL,3,NULL,3,3,5,NULL,'WUS721010ALE6L4'),(25,'Laptop ASUS Gaming Vivobook 16X K3605VC-RP431W','Laptop ASUS Vivobook 16X K3605VC-RP431W được trang bị vi xử lý Intel Core i5-13420H cho ra hiệu suất ổn định từ công việc cho đến nhu cầu giải trí. Mẫu ASUS Vivobook Gaming này có màn hình lên đến 16 inch cùng tần số quét 144Hz cung cấp hình ảnh sắc nét không bị vỡ. Card đồ hoạ NVIDIA GeForce RTX 3050 4GB GDDR6 là một điểm cộng lớn của dòng laptop này.','assets/img/product/1780333162727_text_ng_n_5__9_130.webp',0,1,'2026-06-01 23:59:22','2026-06-02 12:16:17',3,2,2,1,4,1,1,'RP431W'),(26,'CPU AMD Ryzen 7 7800X3D (Tray)','asa','assets/img/product/1780333276897_cpu-amd-ryzen-7-7800x3d_2__3.webp',0,1,'2026-06-02 00:01:16','2026-06-02 11:10:33',3,2,1,3,2,7,11,'7800X3D '),(27,'RAM Laptop Kingston 4-3200 MHz','asss','assets/img/product/1780333845552_text_ng_n_5__9_130.webp',0,1,'2026-06-02 00:10:35','2026-06-02 00:10:45',3,3,2,1,4,2,6,'R2341D'),(28,'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR','Laptop Acer Gaming Nitro V 15 ProPanel ANV15-41-R7CR sở hữu cấu hình mạnh mẽ với CPU AMD Ryzen 5 7535HS and card đồ họa NVIDIA GeForce RTX 4050 6GB GDDR6. Màn hình 15.6 inch FHD IPS and tần số quét 180Hz đem lại hình ảnh sắc nét. Ổ cứng 512GB PCIe NVMe SSD and RAM 16GB DDR5, với hệ thống tản nhiệt Dual-fan đảm bảo hiệu suất tối ưu.\r\n\r\n','assets/img/product/1780334956211_sssxs_26.png',0,1,'2026-06-02 00:29:05','2026-06-02 00:45:15',2,2,2,1,1,7,11,'ANV15-41'),(29,'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR','','assets/img/product/1780336413334_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png',0,1,'2026-06-02 00:31:32','2026-06-02 00:53:33',2,2,2,1,1,7,11,'ANV15-41-R5AS'),(30,'RAM Laptop Transcend DDR5 4800MHz 16GB','Ram laptop','assets/img/product/1780335486900_ram-transcend-ddr5-4800mhz-16gb_1_.webp',0,1,'2026-06-02 00:38:06','2026-06-02 00:38:06',3,NULL,NULL,1,2,5,NULL,'R.TC.04'),(31,'RAM Laptop Transcend DDR5 4800MHz 16GB','','assets/img/product/1780335748931_ram-transcend-ddr5-4800mhz-16gb_1_.webp',0,1,'2026-06-02 00:42:28','2026-06-02 00:42:28',NULL,2,NULL,2,3,2,NULL,'R.TC.'),(32,'CPU AMD Ryzen 5 5500','','assets/img/product/1780336054684_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png',0,0,'2026-06-02 00:47:00','2026-06-02 00:47:34',3,2,3,1,6,7,11,'CPU.AM.09'),(33,'Laptop ASUS VivoBook 15','aa','assets/img/product/1780364201177_ssss_2_42.png',0,1,'2026-06-02 08:36:41','2026-06-02 12:16:35',2,1,3,1,1,1,1,'BQ021W'),(34,'Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU','Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU sở hữu màn hình 16 inch Full HD+, RAM 16GB DDR5 tốc độ 4800MHz (hỗ trợ nâng cấp tối đa 64GB). Chiếc laptop Acer Aspire được trang bị Intel Core i7-1355U, card đồ họa Intel Iris Xe, cùng loa Stereo and webcam Full HD. Thiết kế gọn nhẹ 1.7kg, tích hợp đầy đủ cổng kết nối giúp sử dụng linh hoạt.','assets/img/product/1780375469602_text_ng_n_6__2_234.png',0,1,'2026-06-02 11:44:29','2026-06-02 12:17:03',3,2,2,1,7,7,13,'AL16-52P-76DU'),(35,'Laptop ASUS Vivobook S14 S3407VA','Laptop ASUS Vivobook S14 S3407VA-LY146W trang bị vi xử lý Intel Core 5 210H, RAM 16GB DDR5, SSD 512GB cùng với màn hình 14 inch WUXGA sắc nét, chân thực. Máy có thiết kế mỏng nhẹ chỉ 1.4kg, vỏ kim loại bền bỉ, pin lớn 70Wh cho thời gian sử dụng dài. Hỗ trợ Wi-Fi 6, camera IR nhận diện khuôn mặt and bàn phím có đèn nền tích hợp phím Copilot.\r\n','assets/img/product/1780378095512_text_ng_n_4__8_52.webp',0,0,'2026-06-02 12:28:15','2026-06-02 12:28:55',3,4,4,1,1,2,8,'LY146W'),(36,'Laptop Acer Gaming Aspire 7 A715-59G-57TU','Laptop Acer Gaming Aspire 7 A715-59G-57TU được trang bị vi xử lý Intel Core i5-12450H cân trơn tru mọi tác vụ từ văn phòng cho đến chơi game nặng. Hỗ trợ cho vi xử lý là card đồ hoạ RTX 3050 6GB giúp chơi game nặng mượt mà hơn. Người dùng có thể mở nhiều nội dung hiển thị cùng một lúc với màn hình lên đến 15.6 inch.\r\n\r\n','assets/img/product/1780379484406_text_ng_n_14__9_26.webp',10,1,'2026-06-02 12:51:24','2026-06-22 17:19:55',3,2,3,1,4,7,13,'A715-59G-57TU'),(37,'Laptop Acer Aspire Lite 15 AL15-46P-R73C','Laptop Acer Aspire Lite 15 AL15-46P-R73C sở hữu hiệu năng ấn tượng nhờ chip AMD Ryzen 3 5400U, RAM 8GB DDR4, cùng bộ nhớ trong SSD 512GB rộng rãi. Máy có màn hình Full HD 15.6 inch, tần số quét 60Hz trong thân máy chỉ 1.45kg. Laptop có pin 53Wh and hệ thống cổng đa dạng gồm: USB-C, USB-A and HDMI.\r\n\r\n','assets/img/product/1780379586909_sssxs_1__9.webp',2,1,'2026-06-02 12:53:06','2026-06-22 17:19:55',3,2,3,1,4,7,13,'AL15-46P-R73C');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `purchase_request_items`
--

DROP TABLE IF EXISTS `purchase_request_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_request_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `purchaserequestid` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `purchaserequestid` (`purchaserequestid`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `purchase_request_items_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
  CONSTRAINT `purchase_request_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_request_items`
--

LOCK
TABLES `purchase_request_items` WRITE;
/*!40000 ALTER TABLE `purchase_request_items` DISABLE KEYS */;
INSERT INTO `purchase_request_items` VALUES (1,1,2,5,15000000.00,0),(2,1,3,5,12000000.00,0),(3,2,4,5,500000.00,0),(4,2,17,5,200000.00,0),(5,3,36,10,15000000.00,0),(6,3,37,10,16000000.00,0),(7,4,5,10,2500000.00,0);
/*!40000 ALTER TABLE `purchase_request_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `purchase_requests`
--

DROP TABLE IF EXISTS `purchase_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `createdby` int NOT NULL,
  `approvedby` int DEFAULT NULL,
  `status` enum('NEW','APPROVED','REJECTED','PROCESSING','COMPLETED') DEFAULT 'NEW',
  `note` text,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `supplierid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `createdby` (`createdby`),
  KEY `approvedby` (`approvedby`),
  KEY `purchase_requests_ibfk_3` (`supplierid`),
  CONSTRAINT `purchase_requests_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
  CONSTRAINT `purchase_requests_ibfk_2` FOREIGN KEY (`approvedby`) REFERENCES `users` (`userid`),
  CONSTRAINT `purchase_requests_ibfk_3` FOREIGN KEY (`supplierid`) REFERENCES `suppliers` (`supplierid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_requests`
--

LOCK
TABLES `purchase_requests` WRITE;
/*!40000 ALTER TABLE `purchase_requests` DISABLE KEYS */;
INSERT INTO `purchase_requests` VALUES (1,16,14,'COMPLETED','Đơn hàng mới cần mua','2026-06-22 16:48:24','2026-06-22 16:56:51',0,2),(2,16,14,'APPROVED','Nhập 1 lô ram mới','2026-06-22 16:49:31','2026-06-22 16:52:05',0,2),(3,16,14,'APPROVED','Nhập Lô Laptop Gaming','2026-06-22 16:50:33','2026-06-22 16:52:12',0,4),(4,16,NULL,'REJECTED','Lô sản phẩm ROM giá rẻ','2026-06-22 16:51:38','2026-06-22 16:52:16',0,2);
/*!40000 ALTER TABLE `purchase_requests` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `rams`
--

DROP TABLE IF EXISTS `rams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rams` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size` varchar(20) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rams`
--

LOCK
TABLES `rams` WRITE;
/*!40000 ALTER TABLE `rams` DISABLE KEYS */;
INSERT INTO `rams`
VALUES (1, '4GB', 1),
       (2, '8GB', 1),
       (3, '16GB', 1),
       (4, '32GB', 1),
       (5, '64GB', 1),
       (6, '125GB', 1);
/*!40000 ALTER TABLE `rams` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleid` int NOT NULL,
  `permissionid` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleid` (`roleid`,`permissionid`),
  KEY `permissionid` (`permissionid`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permissionid`) REFERENCES `permissions` (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK
TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission`
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 1, 5),
       (6, 1, 6),
       (7, 1, 8),
       (8, 1, 9),
       (9, 1, 10),
       (10, 1, 11),
       (11, 1, 12),
       (12, 1, 13),
       (13, 1, 14),
       (33, 2, 1),
       (34, 2, 5),
       (35, 2, 6),
       (36, 2, 10),
       (37, 2, 11),
       (38, 2, 12),
       (44, 2, 15),
       (40, 2, 16),
       (41, 2, 19),
       (20, 3, 8),
       (21, 3, 9),
       (22, 3, 13),
       (23, 3, 14),
       (45, 3, 15),
       (43, 3, 17),
       (46, 4, 15);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `roleid` int NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK
TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles`
VALUES (1, 'ADMIN', 1),
       (2, 'MANAGER', 1),
       (3, 'WAREHOUSE_STAFF', 1),
       (4, 'SALESMAN', 1),
       (5, 'WAREHOUSE_PROCESSOR', 0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `roms`
--

DROP TABLE IF EXISTS `roms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `size` varchar(20) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `size` (`size`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roms`
--

LOCK
TABLES `roms` WRITE;
/*!40000 ALTER TABLE `roms` DISABLE KEYS */;
INSERT INTO `roms`
VALUES (1, '256GB', 1),
       (2, '512GB', 1),
       (3, '1TB', 1),
       (4, '125GB', 1);
/*!40000 ALTER TABLE `roms` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `stock_movement`
--

DROP TABLE IF EXISTS `stock_movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_movement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `productid` int NOT NULL,
  `quantity` int NOT NULL,
  `type` enum('INCREASED','DECREASED') NOT NULL,
  `reference_type` enum('INVENTORY_AUDIT','IMPORT','EXPORT') NOT NULL,
  `createdat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `productid` (`productid`),
  CONSTRAINT `stock_movement_ibfk_1` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_movement`
--

LOCK
TABLES `stock_movement` WRITE;
/*!40000 ALTER TABLE `stock_movement` DISABLE KEYS */;
INSERT INTO `stock_movement` VALUES (1,2,5,'INCREASED','IMPORT','2026-06-22 16:56:50'),(2,3,5,'INCREASED','IMPORT','2026-06-22 16:56:51'),(3,36,10,'INCREASED','IMPORT','2026-06-22 17:07:44'),(4,37,2,'INCREASED','IMPORT','2026-06-22 17:07:44');
/*!40000 ALTER TABLE `stock_movement` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supplierid` int NOT NULL AUTO_INCREMENT,
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

LOCK
TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers`
VALUES (1, 'Công ty TNHH Công nghệ An Phát', '0912345678', 'contact@anphat.com.vn', '123 Thái Hà, Hà Nội', 1,
        '2026-06-18 10:00:00', '2026-06-18 10:00:00'),
       (2, 'Cửa hàng Điện tử Phong Vũ', '0987654321', 'sales@phongvu.vn', '456 Nguyễn Thị Minh Khai, TP.HCM', 1,
        '2026-06-18 10:05:00', '2026-06-18 10:05:00'),
       (3, 'Đại lý Phân phối Dell Việt Nam', '0901112233', 'info@dell.com.vn', '789 Lê Duẩn, Đà Nẵng', 1,
        '2026-06-18 10:10:00', '2026-06-18 10:10:00'),
       (4, 'Công ty Cổ phần Thế Giới Số (Digiworld)', '0933334444', 'partner@digiworld.com.vn', 'Tòa nhà Etown, Hà Nội',
        1, '2026-06-18 10:15:00', '2026-06-18 10:15:00'),
       (5, 'Công ty TNHH Phụ kiện Viễn thông FPT', '0977778888', 'supplier@fpt.com.vn', 'KCN Đình Vũ, Hải Phòng', 0,
        '2026-06-18 10:20:00', '2026-06-18 10:20:00');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `isactive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK
TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units`
VALUES (1, 'Chiếc', 1),
       (2, 'Thanh', 1),
       (3, 'Ổ', 1);
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `fullname` varchar(150) NOT NULL,
  `passwordhash` varchar(255) NOT NULL,
  `roleid` int NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK
TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'manager01','Nguyen Thi Manager 1','manager123hash',2,'0900000002','manager@gmail.com','MALE',1,'Nguyen Thi','Manager'),(3,'staff01','Tran Van Staff','staff123hash',3,'0900000003','staff@gmail.com','MALE',1,'Tran Van','Staff'),(4,'customer01','Le Customer','customer123hash',4,'0900000004','customer@gmail.com','OTHER',0,'Le','Customer'),(5,'nam1','Nguyễn Tuấn Nam','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',5,'0982699381','emnam2k5@gmail.com','MALE',1,'Nguyễn Tuấn','Nam'),(6,'admin0','System Admin','$2a$05$ViJOaXoxE8h3Y1XxHZ5O0efAw9flQgH4pkX82AGi3aR3TOGJEiK8.',1,'0982699382','stdsddaff1@gmail.com','MALE',1,'System','Admin'),(7,'linh','Tran Phuong Linh','$2a$05$SSR/XL0QK7SXrPLdL8ki1uWs3IlfkYVuAaK8qDREBxelF7aM4hjoG',2,'0900000005','1243@gmail.com','MALE',0,'Tran Phuong','Linh'),(8,'meomeo123','Quang Hung MasterD','$2a$05$Ghax46XQdit.TPqhSoB.Ee9gYoBWZjvI.VRGEp0HxsNbObXgEUFa2',3,'0900000006','staff1@gmail.com','FEMALE',1,'Quang Hung','MasterD'),(9,'NamNT123','Nguyễn Thành Nam','$2a$12$HVFkUoHr/R2lUW9BHIWacOVC8vHh3AT.rpqU69ObsBHR2avbZKhWW',3,'0900000007','nam2k5@gmail.com','MALE',1,'Nguyễn Thành','Nam'),(11,'nam12','Quang Hung MasterD','$2a$12$nQfTxq2ybyDLlzkqwICpguBnuEKlk.ZQZ1Y4GPI4qoGihW6jB9U/a',3,'0912345678','staff12@gmail.com','MALE',1,'Test','Nguyen'),(12,'po122','Tran Duc Duy','$2a$12$UPOyr4qmHE.MDxfEGyq.aOAHWAUYpBeCa1UCl612dF.KIGa.hrF0y',4,'0900000089','admin12@gmail.com','MALE',1,'Duy','Tran Duc'),(13,'admin','Admin','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',1,'0900000010','audit_admin@gmail.com','MALE',1,'Audit','Admin'),(14,'manager','Manager','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',2,'0900000011','audit_manager@gmail.com','MALE',1,'Audit','Manager'),(15,'warehouse','Warehouse Staff','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',3,'0900000012','audit_staff@gmail.com','MALE',1,'Audit','Staff'),(16,'saleman','Saleman Staff','$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG',4,'0900000212','audsi_staff@gmail.com','MALE',1,'Audist','Staff'),(17,'tung','Tran Thanh Tung','$2a$12$Jt1ki.DA8zjeyJLU2EDTHeTlHDtd59S0m/sEQtf4h/IgefF4r0wuW',2,'0966244761','trthtung231@gmail.com','MALE',1,'Tung','Tran');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-22 17:35:33
