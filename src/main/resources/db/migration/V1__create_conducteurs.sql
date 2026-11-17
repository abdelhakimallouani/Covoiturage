CREATE TABLE conducteurs (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) NOT NULL,
    numero_permis VARCHAR(255) NOT NULL
);