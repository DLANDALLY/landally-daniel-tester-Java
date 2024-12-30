-- Création de la table parking
CREATE TABLE IF NOT EXISTS parking (
    PARKING_NUMBER INT AUTO_INCREMENT PRIMARY KEY,
    TYPE VARCHAR(10) NOT NULL,
    AVAILABLE BOOLEAN NOT NULL
);

-- Insertion de données fictives dans parking
INSERT INTO parking (PARKING_NUMBER, TYPE, AVAILABLE) VALUES
(1, 'CAR', true),
(2, 'CAR', false),
(3, 'BIKE', true),
(4, 'CAR', true),
(5, 'CAR', false),
(6, 'BIKE', true),
(7, 'CAR', true),
(8, 'CAR', false),
(9, 'BIKE', true),
(10, 'CAR', true);

-- Création de la table ticket
CREATE TABLE IF NOT EXISTS ticket (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    PARKING_NUMBER INT NOT NULL,
    VEHICLE_REG_NUMBER VARCHAR(20) NOT NULL,
    PRICE DECIMAL(10, 2) NOT NULL,
    IN_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    OUT_TIME TIMESTAMP NULL,
    FOREIGN KEY (PARKING_NUMBER) REFERENCES parking(PARKING_NUMBER)
);


-- Insertion de données fictives dans ticket
INSERT INTO ticket (PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) VALUES
(1, 'AB123CD', 15.00, '2024-12-01 08:00:00', '2024-12-01 12:00:00'),
(2, 'EF456GH', 10.50, '2024-12-01 09:00:00', NULL),
(3, 'IJ789KL', 5.00, '2024-12-01 10:00:00', '2024-12-01 11:00:00'),
(4, 'MN012OP', 20.00, '2024-12-01 07:30:00', '2024-12-01 13:00:00'),
(5, 'QR345ST', 7.50, '2024-12-01 08:15:00', '2024-12-01 10:45:00'),
(6, 'UV678WX', 12.00, '2024-12-01 06:00:00', NULL),
(7, 'YZ901AB', 18.00, '2024-12-01 07:00:00', '2024-12-01 12:30:00'),
(8, 'CD234EF', 9.00, '2024-12-01 09:45:00', '2024-12-01 11:45:00'),
(9, 'GH567IJ', 14.00, '2024-12-01 10:30:00', NULL),
(10, 'KL890MN', 6.00, '2024-12-01 11:00:00', '2024-12-01 14:00:00');
