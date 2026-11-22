package ma.youcode.covoiturage.reservation.service;

import ma.youcode.covoiturage.reservation.dto.request.CreateReservationRequest;
import ma.youcode.covoiturage.reservation.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(CreateReservationRequest request);

    // ReservationResponse getReservationById(Long id);

    // List<ReservationResponse> getReservationsByPassagerId(Long passagerId);

    // List<ReservationResponse> getReservationsByTrajetId(Long trajetId);

    // void cancelReservation(Long id);
}
