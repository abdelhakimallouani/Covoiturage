package ma.youcode.covoiturage.trajet.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePointArretRequest(

        @NotBlank String ville,

        @NotNull @Min(0) Integer ordre,

        @NotNull LocalDateTime heurePassage,

        @NotNull @DecimalMin("0.00") BigDecimal prixDepuisDepart

) {
}
