package ma.youcode.covoiturage.trajet.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import ma.youcode.covoiturage.reservation.enums.StatutReservation;
import ma.youcode.covoiturage.trajet.dto.response.TrajetSearchResponse;
import ma.youcode.covoiturage.trajet.entity.Trajet;
import ma.youcode.covoiturage.trajet.enums.StatutTrajet;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findAllByConducteurId(Long conducteurId);

    @Query("""
            SELECT new ma.youcode.covoiturage.trajet.dto.response.TrajetSearchResponse(
                t.id,
                pMontee.ville,
                pDescente.ville,
                t.dateDepart,
                t.placesProposees,

                (t.placesProposees - COALESCE(SUM(r.nombrePlaces), 0)),

                (pDescente.prixDepuisDepart - pMontee.prixDepuisDepart),

                t.statut,
                t.conducteur.id,
                t.vehicule.id
            )

            FROM Trajet t

            JOIN t.pointsArret pMontee
            JOIN t.pointsArret pDescente

            LEFT JOIN t.reservations r ON r.statut = :statutReservation

            WHERE t.statut = :statutTrajet

            AND pMontee.ordre < pDescente.ordre

            AND LOWER(pMontee.ville) = LOWER(:villeDepart)

            AND LOWER(pDescente.ville) = LOWER(:villeArrivee)
            AND t.dateDepart >= :dateDebut
            AND t.dateDepart < :dateFin

            AND (pDescente.prixDepuisDepart - pMontee.prixDepuisDepart) <= :prixMax

            GROUP BY
                t.id,
                pMontee.ville,
                pDescente.ville,
                t.dateDepart,
                t.placesProposees,
                pMontee.prixDepuisDepart,
                pDescente.prixDepuisDepart,
                t.statut,
                t.conducteur.id,
                t.vehicule.id

            HAVING
                (t.placesProposees - COALESCE(SUM(r.nombrePlaces), 0)) >= :nombrePlaces

            ORDER BY t.dateDepart ASC
            """)
    Page<TrajetSearchResponse> searchTrajets(

            @Param("villeDepart") String villeDepart,

            @Param("villeArrivee") String villeArrivee,

            @Param("dateDebut") LocalDateTime dateDebut,

            @Param("dateFin") LocalDateTime dateFin,

            @Param("nombrePlaces") Integer nombrePlaces,

            @Param("prixMax") BigDecimal prixMax,

            @Param("statutTrajet") StatutTrajet statutTrajet,

            @Param("statutReservation") StatutReservation statutReservation,

            Pageable pageable);

    @Query("""
            SELECT COUNT(t)
            FROM Trajet t
            WHERE
                (t.conducteur.id = :conducteurId
                 OR t.vehicule.id = :vehiculeId)
                AND t.statut IN :statuts
                AND t.dateDepart < :nouvelleDateArrivee
                AND (
                    SELECT MAX(p.heurePassage)
                    FROM PointArret p
                    WHERE p.trajet = t
                ) > :nouvelleDateDepart
            """)
    long countPlanningConflicts(
            @Param("conducteurId") Long conducteurId,
            @Param("vehiculeId") Long vehiculeId,
            @Param("nouvelleDateDepart") LocalDateTime nouvelleDateDepart,
            @Param("nouvelleDateArrivee") LocalDateTime nouvelleDateArrivee,
            @Param("statuts") Set<StatutTrajet> statuts);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Trajet t WHERE t.id = :id")
    Optional<Trajet> findByIdForUpdate(@Param("id") Long id);

}
