-- Création de la table Fourniture
CREATE TABLE if not exists fourniture
(
    id          SERIAL PRIMARY KEY,
    nom         VARCHAR(255)   NOT NULL,
    prix_ht     NUMERIC(10, 2) NOT NULL,
    fournisseur VARCHAR(255)   NOT NULL,
    treated     BOOLEAN default false
);