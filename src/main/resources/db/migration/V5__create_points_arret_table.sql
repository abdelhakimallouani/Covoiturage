CREATE TABLE points_arret (
    id BIGSERIAL PRIMARY KEY,

    trajet_id BIGINT NOT NULL,

    ville VARCHAR(100) NOT NULL,

    ordre INTEGER NOT NULL,

    heure_passage TIMESTAMP NOT NULL,

    prix_depuis_depart NUMERIC(10, 2) NOT NULL,

    CONSTRAINT fk_point_arret_trajet
        FOREIGN KEY (trajet_id)
        REFERENCES trajets(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_point_arret_ordre
        UNIQUE (trajet_id, ordre),

    CONSTRAINT uk_point_arret_ville
        UNIQUE (trajet_id, ville),

    CONSTRAINT chk_point_arret_ordre
        CHECK (ordre >= 0),

    CONSTRAINT chk_point_arret_prix
        CHECK (prix_depuis_depart >= 0)
);