package ma.youcode.covoiturage.reservation.dto.response;

import ma.youcode.covoiturage.reservation.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(

        Long id,

        Long passagerId,

        Long trajetId,

        Long pointMonteeId,

        String villeMontee,

        Long pointDescenteId,

        String villeDescente,

        Integer nombrePlaces,

        BigDecimal montantTotal,

        LocalDateTime dateReservation,

        StatutReservation statut

) {
}
