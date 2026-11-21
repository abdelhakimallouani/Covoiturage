package ma.youcode.covoiturage.trajet.dto.response;

import ma.youcode.covoiturage.trajet.enums.StatutTrajet;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TrajetSearchResponse(
                Long id,
                String villeDepart,
                String villeArrivee,
                LocalDateTime dateDepart,
                Integer placesProposees,
                Long placesRestantes,
                BigDecimal prixSegment,
                StatutTrajet statut,
                Long conducteurId,
                Long vehiculeId) {
}
