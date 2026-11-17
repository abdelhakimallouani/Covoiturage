package ma.youcode.covoiturage.conducteur.mapper;

import org.mapstruct.Mapper;

import ma.youcode.covoiturage.conducteur.dto.request.CreateConducteurRequest;
import ma.youcode.covoiturage.conducteur.dto.response.ConducteurResponse;
import ma.youcode.covoiturage.conducteur.entity.Conducteur;

@Mapper(componentModel = "spring")
public interface ConducteurMapper {

    Conducteur toEntity(CreateConducteurRequest request);

    ConducteurResponse toResponse(Conducteur conducteur);
}
