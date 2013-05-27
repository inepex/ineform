CREATE DATABASE translatorapp CHARACTER SET utf8;
CREATE USER 'transUser'@'localhost' IDENTIFIED BY 'transPass';
GRANT ALL PRIVILEGES ON translatorapp.* TO 'transUser'@'localhost';
