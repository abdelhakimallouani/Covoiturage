
CREATE TABLE vehicules (
    id BIGSERIAL PRIMARY KEY,

    marque VARCHAR(100) NOT NULL,

    modele VARCHAR(100) NOT NULL,

    immatriculation VARCHAR(20) NOT NULL,

    couleur VARCHAR(50) NOT NULL,

    capacite INTEGER NOT NULL,

    conducteur_id BIGINT NOT NULL,

    CONSTRAINT fk_vehicule_conducteur
        FOREIGN KEY (conducteur_id)
        REFERENCES conducteurs(id)
);
