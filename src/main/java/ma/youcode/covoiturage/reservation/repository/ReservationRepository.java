package ma.youcode.covoiturage.reservation.repository;

import ma.youcode.covoiturage.reservation.entity.Reservation;
import ma.youcode.covoiturage.reservation.enums.StatutReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByTrajetIdAndStatut(Long trajetId, StatutReservation statut);

    List<Reservation> findAllByPassagerId(Long passagerId);

    List<Reservation> findAllByTrajetId(Long trajetId);

    boolean existsByPassagerIdAndTrajetIdAndStatut(Long passagerId, Long trajetId, StatutReservation statut);

    @Query("""
            SELECT COALESCE(SUM(r.nombrePlaces), 0)
            FROM Reservation r
            WHERE r.trajet.id = :trajetId
            AND r.statut = :statut
            """)
    Long countReservedPlaces(@Param("trajetId") Long trajetId, @Param("statut") StatutReservation statut);

}
