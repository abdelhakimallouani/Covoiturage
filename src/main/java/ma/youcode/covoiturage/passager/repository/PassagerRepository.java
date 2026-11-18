package ma.youcode.covoiturage.passager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.covoiturage.passager.entity.Passager;

public interface PassagerRepository extends JpaRepository<Passager,Long>
{

    boolean existsByEmail(String email);
}
