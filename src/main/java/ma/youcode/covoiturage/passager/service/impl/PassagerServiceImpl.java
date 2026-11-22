package ma.youcode.covoiturage.passager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ma.youcode.covoiturage.common.exception.DuplicateResourceException;
import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;

import ma.youcode.covoiturage.passager.dto.request.CreatePassagerRequest;
import ma.youcode.covoiturage.passager.dto.response.PassagerResponse;
import ma.youcode.covoiturage.passager.entity.Passager;
import ma.youcode.covoiturage.passager.mapper.PassagerMapper;
import ma.youcode.covoiturage.passager.repository.PassagerRepository;
import ma.youcode.covoiturage.passager.service.PassagerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final PassagerMapper passagerMapper;

    @Override
    public PassagerResponse createPassager(CreatePassagerRequest request) {

        log.info("Creation d un passager avec email ", request.email());

        if (passagerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Un passager avec cet email existe deja");
        }

        Passager passager = passagerMapper.toEntity(request);

        Passager savedPassager = passagerRepository.save(passager);

        log.info("Passager cree avec id", savedPassager.getId());

        return passagerMapper.toResponse(savedPassager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassagerResponse> getAllPassagers() {

        log.info("Recuperation de la liste des passagers");

        List<Passager> passagers = passagerRepository.findAll();

        return passagerMapper.toResponseList(passagers);
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerResponse getPassagerById(Long id) {

        log.info("Recherche du passager avec id", id);

        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager introuvable avec id : " + id));

        return passagerMapper.toResponse(passager);
    }

    @Override
    @Transactional(readOnly = true)
    public PassagerResponse getPassagerByEmail(String email) {

        Passager passager = passagerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun passager trouve avec cet email : " + email));

        return passagerMapper.toResponse(passager);
    }

}
