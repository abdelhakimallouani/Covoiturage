package ma.youcode.covoiturage.trajet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.trajet.dto.request.CreateTrajetRequest;
import ma.youcode.covoiturage.trajet.dto.response.TrajetResponse;
import ma.youcode.covoiturage.trajet.dto.response.TrajetSearchResponse;
import ma.youcode.covoiturage.trajet.service.TrajetService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
