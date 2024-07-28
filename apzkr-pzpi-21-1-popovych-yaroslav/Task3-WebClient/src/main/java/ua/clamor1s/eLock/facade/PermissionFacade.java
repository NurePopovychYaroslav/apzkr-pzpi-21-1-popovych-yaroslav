package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionFacade {
    List<PermissionResponse> getAllPermissions();

    PermissionResponse createPermission(PermissionRequest permissionRequest);

    PermissionResponse updatePermission(Long permissionId, PermissionRequest permissionRequest);

    PermissionResponse deletePermission(Long permissionId);

    PermissionResponse getById(Long permissionId);
}
