package ma.youcode.covoiturage.trajet.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.covoiturage.trajet.dto.response.TrajetResponse;
import ma.youcode.covoiturage.trajet.service.TrajetService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conducteurs")
@RequiredArgsConstructor
public class ConducteurTrajetController {

    private final TrajetService trajetService;

    @GetMapping("/{id}/trajets")
    public ResponseEntity<List<TrajetResponse>> getTrajetsByConducteurId(@PathVariable Long id) {
        return ResponseEntity.ok(trajetService.getTrajetsByConducteurId(id));
    }
}
