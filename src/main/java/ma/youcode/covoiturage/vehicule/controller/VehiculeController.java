package ma.youcode.covoiturage.vehicule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.covoiturage.vehicule.dto.request.CreateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.request.UpdateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.response.VehiculeResponse;
import ma.youcode.covoiturage.vehicule.service.VehiculeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponse createVehicule(@Valid @RequestBody CreateVehiculeRequest request) {

        return vehiculeService.createVehicule(request);
    }

    @GetMapping("/{id}")
    public VehiculeResponse getVehiculeById(@PathVariable Long id) {
        return vehiculeService.getVehiculeById(id);
    }

    @PutMapping("/{id}")
    public VehiculeResponse updateVehicule(@PathVariable Long id, @Valid @RequestBody UpdateVehiculeRequest request) {

        return vehiculeService.updateVehicule(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicule(@PathVariable Long id) {

        vehiculeService.deleteVehicule(id);
    }
}
