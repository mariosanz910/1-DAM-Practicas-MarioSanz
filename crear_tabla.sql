DROP DATABASE IF EXISTS akihabara_db;
CREATE DATABASE IF NOT EXISTS akihabara_db;
USE akihabara_db;

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10, 2),
    stock INT
);