package ma.youcode.covoiturage.conducteur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.conducteur.dto.request.CreateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.response.ConducteurResponse;
import ma.youcode.covoiturage.conducteur.service.ConducteurService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conducteurs")
@RequiredArgsConstructor
public class ConducteurController {

    private final ConducteurService conducteurService;

    @PostMapping
    public ResponseEntity<ConducteurResponse> createConducteur(@Valid @RequestBody CreateConducteurRequest request) {

        ConducteurResponse response = conducteurService.createConducteur(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConducteurResponse>> getAllConducteurs() {

        return ResponseEntity.ok(conducteurService.getAllConducteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConducteurResponse> getConducteurById(@PathVariable Long id) {

        return ResponseEntity.ok(conducteurService.getConducteurById(id));
    }
}
