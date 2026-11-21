package ma.youcode.covoiturage.trajet.service;

import ma.youcode.covoiturage.trajet.dto.request.CreateTrajetRequest;
import ma.youcode.covoiturage.trajet.dto.response.TrajetResponse;
import ma.youcode.covoiturage.trajet.dto.response.TrajetSearchResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TrajetService {

    TrajetResponse publierTrajet(CreateTrajetRequest request);

    TrajetResponse getTrajetById(Long id);

    Page<TrajetSearchResponse> searchTrajets(
            String villeDepart,
            String villeArrivee,
            LocalDate date,
            Integer nombrePlaces,
            BigDecimal prixMax,
            Pageable pageable);

    List<TrajetResponse> getTrajetsByConducteurId(Long conducteurId);

    void terminerTrajet(Long id);

    void annulerTrajet(Long id);
}
