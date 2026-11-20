package ma.youcode.covoiturage.trajet.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public record CreateTrajetRequest(

        @NotNull Long conducteurId,

        @NotNull Long vehiculeId,

        @NotBlank String villeDepart,

        @NotBlank String villeArrivee,

        @NotNull @Future LocalDateTime dateDepart,

        @NotNull @Min(1) Integer placesProposees,

        @NotEmpty @Size(min = 2) List<@Valid CreatePointArretRequest> pointsArret

) {
}
