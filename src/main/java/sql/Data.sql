DROP TABLE IF EXISTS Artikel CASCADE;
DROP TABLE IF EXISTS Bestellung CASCADE;
DROP TABLE IF EXISTS Position CASCADE;

CREATE TABLE Artikel (
    artikelID SERIAL,
    name VARCHAR(20) NOT NULL,
    preis DECIMAL,
    PRIMARY KEY(artikelID)
);

CREATE TABLE Bestellung (
    bestellID SERIAL,
    dateTime TIMESTAMP NOT NULL,
    PRIMARY KEY(bestellID)
);

CREATE TABLE Position (
    bestellID INTEGER,
    artikelID INTEGER,
    anzahl INTEGER NOT NULL,
    FOREIGN KEY(bestellID) REFERENCES Bestellung(bestellID),
    FOREIGN KEY(artikelID) REFERENCES Artikel(artikelID),
    PRIMARY KEY(bestellID, artikelID)
);

INSERT INTO Artikel(name, preis) VALUES
    ('CD', 3.50),
    ('DVD', 15.00),
    ('Fussball', 49.99),
    ('Haarbuerste', 3.20),
    ('Fernseher', 273.89);