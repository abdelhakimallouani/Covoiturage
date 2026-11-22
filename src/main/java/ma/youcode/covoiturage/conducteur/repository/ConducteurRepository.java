package ma.youcode.covoiturage.conducteur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.youcode.covoiturage.conducteur.entity.Conducteur;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    boolean existsByEmail(String email);

    boolean existsByNumeroPermis(String numeroPermis);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByNumeroPermisAndIdNot(String numeroPermis, Long id);

    @Query("""
            SELECT c
            FROM Conducteur c
            WHERE
                (:nom IS NULL OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :nom, '%')))
                AND
                (:email IS NULL OR LOWER(c.email) = LOWER(:email))
            """)
    List<Conducteur> searchConducteurs(
            @Param("nom") String nom,
            @Param("email") String email);
}
