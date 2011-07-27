CREATE DATABASE contactmanager;
CREATE USER 'manager'@'localhost' IDENTIFIED BY 'manager123';
GRANT ALL PRIVILEGES ON contactmanager.* TO 'manager'@'localhost';
