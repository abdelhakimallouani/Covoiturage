package ma.youcode.covoiturage.conducteur.dto.request;

import jakarta.validation.constraints.*;

public record UpdateConducteurRequest(
        @NotBlank String nom,

        @NotBlank String prenom,

        @NotBlank @Email(message = "L'email est invalide") String email,

        @NotBlank String telephone,

        @NotBlank String numeroPermis) {
}
