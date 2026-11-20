package ma.youcode.covoiturage.trajet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.covoiturage.trajet.entity.Trajet;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findAllByConducteurId(Long conducteurId);

}
