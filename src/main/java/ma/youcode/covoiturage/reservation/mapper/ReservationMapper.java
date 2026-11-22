package ma.youcode.covoiturage.reservation.mapper;

import ma.youcode.covoiturage.reservation.dto.response.ReservationResponse;
import ma.youcode.covoiturage.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "passagerId", source = "passager.id")
    @Mapping(target = "trajetId", source = "trajet.id")
    @Mapping(target = "pointMonteeId", source = "pointMontee.id")
    @Mapping(target = "villeMontee", source = "pointMontee.ville")
    @Mapping(target = "pointDescenteId", source = "pointDescente.id")
    @Mapping(target = "villeDescente", source = "pointDescente.ville")
    ReservationResponse toResponse(Reservation reservation);

    List<ReservationResponse> toResponseList(List<Reservation> reservations);
}
