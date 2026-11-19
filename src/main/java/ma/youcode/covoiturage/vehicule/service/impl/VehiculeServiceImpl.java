package ma.youcode.covoiturage.vehicule.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ma.youcode.covoiturage.common.exception.DuplicateResourceException;
import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;

import ma.youcode.covoiturage.conducteur.entity.Conducteur;
import ma.youcode.covoiturage.conducteur.repository.ConducteurRepository;

import ma.youcode.covoiturage.vehicule.dto.request.CreateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.request.UpdateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.response.VehiculeResponse;
import ma.youcode.covoiturage.vehicule.entity.Vehicule;
import ma.youcode.covoiturage.vehicule.mapper.VehiculeMapper;
import ma.youcode.covoiturage.vehicule.repository.VehiculeRepository;
import ma.youcode.covoiturage.vehicule.service.VehiculeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final ConducteurRepository conducteurRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponse createVehicule(CreateVehiculeRequest request) {

        log.info("Creer vehicule pour le conducteur id", request.conducteurId());

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId()).orElseThrow(
                () -> new ResourceNotFoundException("Conducteur introuvable avec id : " + request.conducteurId()));

        if (vehiculeRepository.existsByImmatriculation(request.immatriculation())) {

            throw new DuplicateResourceException("Cette immatriculation est deja utilise");
        }

        Vehicule vehicule = vehiculeMapper.toEntity(request);

        vehicule.setConducteur(conducteur);

        Vehicule savedVehicule = vehiculeRepository.save(vehicule);

        log.info("Vehicule cree avec id", savedVehicule.getId());

        return vehiculeMapper.toResponse(savedVehicule);
    }
}
