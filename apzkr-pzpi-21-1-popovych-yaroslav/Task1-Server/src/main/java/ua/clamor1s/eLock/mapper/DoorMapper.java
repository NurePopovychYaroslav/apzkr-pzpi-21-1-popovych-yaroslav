package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Door;
import ua.clamor1s.eLock.utils.annotations.IgnoreBaseMapping;

@Mapper(componentModel = "spring")
public interface DoorMapper extends CreatableUpdatableMapper<Door> {

    @Mapping(target = "areaFromId", source = "from.id")
    @Mapping(target = "areaToId", source = "to.id")
    DoorResponse convertDoorToDoorResponse(Door door);

    @Mapping(target = "name", source = "doorRequest.name")
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @IgnoreBaseMapping
    Door doorRequestToDoorEntity(DoorRequest doorRequest, Area from, Area to);


    void updateDoorByDoorRequest(@MappingTarget Door door, DoorRequest doorRequest);
}
