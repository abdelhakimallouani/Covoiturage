package ma.youcode.covoiturage.trajet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ma.youcode.covoiturage.conducteur.repository.ConducteurRepository;
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
        
    }
}
