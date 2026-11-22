package ma.youcode.covoiturage.reservation.controller;

import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.reservation.dto.response.ReservationResponse;
import ma.youcode.covoiturage.reservation.service.ReservationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
public class PassagerReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByPassagerId(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationsByPassagerId(id));
    }
}
