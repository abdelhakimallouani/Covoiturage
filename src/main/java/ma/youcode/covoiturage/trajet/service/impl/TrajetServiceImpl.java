package ma.youcode.covoiturage.trajet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.conducteur.entity.Conducteur;
import ma.youcode.covoiturage.conducteur.repository.ConducteurRepository;
import ma.youcode.covoiturage.vehicule.entity.Vehicule;
import ma.youcode.covoiturage.vehicule.repository.VehiculeRepository;
import ma.youcode.covoiturage.reservation.entity.Reservation;
import ma.youcode.covoiturage.reservation.enums.StatutReservation;
import ma.youcode.covoiturage.reservation.repository.ReservationRepository;

import ma.youcode.covoiturage.trajet.dto.request.*;
import ma.youcode.covoiturage.trajet.dto.response.*;
import ma.youcode.covoiturage.trajet.entity.*;
import ma.youcode.covoiturage.trajet.enums.StatutTrajet;
import ma.youcode.covoiturage.trajet.mapper.TrajetMapper;
import ma.youcode.covoiturage.trajet.repository.TrajetRepository;
import ma.youcode.covoiturage.trajet.service.TrajetService;

import ma.youcode.covoiturage.common.exception.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final ConducteurRepository conducteurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final ReservationRepository reservationRepository;
    private final TrajetMapper trajetMapper;

    @Override
    public TrajetResponse publierTrajet(CreateTrajetRequest request) {

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur introuvable"));

        Vehicule vehicule = vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule introuvable"));

        if (!vehicule.getConducteur().getId().equals(conducteur.getId())) {
            throw new BusinessRuleException("Ce vehicule n'appartient pas a ce conducteur");
        }

        if (request.placesProposees() > vehicule.getCapacite()) {
            throw new BusinessRuleException("Le nombre de places depasse la capacite du vehicule");
        }
        if (request.villeDepart().equalsIgnoreCase(request.villeArrivee())) {
            throw new BusinessRuleException("La ville de depart et d'arrivee doivent etre differentes");
        }
        validatePointsArret(request);

        Trajet trajet = trajetMapper.toEntity(request);

        trajet.setConducteur(conducteur);
        trajet.setVehicule(vehicule);
        trajet.setStatut(StatutTrajet.PUBLIE);

        request.pointsArret().stream().map(trajetMapper::toPointArret).forEach(trajet::addPointArret);

        Trajet savedTrajet = trajetRepository.save(trajet);

        log.info("Trajet publie avec succe avec id :", savedTrajet.getId());

        return trajetMapper.toResponse(savedTrajet);

    }

    private void validatePointsArret(CreateTrajetRequest request) {
        List<CreatePointArretRequest> points = request.pointsArret();

        if (points == null || points.size() < 2) {
            throw new BusinessRuleException("Un trajet doit avoir au min 2 points dareet");
        }

        CreatePointArretRequest premier = points.get(0);
        CreatePointArretRequest dernier = points.get(points.size() - 1);

        if (premier.ordre() != 0) {
            throw new BusinessRuleException("Le 1er point doit avoir l'ordre 0");
        }

        if (!premier.ville().equalsIgnoreCase(request.villeDepart())) {
            throw new BusinessRuleException("Le 1er point doit correspondre a la ville de depart");
        }

        if (premier.prixDepuisDepart().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException("Le prix du 1er point doit etre egal a 0");
        }

        if (!premier.heurePassage().equals(request.dateDepart())) {
            throw new BusinessRuleException("L'heure du 1er point doit correspondre a la date de depart");
        }

        if (!dernier.ville().equalsIgnoreCase(request.villeArrivee())) {
            throw new BusinessRuleException("Le dernier point doit correspondre a la ville d'arrivee");
        }

        for (int i = 0; i < points.size(); i++) {

            CreatePointArretRequest current = points.get(i);

            if (current.ordre() != i) {
                throw new BusinessRuleException("Les ordres des points d'arret doivent etre consecutifs");
            }

            for (int j = i + 1; j < points.size(); j++) {
                if (current.ville().equalsIgnoreCase(points.get(j).ville())) {
                    throw new BusinessRuleException("Une ville ne peut pas apparaitre deux fois dans le trajet");
                }
            }
        }
    }

    private Trajet findTrajetById(Long id) {
        return trajetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public TrajetResponse getTrajetById(Long id) {

        Trajet trajet = findTrajetById(id);

        return trajetMapper.toResponse(trajet);
    }
}
