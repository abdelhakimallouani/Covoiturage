package ma.youcode.covoiturage.vehicule.controller;

import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.vehicule.dto.response.VehiculeResponse;
import ma.youcode.covoiturage.vehicule.service.VehiculeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conducteurs")
@RequiredArgsConstructor
public class DriverVehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping("/{id}/vehicles")
    public List<VehiculeResponse> getVehiclesByDriverId(
            @PathVariable("id") Long conducteurId) {

        return vehiculeService.getVehiclesByDriverId(conducteurId);
    }
}
