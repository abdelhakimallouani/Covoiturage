package ma.youcode.covoiturage.reservation.repository;

import ma.youcode.covoiturage.reservation.entity.Reservation;
import ma.youcode.covoiturage.reservation.enums.StatutReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByTrajetIdAndStatut(Long trajetId, StatutReservation statut);
}
