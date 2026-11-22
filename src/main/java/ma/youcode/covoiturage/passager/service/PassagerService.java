package ma.youcode.covoiturage.passager.service;


import ma.youcode.covoiturage.passager.dto.request.CreatePassagerRequest;
import ma.youcode.covoiturage.passager.dto.response.PassagerResponse;

import java.util.List;

public interface PassagerService {

    PassagerResponse createPassager(CreatePassagerRequest request);

    List<PassagerResponse> getAllPassagers();

    PassagerResponse getPassagerById(Long id);
    
    PassagerResponse getPassagerByEmail(String email);
}

