package ma.youcode.covoiturage.trajet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PointArretResponse(
        Long id,
        String ville,
        Integer ordre,
        LocalDateTime heurePassage,
        BigDecimal prixDepuisDepart) {
}
