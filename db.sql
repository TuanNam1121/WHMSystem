DROP DATABASE IF EXISTS `wms`;

CREATE
DATABASE `wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE
`wms`;
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

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands`
(
    `brandid`     int          NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `img_url`     varchar(500) DEFAULT NULL,
    `description` text,
    `createdat`   datetime     DEFAULT CURRENT_TIMESTAMP,
    `updatedat`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
CREATE TABLE `categories`
(
    `categoryid`  int          NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `description` text,
    `isactive`    tinyint(1) DEFAULT '1',
    `createdat`   datetime DEFAULT CURRENT_TIMESTAMP,
    `updatedat`   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
CREATE TABLE `chips`
(
    `id`       int          NOT NULL AUTO_INCREMENT,
    `name`     varchar(100) NOT NULL,
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
CREATE TABLE `customers`
(
    `id`    int          NOT NULL AUTO_INCREMENT,
    `name`  varchar(150) NOT NULL,
    `phone` varchar(20)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
       (5, 'Hoang Van E', '0944444444'),
       (6, 'CellphoneS Thai Ha', '0123321123');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `export_receipt_details`
--

DROP TABLE IF EXISTS `export_receipt_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `export_receipt_details`
(
    `id`                int NOT NULL AUTO_INCREMENT,
    `export_receipt_id` int NOT NULL,
    `order_item_id`     int NOT NULL,
    `product_id`        int NOT NULL,
    `quantity`          int NOT NULL,
    `unit_price`        decimal(15, 2) DEFAULT NULL,
    `created_at`        datetime       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_export_receipt_order_item` (`export_receipt_id`,`order_item_id`),
    KEY                 `order_item_id` (`order_item_id`),
    KEY                 `product_id` (`product_id`),
    CONSTRAINT `fk_export_receipt_details_order_item` FOREIGN KEY (`order_item_id`) REFERENCES `order_items` (`id`),
    CONSTRAINT `fk_export_receipt_details_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`),
    CONSTRAINT `fk_export_receipt_details_receipt` FOREIGN KEY (`export_receipt_id`) REFERENCES `export_receipts` (`id`),
    CONSTRAINT `chk_export_receipt_details_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipt_details`
--

LOCK
TABLES `export_receipt_details` WRITE;
/*!40000 ALTER TABLE `export_receipt_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `export_receipt_details` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `export_receipt_serials`
--

DROP TABLE IF EXISTS `export_receipt_serials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `export_receipt_serials`
(
    `id`                       int NOT NULL AUTO_INCREMENT,
    `export_receipt_detail_id` int NOT NULL,
    `product_item_id`          int NOT NULL,
    `created_at`               datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_export_receipt_serial_once` (`product_item_id`),
    KEY                        `export_receipt_detail_id` (`export_receipt_detail_id`),
    CONSTRAINT `fk_export_receipt_serials_detail` FOREIGN KEY (`export_receipt_detail_id`) REFERENCES `export_receipt_details` (`id`),
    CONSTRAINT `fk_export_receipt_serials_product_item` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipt_serials`
--

LOCK
TABLES `export_receipt_serials` WRITE;
/*!40000 ALTER TABLE `export_receipt_serials` DISABLE KEYS */;
/*!40000 ALTER TABLE `export_receipt_serials` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `export_receipts`
--

DROP TABLE IF EXISTS `export_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `export_receipts`
(
    `id`          int         NOT NULL AUTO_INCREMENT,
    `code`        varchar(50) NOT NULL,
    `order_id`    int         NOT NULL,
    `status`      enum('DRAFT','COMPLETED') NOT NULL DEFAULT 'DRAFT',
    `note`        text,
    `created_by`  int         NOT NULL,
    `exported_by` int      DEFAULT NULL,
    `created_at`  datetime DEFAULT CURRENT_TIMESTAMP,
    `exported_at` datetime DEFAULT NULL,
    `updated_at`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_export_receipts_code` (`code`),
    UNIQUE KEY `uk_export_receipts_order` (`order_id`),
    KEY           `created_by` (`created_by`),
    KEY           `exported_by` (`exported_by`),
    CONSTRAINT `fk_export_receipts_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`userid`),
    CONSTRAINT `fk_export_receipts_exported_by` FOREIGN KEY (`exported_by`) REFERENCES `users` (`userid`),
    CONSTRAINT `fk_export_receipts_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `export_receipts`
--

LOCK
TABLES `export_receipts` WRITE;
/*!40000 ALTER TABLE `export_receipts` DISABLE KEYS */;
/*!40000 ALTER TABLE `export_receipts` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `good_receipts`
--

DROP TABLE IF EXISTS `good_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts`
(
    `id`                int NOT NULL AUTO_INCREMENT,
    `purchaserequestid` int NOT NULL,
    `processedby`       int NOT NULL,
    `status`            enum('NEW','INCOMPLETED','COMPLETED') DEFAULT 'NEW',
    `created_at`        datetime    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        datetime    DEFAULT CURRENT_TIMESTAMP,
    `note`              text,
    PRIMARY KEY (`id`),
    KEY                 `purchaserequestid` (`purchaserequestid`),
    KEY                 `processedby` (`processedby`),
    CONSTRAINT `good_receipts_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
    CONSTRAINT `good_receipts_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK
TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipts` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `good_receipts_items`
--

DROP TABLE IF EXISTS `good_receipts_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts_items`
(
    `id`              int NOT NULL AUTO_INCREMENT,
    `goodreceiptid`   int NOT NULL,
    `product_id`      int NOT NULL,
    `actual_quantity` int NOT NULL,
    `created_at`      datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY               `goodreceiptid` (`goodreceiptid`),
    KEY               `product_id` (`product_id`),
    CONSTRAINT `good_receipts_items_ibfk_1` FOREIGN KEY (`goodreceiptid`) REFERENCES `good_receipts` (`id`),
    CONSTRAINT `good_receipts_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts_items`
--

LOCK
TABLES `good_receipts_items` WRITE;
/*!40000 ALTER TABLE `good_receipts_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipts_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `inventory_audit`
--

DROP TABLE IF EXISTS `inventory_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_audit`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `createdby`   int NOT NULL,
    `processedby` int DEFAULT NULL,
    `status`      enum('DRAFT','CANCELLED','SUBMITTED','COMPLETED','PENDING','REJECTED') DEFAULT 'DRAFT',
    `createdat`   datetime DEFAULT CURRENT_TIMESTAMP,
    `updatedat`   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY           `createdby` (`createdby`),
    KEY           `processedby` (`processedby`),
    CONSTRAINT `inventory_audit_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
    CONSTRAINT `inventory_audit_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `inventory_audit_item_serials`
(
    `id`              int NOT NULL AUTO_INCREMENT,
    `audit_item_id`   int NOT NULL,
    `product_item_id` int NOT NULL,
    `type`            enum('ADD','DELETE') NOT NULL,
    PRIMARY KEY (`id`),
    KEY               `fk_audit_item_serials_item` (`audit_item_id`),
    KEY               `fk_audit_item_serials_product_item` (`product_item_id`),
    CONSTRAINT `fk_audit_item_serials_item` FOREIGN KEY (`audit_item_id`) REFERENCES `inventory_audit_items` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_audit_item_serials_product_item` FOREIGN KEY (`product_item_id`) REFERENCES `product_items` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `inventory_audit_items`
(
    `id`               int NOT NULL AUTO_INCREMENT,
    `auditid`          int NOT NULL,
    `productid`        int NOT NULL,
    `systemquantity`   int DEFAULT NULL,
    `physicalquantity` int DEFAULT NULL,
    `discrepancy`      int DEFAULT NULL,
    `reasons`          text,
    PRIMARY KEY (`id`),
    KEY                `auditid` (`auditid`),
    KEY                `productid` (`productid`),
    CONSTRAINT `inventory_audit_items_ibfk_1` FOREIGN KEY (`auditid`) REFERENCES `inventory_audit` (`id`),
    CONSTRAINT `inventory_audit_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `models`
(
    `modelid`  int          NOT NULL AUTO_INCREMENT,
    `name`     varchar(100) NOT NULL,
    `brandid`  int          NOT NULL,
    `isactive` tinyint(1) DEFAULT '1',
    PRIMARY KEY (`modelid`),
    KEY        `brandid` (`brandid`),
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
CREATE TABLE `order_items`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `orderid`   int NOT NULL,
    `productid` int NOT NULL,
    `quantity`  int NOT NULL,
    `price`     decimal(15, 2) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY         `orderid` (`orderid`),
    KEY         `productid` (`productid`),
    CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`id`),
    CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `order_items_product_items`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `orderitemid`   int NOT NULL,
    `productitemid` int NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `orderitemid` (`orderitemid`,`productitemid`),
    KEY             `productitemid` (`productitemid`),
    CONSTRAINT `order_items_product_items_ibfk_1` FOREIGN KEY (`orderitemid`) REFERENCES `order_items` (`id`),
    CONSTRAINT `order_items_product_items_ibfk_2` FOREIGN KEY (`productitemid`) REFERENCES `product_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `orders`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `status`      enum('NEW','DOING','COMPLETED','CANCELLED') DEFAULT 'NEW',
    `total_price` decimal(15, 2) DEFAULT NULL,
    `note`        text,
    `orderdate`   datetime       DEFAULT NULL,
    `createdat`   datetime       DEFAULT CURRENT_TIMESTAMP,
    `updatedat`   datetime       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `completedat` datetime       DEFAULT NULL,
    `createdby`   int NOT NULL,
    `processedby` int            DEFAULT NULL,
    `customer_id` int            DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `createdby` (`createdby`),
    KEY           `processedby` (`processedby`),
    KEY           `customer_id` (`customer_id`),
    CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
    CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`processedby`) REFERENCES `users` (`userid`),
    CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
CREATE TABLE `password_resets`
(
    `requestid`   int NOT NULL AUTO_INCREMENT,
    `userid`      int NOT NULL,
    `status`      enum('NEW','COMPLETED') DEFAULT 'NEW',
    `createdat`   datetime DEFAULT CURRENT_TIMESTAMP,
    `completedat` datetime DEFAULT NULL,
    PRIMARY KEY (`requestid`),
    KEY           `userid` (`userid`),
    CONSTRAINT `password_resets_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_resets`
--

LOCK
TABLES `password_resets` WRITE;
/*!40000 ALTER TABLE `password_resets` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_resets` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions`
(
    `permissionid`   int          NOT NULL AUTO_INCREMENT,
    `permissionname` varchar(100) NOT NULL,
    `description`    text,
    PRIMARY KEY (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK
TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;

-- =========================
-- AUDIT PERMISSION
-- =========================
INSERT INTO `permissions`
VALUES 
       (1, 'VIEW_INVENTORY_AUDIT', 'Can view inventory audits'),
       (2, 'CREATE_INVENTORY_AUDIT', 'Can create inventory audits'),
       (3, 'PERFORM_INVENTORY_AUDIT', 'Can perform inventory audits'),
       (4, 'APPROVE_INVENTORY_AUDIT', 'Can approve or decline inventory audits'),
       (5, 'VIEW_INVENTORY_TRANSACTION', 'Can view inventory transactions');
-- =========================
-- PRODUCT PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(6, 'VIEW_PRODUCT', 'View product list'),
(7, 'CREATE_PRODUCT', 'Create new product'),
(8, 'UPDATE_PRODUCT', 'Update product');

-- =========================
-- CATEGORY PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(9, 'VIEW_CATEGORY', 'View category list'),
(10, 'CREATE_CATEGORY', 'Create new category'),
(11, 'UPDATE_CATEGORY', 'Update category');

-- =========================
-- BRAND PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(12, 'VIEW_BRAND', 'View brand list'),
(13, 'CREATE_BRAND', 'Create new brand'),
(14, 'UPDATE_BRAND', 'Update brand');

-- =========================
-- SPECIFICATION PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(15, 'VIEW_SPECIFICATION', 'View specification list'),
(16, 'CREATE_SPECIFICATION', 'Create specification'),
(17, 'UPDATE_SPECIFICATION', 'Update specification');

-- =========================
-- SALE ORDER PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(18, 'VIEW_SALE_ORDER', 'View sale order list'),
(19, 'CREATE_SALE_ORDER', 'Create sale order'),
(20, 'UPDATE_SALE_ORDER', 'Update sale order');

-- =========================
-- PURCHASE ORDER PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(21, 'VIEW_PURCHASE_ORDER', 'View purchase order list'),
(22, 'CREATE_PURCHASE_ORDER', 'Create purchase order'),
(23, 'UPDATE_PURCHASE_ORDER', 'Update purchase order'),
(24, 'APPROVE_REJECT_PURCHASE_REQUEST', 'Approve or Reject purchase request');

-- =========================
-- IMPORT PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(25, 'VIEW_IMPORT_REQUEST', 'View import request list'),
(26, 'VIEW_IMPORT_HISTORY', 'View import history'),
(27, 'PROCESS_IMPORT', 'Process import');

-- =========================
-- EXPORT PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(28, 'VIEW_EXPORT_PRODUCT', 'View export product list'),
(29, 'VIEW_EXPORT_HISTORY', 'View export history'),
(30, 'PROCESS_EXPORT', 'Process export');

-- =========================
-- INVENTORY PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(31, 'VIEW_INVENTORY', 'View inventory');

-- =========================
-- REPORT PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(32, 'VIEW_REPORT', 'View reports');

-- =========================
-- CUSTOMER PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(33, 'VIEW_CUSTOMER', 'View customer list'),
(34, 'CREATE_CUSTOMER', 'Create customer'),
(35, 'UPDATE_CUSTOMER', 'Update customer');

-- =========================
-- SUPPLIER PERMISSION
-- =========================
INSERT INTO permissions (permissionid, permissionname, description) VALUES
(36, 'VIEW_SUPPLIER', 'View supplier list'),
(37, 'CREATE_SUPPLIER', 'Create supplier'),
(38, 'UPDATE_SUPPLIER', 'Update supplier');

/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `product_items`
--

DROP TABLE IF EXISTS `product_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_items`
(
    `id`                 int NOT NULL AUTO_INCREMENT,
    `serial`             varchar(100)   DEFAULT NULL,
    `imported_price`     decimal(15, 2) DEFAULT NULL,
    `export_price`       decimal(15, 2) DEFAULT NULL,
    `isactive`           tinyint(1) DEFAULT '1',
    `imported_at`        datetime       DEFAULT CURRENT_TIMESTAMP,
    `product_id`         int NOT NULL,
    `goodreceiptsitemid` int            DEFAULT NULL,
    `status`             enum('AVAILABLE','UNAVAILABLE','SOLD') DEFAULT 'AVAILABLE',
    PRIMARY KEY (`id`),
    UNIQUE KEY `serial` (`serial`),
    KEY                  `product_id` (`product_id`),
    KEY                  `goodreceiptsitemid` (`goodreceiptsitemid`),
    CONSTRAINT `product_items_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`),
    CONSTRAINT `product_items_ibfk_2` FOREIGN KEY (`goodreceiptsitemid`) REFERENCES `good_receipts_items` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_items`
--

LOCK
TABLES `product_items` WRITE;
/*!40000 ALTER TABLE `product_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products`
(
    `productid`      int          NOT NULL AUTO_INCREMENT,
    `name`           varchar(200) NOT NULL,
    `description`    text,
    `img_url`        varchar(500) DEFAULT NULL,
    `isactive`       tinyint(1) DEFAULT '1',
    `createdat`      datetime     DEFAULT CURRENT_TIMESTAMP,
    `updatedat`      datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `ramid`          int          DEFAULT NULL,
    `romid`          int          DEFAULT NULL,
    `chipid`         int          DEFAULT NULL,
    `unitid`         int          NOT NULL,
    `categoryid`     int          NOT NULL,
    `brandid`        int          NOT NULL,
    `modelid`        int          DEFAULT NULL,
    `sku`            varchar(255) DEFAULT NULL,
    PRIMARY KEY (`productid`),
    KEY              `ramid` (`ramid`),
    KEY              `romid` (`romid`),
    KEY              `chipid` (`chipid`),
    KEY              `unitid` (`unitid`),
    KEY              `categoryid` (`categoryid`),
    KEY              `brandid` (`brandid`),
    KEY              `modelid` (`modelid`),
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
INSERT INTO `products`
VALUES (1, 'Laptop Dell 14 DC14250', 'Office ', 'assets/img/product/1780336108147_ssss_1_125.webp', 0, '2026-05-27 11:27:22', '2026-06-22 08:41:06', 2, 2, 1, 1, 1, 1, 1, 'D15-23'),
       (2, 'Asus TUF Gaming T12', 'Gaming updated', 'assets/img/product/1780327162854_laptopleveno.jpg', 1, '2026-05-27 11:27:22', '2026-06-23 10:45:11', 4, 3, 2, 1, 1, 2, 2, 'A12-53'),
       (3, 'ThinkPad E14 A12-54', 'Business laptop updated', 'assets/img/product/1780327242054_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg', 1, '2026-05-27 11:27:22', '2026-06-22 22:08:45', 3, 2, 3, 1, 1, 3, 3, 'A12-54'),
       (4, 'Kingston Fury 16GB 32000MHZ', 'RAM DDR4 Tesst update', 'assets/img/product/1780327214479_ktc-hero-ddr5-overview-lg.jpg', 1, '2026-05-27 11:27:22', '2026-06-23 10:41:18', 3, NULL, NULL, 2, 2, 4, NULL, 'B12-423'),
       (5, 'Samsung 970 EVO 512GB', 'SSD ass', 'assets/img/product/1780365511731_ssss_1_125.webp', 1, '2026-05-27 11:27:22', '2026-06-22 22:24:09', NULL, 2, NULL, 3, 3, 5, NULL, 'B12-45'),
       (8, 'Asus TUF 7 ', 'con mèo kêu làm sao', 'assets/img/product/1780327319228_61084_laptop_lenovo_thinkbook_14_g8_irl_core_5_9.jpg', 1, '2026-05-28 23:29:54', '2026-06-01 22:21:59', 2, 2, 2, 1, 1, 2, 2, 'B32-12'),
       (10, 'Asus TUF 13', 'meomeo aa', 'assets/img/product/1780327333216_text_ng_n_2__9_31.webp', 1, '2026-05-29 13:32:34', '2026-06-02 11:50:05', 1, 2, 2, 1, 1, 7, 11, 'C21-23'),
       (13, 'Laptop HP 15-fd1289TU Ultra 7', '', 'assets/img/product/1780327353097_laptopleveno.jpg', 1, '2026-05-30 16:12:45', '2026-06-02 11:24:31', 3, 2, 1, 1, 4, 2, 8, 'Generatorc412'),
       (14, 'Laptop ASUS Vivobook S 14 FLIP', 'Laptop ASUS Vivobook S 14 Flip TP3402VA-LZ632W sở hữu bộ CPU Intel Core i5-13420H đi cùng RAM 16GB chuẩn DDR4, cộng thêm ổ cứng 512GB M.2 PCIe 4.0. Mẫu laptop ASUS Vivobook này được trang bị màn hình có độ phân giải WUXGA với kích thước 14 inch. Bên cạnh đó, thiết kế Flip còn cho phép người dùng chuyển đổi laptop và tablet theo nhu cầu.', 'assets/img/product/1780334374996_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg', 1, '2026-05-31 00:18:47', '2026-06-02 08:55:59', 2, 3, 3, 1, 1, 2, 7, 'LZ632W'),
       (15, 'Laptop ASUS TUF Gaming F16 FX608JHR', 'Laptop ASUS TUF Gaming F16 FX608JHR-RV037W được tích hợp một CPU Intel Core i7-14650HX 5.2 GHz, cùng với RAM 16GB và có bộ nhớ SSD lớn lên đến 1TB. Máy còn sở hữu VGA NVIDIA GeForce RTX 8GB kết hợp màn hình FHD+ 16 inch và có tần số quét 165Hz. Máy có màu Jaeger Gray, chỉ nặng 2.2 kg, với pin 90WHrs, phù hợp cho chơi game và làm.', 'assets/img/product/1780299967530_laptopleveno.jpg', 1, '2026-05-31 11:20:01', '2026-06-01 14:46:07', 4, 2, 2, 1, 1, 2, 8, 'RV037W'),
       (16, 'Kingston Fury 16GB', 'RAM DDR4', 'assets/img/product/1780322903782_kingston_8g_3200_compressed_1.webp', 1, '2026-05-31 22:00:03', '2026-06-02 09:31:47', NULL, 3, NULL, 2, 3, 4, NULL, 'KVR32S22S6/4'),
       (17, 'SSD Samsung 123 120000MHz', 'RAM DDR4 SSD Samsung 123 120000MHz', 'assets/img/product/1780361796964_ssss_1_125.webp', 1, '2026-05-31 22:24:24', '2026-06-22 22:25:41', 3, NULL, NULL, 2, 2, 4, NULL, 'KVR32S22S'),
       (18, 'RAM Laptop Kingston 4 GB-DDR4-3200 MHz', 'con mèo kêu', 'assets/img/product/1780247214567_Laptop_HP_15_fd0079_TU_5_c143cff71c.jpg', 1, '2026-05-31 22:39:51', '2026-06-02 08:23:16', 2, 2, NULL, 1, 3, 4, NULL, 'KVR32S22S6'),
       (20, 'RAM Laptop Kingston Sodimm 1.2V', 'RAM Laptop Kingston Sodimm 1.2V 16GB 3200MHz CL22', 'assets/img/product/1780244875864_kingston_8g_3200_compressed_1.webp', 1, '2026-05-31 23:27:55', '2026-06-02 09:22:02', 2, 3, NULL, 1, 3, 4, NULL, 'CL22'),
       (22, 'SSD Samsung 123', 'RAM DDR4', 'assets/img/product/1780246656870_text_ng_n_15_21_1.webp', 1, '2026-05-31 23:57:36', '2026-05-31 23:57:36', 3, NULL, NULL, 2, 2, 4, NULL, 'KVR32S2'),
       (23, 'Ổ cứng HDD Enterprise WD Ultrastar DC HC330 10TB 3.5', 'Ổ cứng HDD WD Ultrastar DC HC330 10TB là một ổ cứng dành cho doanh nghiệp với nhiều tính năng nổi bật. Ổ cứng này đáp ứng nhu cầu lưu trữ, tốc độ truy cập dữ liệu, độ bền và ổn định cao của các doanh nghiệp.\r\n\r\n', 'assets/img/product/1780246828025_05-hdd-enterprise-wd-ultrastar-dc-hc330-10tb-35-01.jpg', 1, '2026-06-01 00:00:28', '2026-06-02 09:01:58', NULL, 3, NULL, 3, 3, 5, NULL, 'WUS721010ALE6L4'),
       (25, 'Laptop ASUS Gaming Vivobook 16X K3605VC-RP431W', 'Laptop ASUS Vivobook 16X K3605VC-RP431W được trang bị vi xử lý Intel Core i5-13420H cho ra hiệu suất ổn định từ công việc cho đến nhu cầu giải trí. Mẫu ASUS Vivobook Gaming này có màn hình lên đến 16 inch cùng tần số quét 144Hz cung cấp hình ảnh sắc nét không bị vỡ. Card đồ hoạ NVIDIA GeForce RTX 3050 4GB GDDR6 là một điểm cộng lớn của dòng laptop này.', 'assets/img/product/1780333162727_text_ng_n_5__9_130.webp', 1, '2026-06-01 23:59:22', '2026-06-02 12:16:17', 3, 2, 2, 1, 4, 1, 1, 'RP431W'),
       (26, 'CPU AMD Ryzen 7 7800X3D (Tray)', 'asa', 'assets/img/product/1780333276897_cpu-amd-ryzen-7-7800x3d_2__3.webp', 1, '2026-06-02 00:01:16', '2026-06-02 11:10:33', 3, 2, 1, 3, 2, 7, 11, '7800X3D '),
       (27, 'RAM Laptop Kingston 4-3200 MHz', 'asss', 'assets/img/product/1780333845552_text_ng_n_5__9_130.webp', 1, '2026-06-02 00:10:35', '2026-06-02 00:10:45', 3, 3, 2, 1, 4, 2, 6, 'R2341D'),
       (28, 'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR', 'Laptop Acer Gaming Nitro V 15 ProPanel ANV15-41-R7CR sở hữu cấu hình mạnh mẽ với CPU AMD Ryzen 5 7535HS and card đồ họa NVIDIA GeForce RTX 4050 6GB GDDR6. Màn hình 15.6 inch FHD IPS and tần số quét 180Hz đem lại hình ảnh sắc nét. Ổ cứng 512GB PCIe NVMe SSD and RAM 16GB DDR5, với hệ thống tản nhiệt Dual-fan đảm bảo hiệu suất tối ưu.\r\n\r\n', 'assets/img/product/1780334956211_sssxs_26.png', 1, '2026-06-02 00:29:05', '2026-06-02 00:45:15', 2, 2, 2, 1, 1, 7, 11, 'ANV15-41'),
       (29, 'Laptop Acer Gaming Nitro ProPanel ANV15-41-R7CR', '', 'assets/img/product/1780336413334_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png', 1, '2026-06-02 00:31:32', '2026-06-02 00:53:33', 2, 2, 2, 1, 1, 7, 11, 'ANV15-41-R5AS'),
       (30, 'RAM Laptop Transcend DDR5 4800MHz 16GB', 'Ram laptop', 'assets/img/product/1780335486900_ram-transcend-ddr5-4800mhz-16gb_1_.webp', 1, '2026-06-02 00:38:06', '2026-06-02 00:38:06', 3, NULL, NULL, 1, 2, 5, NULL, 'R.TC.04'),
       (31, 'RAM Laptop Transcend DDR5 4800MHz 16GB', '', 'assets/img/product/1780335748931_ram-transcend-ddr5-4800mhz-16gb_1_.webp', 1, '2026-06-02 00:42:28', '2026-06-02 00:42:28', NULL, 2, NULL, 2, 3, 2, NULL, 'R.TC.'),
       (32, 'CPU AMD Ryzen 5 5500', '', 'assets/img/product/1780336054684_t_i_xu_ng_-_2023-01-02t221507.270_2_1_1_1_1.png', 0, '2026-06-02 00:47:00', '2026-06-02 00:47:34', 3, 2, 3, 1, 6, 7, 11, 'CPU.AM.09'),
       (33, 'Laptop ASUS VivoBook 15', 'aa', 'assets/img/product/1780364201177_ssss_2_42.png', 1, '2026-06-02 08:36:41', '2026-06-02 12:16:35', 2, 1, 3, 1, 1, 1, 1, 'BQ021W'),
       (34, 'Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU', 'Laptop Acer Aspire Lite 16 GEN 2 AL16-52P-76DU sở hữu màn hình 16 inch Full HD+, RAM 16GB DDR5 tốc độ 4800MHz (hỗ trợ nâng cấp tối đa 64GB). Chiếc laptop Acer Aspire được trang bị Intel Core i7-1355U, card đồ họa Intel Iris Xe, cùng loa Stereo and webcam Full HD. Thiết kế gọn nhẹ 1.7kg, tích hợp đầy đủ cổng kết nối giúp sử dụng linh hoạt.', 'assets/img/product/1780375469602_text_ng_n_6__2_234.png', 1, '2026-06-02 11:44:29', '2026-06-02 12:17:03', 3, 2, 2, 1, 7, 7, 13, 'AL16-52P-76DU'),
       (35, 'Laptop ASUS Vivobook S14 S3407VA', 'Laptop ASUS Vivobook S14 S3407VA-LY146W trang bị vi xử lý Intel Core 5 210H, RAM 16GB DDR5, SSD 512GB cùng với màn hình 14 inch WUXGA sắc nét, chân thực. Máy có thiết kế mỏng nhẹ chỉ 1.4kg, vỏ kim loại bền bỉ, pin lớn 70Wh cho thời gian sử dụng dài. Hỗ trợ Wi-Fi 6, camera IR nhận diện khuôn mặt and bàn phím có đèn nền tích hợp phím Copilot.\r\n', 'assets/img/product/1780378095512_text_ng_n_4__8_52.webp', 0, '2026-06-02 12:28:15', '2026-06-02 12:28:55', 3, 4, 4, 1, 1, 2, 8, 'LY146W'),
       (36, 'Laptop Acer Gaming Aspire 7 A715-59G-57TU', 'Laptop Acer Gaming Aspire 7 A715-59G-57TU được trang bị vi xử lý Intel Core i5-12450H cân trơn tru mọi tác vụ từ văn phòng cho đến chơi game nặng. Hỗ trợ cho vi xử lý là card đồ hoạ RTX 3050 6GB giúp chơi game nặng mượt mà hơn. Người dùng có thể mở nhiều nội dung hiển thị cùng một lúc với màn hình lên đến 15.6 inch.\r\n\r\n', 'assets/img/product/1780379484406_text_ng_n_14__9_26.webp', 1, '2026-06-02 12:51:24', '2026-06-22 22:18:53', 3, 2, 3, 1, 4, 7, 13, 'A715-59G-57TU'),
       (37, 'Laptop Acer Aspire Lite 15 AL15-46P-R73C', 'Laptop Acer Aspire Lite 15 AL15-46P-R73C sở hữu hiệu năng ấn tượng nhờ chip AMD Ryzen 3 5400U, RAM 8GB DDR4, cùng bộ nhớ trong SSD 512GB rộng rãi. Máy có màn hình Full HD 15.6 inch, tần số quét 60Hz trong thân máy chỉ 1.45kg. Laptop có pin 53Wh and hệ thống cổng đa dạng gồm: USB-C, USB-A and HDMI.\r\n\r\n', 'assets/img/product/1780379586909_sssxs_1__9.webp', 1, '2026-06-02 12:53:06', '2026-06-23 14:58:54', 3, 2, 3, 1, 4, 7, 13, 'AL15-46P-R73C');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK
TABLES;


--
-- Table structure for table `purchase_request_items`
--

DROP TABLE IF EXISTS `purchase_request_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_request_items`
(
    `id`                int NOT NULL AUTO_INCREMENT,
    `purchaserequestid` int NOT NULL,
    `product_id`        int NOT NULL,
    `quantity`          int NOT NULL,
    `price`             decimal(15, 2) DEFAULT NULL,
    `isDeleted`         tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY                 `purchaserequestid` (`purchaserequestid`),
    KEY                 `product_id` (`product_id`),
    CONSTRAINT `purchase_request_items_ibfk_1` FOREIGN KEY (`purchaserequestid`) REFERENCES `purchase_requests` (`id`),
    CONSTRAINT `purchase_request_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_request_items`
--

LOCK
TABLES `purchase_request_items` WRITE;
/*!40000 ALTER TABLE `purchase_request_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_request_items` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `purchase_requests`
--

DROP TABLE IF EXISTS `purchase_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_requests`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `createdby`  int NOT NULL,
    `approvedby` int      DEFAULT NULL,
    `status`     enum('NEW','APPROVED','REJECTED','PROCESSING','COMPLETED') DEFAULT 'NEW',
    `note`       text,
    `createdat`  datetime DEFAULT CURRENT_TIMESTAMP,
    `updatedat`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `isDeleted`  tinyint(1) NOT NULL DEFAULT '0',
    `supplierid` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY          `createdby` (`createdby`),
    KEY          `approvedby` (`approvedby`),
    KEY          `purchase_requests_ibfk_3` (`supplierid`),
    CONSTRAINT `purchase_requests_ibfk_1` FOREIGN KEY (`createdby`) REFERENCES `users` (`userid`),
    CONSTRAINT `purchase_requests_ibfk_2` FOREIGN KEY (`approvedby`) REFERENCES `users` (`userid`),
    CONSTRAINT `purchase_requests_ibfk_3` FOREIGN KEY (`supplierid`) REFERENCES `suppliers` (`supplierid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_requests`
--

LOCK
TABLES `purchase_requests` WRITE;
/*!40000 ALTER TABLE `purchase_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_requests` ENABLE KEYS */;
UNLOCK
TABLES;

--

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `quantity` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_product` (`product_id`),
  CONSTRAINT `fk_inventory_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` (product_id, quantity) VALUES
(1, 0),
(2, 0),
(3, 0),
(4, 0),
(5, 0),
(8, 0),
(10, 0),
(13, 0),
(14, 0),
(15, 0),
(16, 0),
(17, 5),
(18, 0),
(20, 0),
(22, 0),
(23, 0),
(25, 0),
(26, 0),
(27, 0),
(28, 0),
(29, 0),
(30, 0),
(31, 0),
(32, 0),
(33, 0),
(34, 0),
(35, 0),
(36, 0),
(37, 0);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `rams`
--

DROP TABLE IF EXISTS `rams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rams`
(
    `id`       int         NOT NULL AUTO_INCREMENT,
    `size`     varchar(20) NOT NULL,
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
CREATE TABLE `role_permission`
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `roleid`       int NOT NULL,
    `permissionid` int NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `roleid` (`roleid`,`permissionid`),
    KEY            `permissionid` (`permissionid`),
    CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`),
    CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permissionid`) REFERENCES `permissions` (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (72,2,1),(73,2,5),(74,2,6),(75,2,9),(76,2,12),(77,2,15),(78,2,18),(79,2,21),(80,2,24),(81,2,25),(82,2,26),(83,2,28),(84,2,29),(85,2,31),(86,2,32),(87,2,33),(88,2,36),(89,3,1),(90,3,2),(91,3,3),(92,3,4),(93,3,5),(94,3,6),(95,3,7),(96,3,8),(97,3,9),(98,3,10),(99,3,11),(100,3,12),(101,3,13),(102,3,14),(103,3,15),(104,3,16),(105,3,17),(106,3,18),(107,3,21),(108,3,25),(109,3,26),(110,3,27),(111,3,28),(112,3,29),(113,3,30),(114,3,31),(47,4,6),(48,4,7),(49,4,8),(50,4,9),(51,4,10),(52,4,11),(53,4,12),(54,4,13),(55,4,14),(56,4,15),(57,4,16),(58,4,17),(59,4,18),(60,4,19),(61,4,20),(62,4,21),(63,4,22),(64,4,23),(65,4,31),(66,4,33),(67,4,34),(68,4,35),(69,4,36),(70,4,37),(71,4,38);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
    `roleid`   int         NOT NULL AUTO_INCREMENT,
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
CREATE TABLE `roms`
(
    `id`       int         NOT NULL AUTO_INCREMENT,
    `size`     varchar(20) NOT NULL,
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
CREATE TABLE `stock_movement`
(
    `id`             int NOT NULL AUTO_INCREMENT,
    `productid`      int NOT NULL,
    `quantity`       int NOT NULL,
    `type`           enum('INCREASED','DECREASED') NOT NULL,
    `reference_type` enum('INVENTORY_AUDIT','IMPORT','EXPORT') NOT NULL,
    `reference_id`   int      DEFAULT NULL,
    `createdat`      datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY              `productid` (`productid`),
    KEY              `idx_stock_movement_reference` (`reference_type`,`reference_id`),
    CONSTRAINT `stock_movement_ibfk_1` FOREIGN KEY (`productid`) REFERENCES `products` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_movement`
--

LOCK
TABLES `stock_movement` WRITE;
/*!40000 ALTER TABLE `stock_movement` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_movement` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers`
(
    `supplierid`   int          NOT NULL AUTO_INCREMENT,
    `suppliername` varchar(255) NOT NULL,
    `phone`        varchar(20) DEFAULT NULL,
    `email`        varchar(100) NOT NULL,
    `address`      text,
    `isactive`     tinyint(1) DEFAULT '1',
    `createdat`    datetime    DEFAULT CURRENT_TIMESTAMP,
    `updatedat`    datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
CREATE TABLE `units`
(
    `id`       int         NOT NULL AUTO_INCREMENT,
    `name`     varchar(50) NOT NULL,
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
CREATE TABLE `users`
(
    `userid`       int          NOT NULL AUTO_INCREMENT,
    `username`     varchar(50)  NOT NULL,
    `fullname`     varchar(150) NOT NULL,
    `passwordhash` varchar(255) NOT NULL,
    `roleid`       int          NOT NULL,
    `phone`        varchar(20)  DEFAULT NULL,
    `email`        varchar(100) DEFAULT NULL,
    `gender`       enum('MALE','FEMALE','OTHER') DEFAULT NULL,
    `isactive`     tinyint(1) DEFAULT '1',
    `firstname`    varchar(50)  NOT NULL,
    `lastname`     varchar(50)  NOT NULL,
    PRIMARY KEY (`userid`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `phone` (`phone`),
    UNIQUE KEY `email` (`email`),
    KEY            `roleid` (`roleid`),
    CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK
TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users`
VALUES (2, 'manager01', 'Nguyen Thi Manager 1', 'manager123hash', 2, '0900000002', 'manager@gmail.com', 'MALE', 1,
        'Nguyen Thi', 'Manager'),
       (3, 'staff01', 'Tran Van Staff', 'staff123hash', 3, '0900000003', 'staff@gmail.com', 'MALE', 1, 'Tran Van',
        'Staff'),
       (4, 'customer01', 'Le Customer', 'customer123hash', 4, '0900000004', 'customer@gmail.com', 'OTHER', 0, 'Le',
        'Customer'),
       (5, 'nam1', 'Nguyễn Tuấn Nam', '$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG', 5, '0982699381',
        'emnam2k5@gmail.com', 'MALE', 1, 'Nguyễn Tuấn', 'Nam'),
       (6, 'admin0', 'System Admin', '$2a$05$ViJOaXoxE8h3Y1XxHZ5O0efAw9flQgH4pkX82AGi3aR3TOGJEiK8.', 1, '0982699382',
        'stdsddaff1@gmail.com', 'MALE', 1, 'System', 'Admin'),
       (7, 'linh', 'Tran Phuong Linh', '$2a$05$SSR/XL0QK7SXrPLdL8ki1uWs3IlfkYVuAaK8qDREBxelF7aM4hjoG', 2, '0900000005',
        '1243@gmail.com', 'MALE', 0, 'Tran Phuong', 'Linh'),
       (8, 'meomeo123', 'Quang Hung MasterD', '$2a$05$Ghax46XQdit.TPqhSoB.Ee9gYoBWZjvI.VRGEp0HxsNbObXgEUFa2', 3,
        '0900000006', 'staff1@gmail.com', 'FEMALE', 1, 'Quang Hung', 'MasterD'),
       (9, 'NamNT123', 'Nguyễn Thành Nam', '$2a$12$HVFkUoHr/R2lUW9BHIWacOVC8vHh3AT.rpqU69ObsBHR2avbZKhWW', 3,
        '0900000007', 'nam2k5@gmail.com', 'MALE', 1, 'Nguyễn Thành', 'Nam'),
       (11, 'nam12', 'Quang Hung MasterD', '$2a$12$nQfTxq2ybyDLlzkqwICpguBnuEKlk.ZQZ1Y4GPI4qoGihW6jB9U/a', 3,
        '0912345678', 'staff12@gmail.com', 'MALE', 1, 'Test', 'Nguyen'),
       (12, 'po122', 'Tran Duc Duy', '$2a$12$UPOyr4qmHE.MDxfEGyq.aOAHWAUYpBeCa1UCl612dF.KIGa.hrF0y', 4, '0900000089',
        'admin12@gmail.com', 'MALE', 1, 'Duy', 'Tran Duc'),
       (13, 'admin', 'Admin', '$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG', 1, '0900000010',
        'audit_admin@gmail.com', 'MALE', 1, 'Audit', 'Admin'),
       (14, 'manager', 'Manager', '$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG', 2, '0900000011',
        'audit_manager@gmail.com', 'MALE', 1, 'Audit', 'Manager'),
       (15, 'warehouse', 'Warehouse Staff', '$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG', 3,
        '0900000012', 'audit_staff@gmail.com', 'MALE', 1, 'Audit', 'Staff'),
       (16, 'saleman', 'Saleman Staff', '$2a$12$ijZe3yxmOyjx19zGgdRnZ.3h13ud0QYDho4YaDgEAjljFvDjlltsG', 4, '0900000212',
        'audsi_staff@gmail.com', 'MALE', 1, 'Audist', 'Staff'),
       (17, 'tung', 'Tran Thanh Tung', '$2a$12$Jt1ki.DA8zjeyJLU2EDTHeTlHDtd59S0m/sEQtf4h/IgefF4r0wuW', 2, '0966244761',
        'trthtung231@gmail.com', 'MALE', 1, 'Tung', 'Tran');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK
TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


DELIMITER //
CREATE TRIGGER after_product_insert
AFTER INSERT ON products
FOR EACH ROW
BEGIN
    INSERT INTO inventory (product_id, quantity) VALUES (NEW.productid, 0);
END//
DELIMITER ;
