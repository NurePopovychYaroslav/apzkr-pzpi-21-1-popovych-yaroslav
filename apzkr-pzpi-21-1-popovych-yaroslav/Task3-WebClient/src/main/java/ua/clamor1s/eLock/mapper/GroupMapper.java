package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.utils.annotations.IgnoreBaseMapping;

@Mapper(componentModel = "spring")
public interface GroupMapper extends CreatableUpdatableMapper<Group> {

    GroupResponse groupToGroupResponse(Group group);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @IgnoreBaseMapping
    Group groupRequestToGroup(GroupRequest groupRequest);

    void updateGroup(@MappingTarget Group group, GroupRequest groupRequest);
}
