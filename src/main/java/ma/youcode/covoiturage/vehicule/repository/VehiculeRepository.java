package ma.youcode.covoiturage.vehicule.repository;

import ma.youcode.covoiturage.vehicule.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository
        extends JpaRepository<Vehicule, Long> {

    boolean existsByImmatriculation(String immatriculation);

    boolean existsByImmatriculationAndIdNot(String immatriculation, Long id);

    List<Vehicule> findAllByConducteurId(Long conducteurId);
}
