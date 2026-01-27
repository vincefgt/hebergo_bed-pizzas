-- creation database
DROP DATABASE IF EXISTS Bed_Pizza;
CREATE DATABASE Bed_Pizza;
USE Bed_Pizza;

-- creation table roles / guest, host, admin
CREATE TABLE IF NOT EXISTS ROLES (
   id_role INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
   label_role VARCHAR(50) UNIQUE NOT NULL
);

-- creation table cities 
CREATE TABLE IF NOT EXISTS CITIES(
   id_city INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
   label_city VARCHAR(50) NOT NULL,
   zip_code INT UNIQUE NOT NULL
);

-- creation table addresses   
CREATE TABLE IF NOT EXISTS ADDRESSES (
   id_address INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
   id_city INT UNIQUE NOT NULL,
   number_street VARCHAR(60) NOT NULL,
   FOREIGN KEY(id_city) REFERENCES Cities(id_city)  -- dictionnaire: indiquer FK pour id_city ?
);

-- creation table users  
CREATE TABLE IF NOT EXISTS USERS (
   id_user INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
   id_admin INT,
   id_role INT NOT NULL DEFAULT 1,
   id_address INT,
   firstname VARCHAR(50) NOT NULL,
   lastname VARCHAR(50) NOT NULL,
   phone VARCHAR(10) UNIQUE NOT NULL,
   email VARCHAR(50) UNIQUE NOT NULL,
   password_hash VARCHAR(255) NOT NULL, 
   is_deleted BOOLEAN,
   FOREIGN KEY(id_admin) REFERENCES Users(id_user),
   FOREIGN KEY(id_address) REFERENCES Addresses(id_address),
   FOREIGN KEY(id_role) REFERENCES Roles(id_role)
);

-- creation table estates 
CREATE TABLE IF NOT EXISTS ESTATES(
   id_estate INT PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
   id_admin INT NOT NULL,
   name_estate VARCHAR(50),
   descriptions VARCHAR (255), 
   is_valid BOOLEAN,
   daily_price DOUBLE NOT NULL, -- mandatory ou pas , dans dictionnaire ?
   photo_estate VARCHAR (255),  -- chat GPT conseil de mettre du varchar pour indiquer le chemin de l'image
   id_address INT NOT NULL,
   id_user INT NOT NULL,
   FOREIGN KEY(id_address) REFERENCES Addresses(id_address),
   FOREIGN KEY(id_user) REFERENCES Users (id_user),
   FOREIGN KEY(id_admin) REFERENCES Users (id_user)
);

-- creation table rents  
CREATE TABLE IF NOT EXISTS rents(
   id_user INT UNIQUE NOT NULL,
   id_estate INT UNIQUE NOT NULL,
   PRIMARY KEY(id_estate, id_user),
   purchase_date DATETIME NOT NULL,
   start_rent DATETIME NOT NULL,
   end_rent DATETIME NOT NULL,
   total_price DOUBLE NOT NULL,
   payment_number VARCHAR(50) UNIQUE NOT NULL,
   FOREIGN KEY(id_estate) REFERENCES Estates(id_estate),
   FOREIGN KEY(id_user) REFERENCES Users(id_user)
);


-- population de la DB

-- insertion des roles
INSERT INTO Roles (label_role) VALUES 
('guest'),
('host'),
('admin');

-- insertion des villes
INSERT INTO CITIES (label_city, zip_code) VALUES
('Paris', 75000),
('Marseille', 13000),
('Lyon', 69000),
('Toulouse', 31000),
('Nice', 06000),
('Nantes', 44000),
('Strasbourg', 67000),
('Montpellier', 34000),
('Bordeaux', 33000),
('Lille', 59000),
('Rennes', 35000),
('Reims', 51100),
('Le Havre', 76600),
('Saint-Étienne', 42000),
('Toulon', 83000),
('Grenoble', 38000),
('Dijon', 21000),
('Angers', 49000),
('Villeurbanne', 69100),
('Saint-Denis', 93200);

