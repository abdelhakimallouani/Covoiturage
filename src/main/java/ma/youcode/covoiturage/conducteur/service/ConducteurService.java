package ma.youcode.covoiturage.conducteur.service;

import java.util.List;

import ma.youcode.covoiturage.conducteur.dto.request.CreateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.request.UpdateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.response.ConducteurResponse;

public interface ConducteurService {
    ConducteurResponse createConducteur(
            CreateConducteurRequest request);

    List<ConducteurResponse> getAllConducteurs();

    ConducteurResponse getConducteurById(Long id);

    ConducteurResponse updateConducteur(Long id, UpdateConducteurRequest request);
}
