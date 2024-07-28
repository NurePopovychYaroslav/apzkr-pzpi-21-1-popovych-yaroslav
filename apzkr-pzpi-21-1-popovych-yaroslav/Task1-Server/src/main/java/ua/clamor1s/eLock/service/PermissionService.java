package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getAllPermissions();

    PermissionResponse convertPermissionToPermissionResponse(Permission permission);

    Permission createPermission(PermissionRequest permissionRequest);

    Permission getById(Long permissionId);

    Permission updatePermission(Permission permission, PermissionRequest permissionRequest);

    void deletePermission(Permission permission);
}