-- insertion des adresses
INSERT INTO ADDRESSES (id_city, number_street) VALUES
(1, '12 Rue de Rivoli'),          -- Paris
(2, '45 Boulevard de la Blancarde'), -- Marseille
(3, '7 Place Bellecour'),         -- Lyon
(4, '23 Rue de Metz'),           -- Toulouse
(5, '10 Promenade des Anglais'),  -- Nice
(6, '3 Rue Crébillon'),           -- Nantes
(7, '15 Rue des Hallebardes'),    -- Strasbourg
(8, '8 Rue de la Loge'),         -- Montpellier
(9, '33 Rue Sainte-Catherine'),  -- Bordeaux
(10, '50 Rue de la Monnaie');     -- Lille

-- Insertion des utilisateurs
INSERT INTO USERS (id_admin, id_role, id_address, firstname, lastname, phone, email, password_hash, is_deleted) VALUES
-- Admins
(null, 3, 1, 'Julien', 'Machin', '0612345678', 'julien.admin1@example.com', '$2a$12$...', FALSE),
(null, 3, 2, 'Sophie', 'Truc', '0687654321', 'sophie.admin2@example.com', '$2a$12$...', FALSE);

-- Hosts 
INSERT INTO USERS (id_admin, id_role, id_address, firstname, lastname, phone, email, password_hash, is_deleted) VALUES
(1, 2, 3, 'Thomas', 'Martin', '0611223344', 'thomas.host1@example.com', '$2a$12$.98', FALSE),
(1, 2, 4, 'Camille', 'Dupont', '0655667788', 'camille.host2@example.com', '$2a$12$.213', FALSE),

-- Guests 
(2, 1, 5, 'Lucas', 'Chauvin', '0699887766', 'lucas.guest1@example.com', '$2a$12$.325', FALSE),
(2, 1, 6, 'Emma', 'Pomme', '0644332211', 'emma.guest2@example.com', '$2a$12$1218', FALSE);

-- insetion des biens
INSERT INTO ESTATES (id_admin, name_estate, descriptions, is_valid, daily_price, photo_estate, id_address, id_user) VALUES
-- Biens pour id_user=3
(1, 'Appartement Cosy Paris 15ème', 'Appartement lumineux de 50m², proche métro et commerces.', TRUE, 85.50, 'asset/images/estates/appartParis/', 7, 3),
(1, 'Studio Moderne Lyon 2ème', 'Studio rénové de 30m², idéal pour étudiants ou jeunes actifs.', TRUE, 65.00, 'asset/images/estates/lyon_studio1/', 8, 3),

-- Biens pour id_user=4
(1, 'Maison avec Jardin Bordeaux', 'Maison de 120m² avec jardin, quartier calme.', TRUE, 120.00, 'asset/images/estates/bordeaux_maison1/', 9, 4),
(1, 'Loft Industriel Lille', 'Loft de 80m², style industriel, proche centre-ville.', TRUE, 95.00, 'asset/images/estates/lille_loft1/', 10, 4);

-- insertion de locations
INSERT INTO rents (id_user, id_estate, purchase_date, start_rent, end_rent, total_price, payment_number) VALUES
-- Location 1 : Utilisateur 5 (guest) loue l'appartement "Appartement Cosy Paris 15ème" (id_estate=1)
(5, 1, '2026-02-10 14:30:00', '2026-03-01 15:00:00', '2026-03-15 11:00:00', 1207.50, 'PAY-2026-02-001'),

-- Location 2 : Utilisateur 6 (guest) loue la maison "Maison avec Jardin Bordeaux" (id_estate=3)
(6, 3, '2026-02-12 10:15:00', '2026-04-10 16:00:00', '2026-04-24 10:00:00', 1680.00, 'PAY-2026-02-002');

