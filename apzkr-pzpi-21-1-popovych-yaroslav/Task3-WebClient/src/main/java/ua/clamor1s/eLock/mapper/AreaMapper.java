package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.entity.Area;

@Mapper(componentModel = "spring")
public interface AreaMapper extends CreatableUpdatableMapper<Area> {
    @Mapping(target = "campusId", source = "campus.id")
    AreaResponse mapAreaToAreaResponse(Area area);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    Area areaRequestToArea(AreaRequest areaRequest);

    void updateAreByAreaRequest(@MappingTarget Area area, AreaRequest areaRequest);
}
