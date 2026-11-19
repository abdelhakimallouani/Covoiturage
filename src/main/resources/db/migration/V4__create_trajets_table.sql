CREATE TABLE trajets (
    id BIGSERIAL PRIMARY KEY,

    conducteur_id BIGINT NOT NULL,
    vehicule_id BIGINT NOT NULL,

    ville_depart VARCHAR(100) NOT NULL,
    ville_arrivee VARCHAR(100) NOT NULL,

    date_depart TIMESTAMP NOT NULL,

    places_proposees INTEGER NOT NULL,

    statut VARCHAR(20) NOT NULL,

    CONSTRAINT fk_trajet_conducteur
        FOREIGN KEY (conducteur_id)
        REFERENCES conducteurs(id),

    CONSTRAINT fk_trajet_vehicule
        FOREIGN KEY (vehicule_id)
        REFERENCES vehicules(id),

    CONSTRAINT chk_trajet_places
        CHECK (places_proposees >= 1),

    CONSTRAINT chk_trajet_statut
        CHECK (statut IN ('PUBLIE', 'COMPLET', 'TERMINE', 'ANNULE'))
);