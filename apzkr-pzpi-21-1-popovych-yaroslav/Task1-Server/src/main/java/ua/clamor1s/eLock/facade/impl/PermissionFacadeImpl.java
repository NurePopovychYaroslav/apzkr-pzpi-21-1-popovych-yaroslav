package ua.clamor1s.eLock.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.facade.PermissionFacade;
import ua.clamor1s.eLock.service.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionFacadeImpl implements PermissionFacade {
    private final PermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return permissions.stream()
                .map(permissionService::convertPermissionToPermissionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionService.createPermission(permissionRequest);
        return permissionService.convertPermissionToPermissionResponse(permission);
    }

    @Transactional
    @Override
    public PermissionResponse updatePermission(Long permissionId, PermissionRequest permissionRequest) {
        Permission permission = permissionService.getById(permissionId);
        permission = permissionService.updatePermission(permission, permissionRequest);
        return permissionService.convertPermissionToPermissionResponse(permission);
    }

    @Transactional
    @Override
    public PermissionResponse deletePermission(Long permissionId) {
        Permission permission = permissionService.getById(permissionId);
        PermissionResponse response = permissionService.convertPermissionToPermissionResponse(permission);
        permissionService.deletePermission(permission);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public PermissionResponse getById(Long permissionId) {
        Permission permission = permissionService.getById(permissionId);
        return permissionService.convertPermissionToPermissionResponse(permission);
    }
}
