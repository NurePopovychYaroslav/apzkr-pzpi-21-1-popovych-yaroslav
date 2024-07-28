package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.GroupPermissionRequest;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;

import java.util.List;

public interface GroupFacade {
    List<GroupResponse> getAllGroups();

    GroupResponse createGroup(GroupRequest groupRequest);

    GroupResponse updateGroup(Long groupId, GroupRequest groupRequest);

    GroupResponse deleteGroup(Long groupId);

    GroupResponse getGroupById(Long groupId);

    List<GroupPermissionResponse> getAllGroupPermissions();

    GroupPermissionResponse addPermission(GroupPermissionRequest groupPermissionRequest);

    GroupPermissionResponse deleteGroupPermission(Long groupId, Long permissionId);
}
