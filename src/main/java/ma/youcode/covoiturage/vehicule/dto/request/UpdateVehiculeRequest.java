package ma.youcode.covoiturage.vehicule.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateVehiculeRequest(

    @NotBlank(message = "La marque est obligatoire")
    String marque,

    @NotBlank(message = "Le modele est obligatoire")
    String modele,

    @NotBlank(message = "immatriculation est obligatoire")
    String immatriculation,

    @NotBlank(message = "La couleur est obligatoire")
    String couleur,

    @NotNull(message = "La capacite est obligatoire")
    @Min(value = 1, message = "La capacite min est de 1 place")
    @Max(value = 8, message = "La capacite max est de 8 place")
    Integer capacite

) {
}

