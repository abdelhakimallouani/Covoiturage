package ma.youcode.covoiturage.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ma.youcode.covoiturage.passager.entity.Passager;
import ma.youcode.covoiturage.passager.repository.PassagerRepository;

import ma.youcode.covoiturage.trajet.entity.PointArret;
import ma.youcode.covoiturage.trajet.entity.Trajet;
import ma.youcode.covoiturage.trajet.enums.StatutTrajet;
import ma.youcode.covoiturage.trajet.repository.TrajetRepository;

import ma.youcode.covoiturage.reservation.dto.request.CreateReservationRequest;
import ma.youcode.covoiturage.reservation.dto.response.ReservationResponse;
import ma.youcode.covoiturage.reservation.entity.Reservation;
import ma.youcode.covoiturage.reservation.enums.StatutReservation;
import ma.youcode.covoiturage.reservation.mapper.ReservationMapper;
import ma.youcode.covoiturage.reservation.repository.ReservationRepository;
import ma.youcode.covoiturage.reservation.service.ReservationService;

import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;
import ma.youcode.covoiturage.common.exception.BusinessRuleException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;
    private final TrajetRepository trajetRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) {

        Passager passager = passagerRepository.findById(request.passagerId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Passager introuvable avec ID : " + request.passagerId()));

        Trajet trajet = trajetRepository.findByIdForUpdate(request.trajetId())
                .orElseThrow(() -> new ResourceNotFoundException("Trajet introuvable avec ID : " + request.trajetId()));

        if (trajet.getStatut() != StatutTrajet.PUBLIE) {
            throw new BusinessRuleException("Impossible de reserver : le trajet n'est pas ouvert aux reservations.");
        }

        if (!trajet.getDateDepart().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException("Impossible de reserver : le trajet a deja commence.");
        }

        PointArret pointMontee = trajet.getPointsArret().stream()
                .filter(p -> p.getId().equals(request.pointMonteeId()))
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("Le point de montee n'appartient pas a ce trajet."));

        PointArret pointDescente = trajet.getPointsArret().stream()
                .filter(p -> p.getId().equals(request.pointDescenteId()))
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException("Le point de descente n'appartient pas a ce trajet."));

        if (pointMontee.getOrdre() >= pointDescente.getOrdre()) {
            throw new BusinessRuleException("Le point de montee doit etre avant le point de descente.");
        }

        if (request.nombrePlaces() == null || request.nombrePlaces() < 1) {
            throw new BusinessRuleException("Le nombre de places doit etre superieur ou egal a 1.");
        }

        boolean dejaReserve = reservationRepository.existsByPassagerIdAndTrajetIdAndStatut(passager.getId(),
                trajet.getId(), StatutReservation.CONFIRMEE);

        if (dejaReserve) {
            throw new BusinessRuleException("Ce passager possede deja une reservation confirmee pour ce trajet.");
        }

        Long placesReservees = reservationRepository.countReservedPlaces(trajet.getId(), StatutReservation.CONFIRMEE);

        int placesRestantes = trajet.getPlacesProposees() - placesReservees.intValue();

        if (request.nombrePlaces() > placesRestantes) {
            throw new BusinessRuleException("Places insuffisantes. Places restantes : " + placesRestantes);
        }

        BigDecimal prixSegment = pointDescente.getPrixDepuisDepart().subtract(pointMontee.getPrixDepuisDepart());

        BigDecimal montantTotal = prixSegment.multiply(BigDecimal.valueOf(request.nombrePlaces())).setScale(2,
                RoundingMode.HALF_UP);

        Reservation reservation = new Reservation();

        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPointMontee(pointMontee);
        reservation.setPointDescente(pointDescente);
        reservation.setNombrePlaces(request.nombrePlaces());
        reservation.setMontantTotal(montantTotal);
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatut(StatutReservation.CONFIRMEE);

        Reservation savedReservation = reservationRepository.save(reservation);

        int nouvellesPlacesReservees = placesReservees.intValue() + request.nombrePlaces();

        if (nouvellesPlacesReservees == trajet.getPlacesProposees()) {
            trajet.setStatut(StatutTrajet.COMPLET);
            trajetRepository.save(trajet);
        }

        log.info("Reservation creee : ID={}, Passager={}, Trajet={}, Montant={}", savedReservation.getId(),
                passager.getId(), trajet.getId(), montantTotal);

        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation introuvable avec ID : " + id));

        return reservationMapper.toResponse(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByPassagerId(Long passagerId) {

        if (!passagerRepository.existsById(passagerId)) {
            throw new ResourceNotFoundException("Passager introuvable avec ID : " + passagerId);
        }

        List<Reservation> reservations = reservationRepository.findAllByPassagerId(passagerId);

        return reservationMapper.toResponseList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByTrajetId(Long trajetId) {

        if (!trajetRepository.existsById(trajetId)) {
            throw new ResourceNotFoundException("Trajet introuvable avec ID : " + trajetId);
        }

        return reservationMapper.toResponseList(reservationRepository.findAllByTrajetId(trajetId));
    }

}
