CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,

    passager_id BIGINT NOT NULL,
    trajet_id BIGINT NOT NULL,

    point_montee_id BIGINT NOT NULL,
    point_descente_id BIGINT NOT NULL,

    nombre_places INTEGER NOT NULL,

    montant_total NUMERIC(10, 2) NOT NULL,

    date_reservation TIMESTAMP NOT NULL,

    statut VARCHAR(20) NOT NULL,

    CONSTRAINT fk_reservation_passager
        FOREIGN KEY (passager_id)
        REFERENCES passagers(id),

    CONSTRAINT fk_reservation_trajet
        FOREIGN KEY (trajet_id)
        REFERENCES trajets(id),

    CONSTRAINT fk_reservation_point_montee
        FOREIGN KEY (point_montee_id)
        REFERENCES points_arret(id),

    CONSTRAINT fk_reservation_point_descente
        FOREIGN KEY (point_descente_id)
        REFERENCES points_arret(id),

    CONSTRAINT chk_reservation_nombre_places
        CHECK (nombre_places >= 1),

    CONSTRAINT chk_reservation_montant
        CHECK (montant_total >= 0),

    CONSTRAINT chk_reservation_statut
        CHECK (statut IN ('CONFIRMEE', 'ANNULEE'))
);