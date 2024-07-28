package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends CreatableUpdatableMapper<Permission> {
    PermissionResponse permissionToPermissionResponse(Permission permission);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    Permission permissionRequestToPermission(PermissionRequest permissionRequest);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
