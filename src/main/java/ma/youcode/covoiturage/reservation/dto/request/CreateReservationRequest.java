package ma.youcode.covoiturage.reservation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(

        @NotNull Long passagerId,

        @NotNull Long trajetId,

        @NotNull Long pointMonteeId,

        @NotNull Long pointDescenteId,

        @NotNull @Min(1) Integer nombrePlaces

) {
}
