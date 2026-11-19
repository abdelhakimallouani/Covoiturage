package ma.youcode.covoiturage.vehicule.mapper;

import ma.youcode.covoiturage.vehicule.dto.request.CreateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.request.UpdateVehiculeRequest;
import ma.youcode.covoiturage.vehicule.dto.response.VehiculeResponse;
import ma.youcode.covoiturage.vehicule.entity.Vehicule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    Vehicule toEntity(CreateVehiculeRequest request);

    @Mapping(target = "conducteurId", source = "conducteur.id")
    VehiculeResponse toResponse(Vehicule vehicule);

    List<VehiculeResponse> toResponseList(List<Vehicule> vehicules);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    void updateEntity(UpdateVehiculeRequest request, @MappingTarget Vehicule vehicule);
}
