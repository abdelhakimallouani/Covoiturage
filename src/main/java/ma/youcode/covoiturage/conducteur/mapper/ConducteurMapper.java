package ma.youcode.covoiturage.conducteur.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ma.youcode.covoiturage.conducteur.dto.request.CreateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.request.UpdateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.response.ConducteurResponse;
import ma.youcode.covoiturage.conducteur.entity.Conducteur;

@Mapper(componentModel = "spring")
public interface ConducteurMapper {

    @Mapping(target = "id", ignore = true)

    Conducteur toEntity(CreateConducteurRequest request);

    ConducteurResponse toResponse(Conducteur conducteur);

    List<ConducteurResponse> toResponseList(List<Conducteur> conducteurs);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateConducteurRequest request, @MappingTarget Conducteur conducteur);

}
