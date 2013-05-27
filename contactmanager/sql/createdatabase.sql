CREATE DATABASE contactmanager CHARACTER SET utf8;
CREATE USER 'manager'@'localhost' IDENTIFIED BY 'manager123';
GRANT ALL PRIVILEGES ON contactmanager.* TO 'manager'@'localhost';
