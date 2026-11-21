package ma.youcode.covoiturage.trajet.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.trajet.dto.request.CreateTrajetRequest;
import ma.youcode.covoiturage.trajet.dto.response.TrajetResponse;
import ma.youcode.covoiturage.trajet.dto.response.TrajetSearchResponse;
import ma.youcode.covoiturage.trajet.service.TrajetService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/trajets")
@RequiredArgsConstructor
public class TrajetController {

    private final TrajetService trajetService;

    @PostMapping
    public ResponseEntity<TrajetResponse> publierTrajet(@Valid @RequestBody CreateTrajetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trajetService.publierTrajet(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrajetResponse> getTrajetById(@PathVariable Long id) {
        return ResponseEntity.ok(trajetService.getTrajetById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TrajetSearchResponse>> searchTrajets(

            @RequestParam String villeDepart,

            @RequestParam String villeArrivee,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @RequestParam(defaultValue = "1") @Min(1) Integer nombrePlaces,

            @RequestParam(required = false) @DecimalMin("0.00") BigDecimal prixMax,

            @PageableDefault(size = 10, sort = "dateDepart", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(
                trajetService.searchTrajets(villeDepart, villeArrivee, date, nombrePlaces, prixMax, pageable));
    }

    @PatchMapping("/{id}/terminer")
    public ResponseEntity<Void> terminerTrajet(@PathVariable Long id) {
        trajetService.terminerTrajet(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/annuler")
    public ResponseEntity<Void> annulerTrajet(@PathVariable Long id) {
        trajetService.annulerTrajet(id);
        return ResponseEntity.noContent().build();
    }
}
