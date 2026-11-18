package ma.youcode.covoiturage.passager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePassagerRequest(

        @NotBlank(message = "Le nom est obligatoire") String nom,

        @NotBlank(message = "Le prenom est obligatoire")
        String prenom,

        @NotBlank(message = "email est obligatoire") @Email(message = "L'adresse email est invalide")
        String email,

        @NotBlank(message = "Le tele est obligatoire")
        String telephone

) {
}
