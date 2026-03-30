-- Create Database
CREATE DATABASE IF NOT EXISTS course_db;
USE course_db;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS order_detail;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS product;

-- Create Product Table
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    description LONGTEXT,
    category VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Order Table
CREATE TABLE `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_date DATETIME NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20),
    total_amount DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create OrderDetail Table (Bridge table for Order and Product)
CREATE TABLE order_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Indexes for better performance
CREATE INDEX idx_order_date ON `order`(order_date);
CREATE INDEX idx_customer_name ON `order`(customer_name);
CREATE INDEX idx_category ON product(category);
CREATE INDEX idx_order_id ON order_detail(order_id);
CREATE INDEX idx_product_id ON order_detail(product_id);
