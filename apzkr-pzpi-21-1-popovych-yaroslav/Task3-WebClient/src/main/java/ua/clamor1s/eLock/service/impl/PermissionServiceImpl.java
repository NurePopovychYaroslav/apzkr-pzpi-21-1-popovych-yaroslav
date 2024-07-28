package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.mapper.PermissionMapper;
import ua.clamor1s.eLock.repository.PermissionRepository;
import ua.clamor1s.eLock.service.PermissionService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final AuthUtils authUtils;

    @Override
    @Transactional(readOnly = true)
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Transactional
    @Override
    public Permission createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.permissionRequestToPermission(permissionRequest);
        User user = authUtils.getCurrentUser().orElseThrow(RuntimeException::new);
        permission.setCreatedBy(user.getEmail());
        return permissionRepository.save(permission);
    }

    @Transactional(readOnly = true)
    @Override
    public Permission getById(Long permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(RuntimeException::new);
    }

    @Transactional
    @Override
    public Permission updatePermission(Permission permission, PermissionRequest permissionRequest) {
        permissionMapper.updatePermission(permission, permissionRequest);
        return permissionRepository.save(permission);
    }

    @Transactional
    @Override
    public void deletePermission(Permission permission) {
        permissionRepository.delete(permission);
    }

    @Override
    public PermissionResponse convertPermissionToPermissionResponse(Permission permission) {
        return permissionMapper.permissionToPermissionResponse(permission);
    }
}
