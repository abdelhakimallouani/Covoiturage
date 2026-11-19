package ma.youcode.covoiturage.vehicule.service;

import ma.youcode.covoiturage.vehicule.dto.request.CreateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.request.UpdateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.response.VehiculeResponse;

import java.util.List;

public interface VehiculeService {

    VehiculeResponse createVehicule(CreateVehiculeRequest request);

    VehiculeResponse getVehiculeById(Long id);

    VehiculeResponse updateVehicule(Long id, UpdateVehiculeRequest request);

    // void deleteVehicule(Long id);

    // List<VehiculeResponse> getVehiclesByDriverId(Long conducteurId);
}
