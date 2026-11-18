package ma.youcode.covoiturage.passager.dto.response;

public record PassagerResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String telephone) {
}
