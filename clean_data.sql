USE
`wms`;

SET
@OLD_SQL_SAFE_UPDATES = @@SQL_SAFE_UPDATES;
SET
SQL_SAFE_UPDATES = 0;
SET
FOREIGN_KEY_CHECKS = 0;

-- Restore product items exported by the new export receipt flow
UPDATE `product_items` pi
    JOIN `export_receipt_serials` ers
ON ers.`product_item_id` = pi.`id`
    SET pi.`status` = 'AVAILABLE', pi.`export_price` = NULL;

-- Restore product items exported by the old order mapping flow
UPDATE `product_items` pi
    JOIN `order_items_product_items` oipi
ON oipi.`productitemid` = pi.`id`
    SET pi.`status` = 'AVAILABLE', pi.`export_price` = NULL;

-- Restore product items marked unavailable by inventory audit
UPDATE `product_items` pi
    JOIN `inventory_audit_item_serials` iais
ON iais.`serial` = pi.`serial`
    SET pi.`status` = 'AVAILABLE'
WHERE iais.`type` = 'DELETE';

-- Remove product items created by inventory audit
DELETE
pi
FROM `product_items` pi
JOIN `inventory_audit_item_serials` iais
    ON iais.`serial` = pi.`serial`
WHERE iais.`type` = 'ADD';

-- Clean new export receipt data
DELETE
FROM `export_receipt_serials`;
DELETE
FROM `export_receipt_details`;
DELETE
FROM `export_receipts`;

-- Clean old export mapping data
DELETE
FROM `order_items_product_items`;

-- Clean order data
DELETE
FROM `order_items`;
DELETE
FROM `orders`;

-- Clean inventory audit data
DELETE
FROM `inventory_audit_item_serials`;
DELETE
FROM `inventory_audit_items`;
DELETE
FROM `inventory_audit`;

-- Keep import history, only remove export and inventory audit movements
DELETE
FROM `stock_movement`
WHERE `reference_type` IN ('EXPORT', 'INVENTORY_AUDIT');

-- Recalculate product quantity from available product items
UPDATE `products` p
SET p.`total_quantity` = (SELECT COUNT(*)
                          FROM `product_items` pi
                          WHERE pi.`product_id` = p.`productid`
                            AND pi.`status` = 'AVAILABLE');

-- Reset IDs of cleaned tables
ALTER TABLE `export_receipt_serials` AUTO_INCREMENT = 1;
ALTER TABLE `export_receipt_details` AUTO_INCREMENT = 1;
ALTER TABLE `export_receipts` AUTO_INCREMENT = 1;
ALTER TABLE `order_items_product_items` AUTO_INCREMENT = 1;
ALTER TABLE `order_items` AUTO_INCREMENT = 1;
ALTER TABLE `orders` AUTO_INCREMENT = 1;
ALTER TABLE `inventory_audit_item_serials` AUTO_INCREMENT = 1;
ALTER TABLE `inventory_audit_items` AUTO_INCREMENT = 1;
ALTER TABLE `inventory_audit` AUTO_INCREMENT = 1;

SET
FOREIGN_KEY_CHECKS = 1;
SET
SQL_SAFE_UPDATES = @OLD_SQL_SAFE_UPDATES;
