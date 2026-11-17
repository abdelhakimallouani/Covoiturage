package ma.youcode.covoiturage.conducteur.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateConducteurRequest(

        @NotBlank String nom,

        @NotBlank String prenom,

        @NotBlank @Email(message = "L'email est invalide") String email,

        @NotBlank String telephone,

        @NotBlank String numeroPermis

) {
}