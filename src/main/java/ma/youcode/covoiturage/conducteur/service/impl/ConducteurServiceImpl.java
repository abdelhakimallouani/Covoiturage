package ma.youcode.covoiturage.conducteur.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.covoiturage.common.exception.DuplicateResourceException;
import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;
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

        log.info("Conducteur cree avec id", savedConducteur.getId());

        return conducteurMapper.toResponse(savedConducteur);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConducteurResponse> getAllConducteurs() {

        return conducteurMapper.toResponseList(conducteurRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ConducteurResponse getConducteurById(Long id) {

        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur introuvable avec id : " + id));

        return conducteurMapper.toResponse(conducteur);
    }

    @Override
    public ConducteurResponse updateConducteur(Long id, UpdateConducteurRequest request) {

        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur introuvable avec id : " + id));

        if (conducteurRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException("Cet email est deja utilise par un autre conducteur");
        }

        if (conducteurRepository.existsByNumeroPermisAndIdNot(request.numeroPermis(), id)) {
            throw new DuplicateResourceException("Ce numero de permis est deja utilise par un autre conducteur");
        }

        conducteurMapper.updateEntity(request, conducteur);

        Conducteur updatedConducteur = conducteurRepository.save(conducteur);

        log.info("Conducteur modifie", id);

        return conducteurMapper.toResponse(updatedConducteur);
    }
}
