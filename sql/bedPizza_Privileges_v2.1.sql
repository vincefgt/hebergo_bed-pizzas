CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT SELECT, CREATE, DELETE, UPDATE ON *.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

CREATE USER 'guest'@'localhost' IDENTIFIED BY 'guest';
GRANT SELECT, CREATE, UPDATE ON Users TO 'guest'@'localhost';
GRANT SELECT, CREATE ON Rents TO 'guest'@'localhost';
GRANT SELECT ON Estates TO 'guest'@'localhost';
GRANT SELECT, CREATE, UPDATE ON Addresses TO 'guest'@'localhost';
GRANT SELECT, CREATE ON Cities TO 'guest'@'localhost';
GRANT SELECT ON Roles TO 'guest'@'localhost';
FLUSH PRIVILEGES;

CREATE USER 'host'@'localhost' IDENTIFIED BY 'host';
GRANT SELECT, CREATE, UPDATE ON Users TO 'host'@'localhost';
GRANT SELECT ON Rents TO 'host'@'localhost';
GRANT SELECT, CREATE, UPDATE ON Estates TO 'host'@'localhost';
GRANT SELECT, CREATE, UPDATE ON Addresses TO 'host'@'localhost';
GRANT SELECT, CREATE ON Cities TO 'host'@'localhost';
GRANT SELECT ON Roles TO 'host'@'localhost';
FLUSH PRIVILEGES;