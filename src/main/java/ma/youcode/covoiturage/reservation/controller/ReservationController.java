package ma.youcode.covoiturage.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.reservation.dto.request.CreateReservationRequest;
import ma.youcode.covoiturage.reservation.dto.response.ReservationResponse;
import ma.youcode.covoiturage.reservation.service.ReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }
}
