package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.entity.Campus;

@Mapper(componentModel = "spring")
public interface CampusMapper extends CreatableUpdatableMapper<Campus> {
    @BeanMapping(builder = @Builder(disableBuilder = true))
    Campus campusRequestToCampus(CampusRequest campusRequest);

    CampusResponse campusToCampusResponse(Campus campus);

    void updateCampusByCampusRequest(@MappingTarget Campus campus, CampusRequest campusRequest);
}
