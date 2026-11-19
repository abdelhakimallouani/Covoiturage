package ma.youcode.covoiturage.passager.mapper;

import ma.youcode.covoiturage.passager.dto.request.CreatePassagerRequest;
import ma.youcode.covoiturage.passager.dto.response.PassagerResponse;
import ma.youcode.covoiturage.passager.entity.Passager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassagerMapper {
    @Mapping(target = "id", ignore = true)

    Passager toEntity(CreatePassagerRequest request);

    PassagerResponse toResponse(Passager passager);

    List<PassagerResponse> toResponseList(List<Passager> passagers);
}
