package ma.youcode.covoiturage.conducteur.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.DuplicateResourceException;
import ma.youcode.covoiturage.conducteur.dto.request.CreateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.request.UpdateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.response.ConducteurResponse;

import ma.youcode.covoiturage.conducteur.entity.Conducteur;
import ma.youcode.covoiturage.conducteur.mapper.ConducteurMapper;
import ma.youcode.covoiturage.conducteur.repository.ConducteurRepository;
import ma.youcode.covoiturage.conducteur.service.ConducteurService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ConducteurServiceImpl implements ConducteurService {

    private final ConducteurRepository conducteurRepository;
    private final ConducteurMapper conducteurMapper;

    @Override
    public ConducteurResponse createConducteur(CreateConducteurRequest request) {

        if (conducteurRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Cet email est deja utilise");
        }

        if (conducteurRepository.existsByNumeroPermis(request.numeroPermis())) {
            throw new DuplicateResourceException("Ce numero de permis est deja utilise");
        }

        Conducteur conducteur = conducteurMapper.toEntity(request);

        Conducteur savedConducteur = conducteurRepository.save(conducteur);

        log.info("Conducteur cree avec l'identifiant", savedConducteur.getId());

        return conducteurMapper.toResponse(savedConducteur);
    }
}
