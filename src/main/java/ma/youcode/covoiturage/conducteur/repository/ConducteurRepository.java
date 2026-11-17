package ma.youcode.covoiturage.conducteur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.covoiturage.conducteur.entity.Conducteur;

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {
    boolean existsByEmail(String email);
    boolean existsByNumeroPermis(String numeroPermis);
}
