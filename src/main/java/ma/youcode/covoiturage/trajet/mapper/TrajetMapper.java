package ma.youcode.covoiturage.trajet.mapper;

import ma.youcode.covoiturage.trajet.dto.request.CreatePointArretRequest;
import ma.youcode.covoiturage.trajet.dto.request.CreateTrajetRequest;
import ma.youcode.covoiturage.trajet.dto.response.*;
import ma.youcode.covoiturage.trajet.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrajetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "pointsArret", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "statut", ignore = true)
    Trajet toEntity(CreateTrajetRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajet", ignore = true)
    PointArret toPointArret(CreatePointArretRequest request);

    @Mapping(target = "conducteurId", source = "conducteur.id")
    @Mapping(target = "vehiculeId", source = "vehicule.id")
    TrajetResponse toResponse(Trajet trajet);

    PointArretResponse toPointArretResponse(PointArret point);

    List<PointArretResponse> toPointArretResponseList(List<PointArret> points);
}
