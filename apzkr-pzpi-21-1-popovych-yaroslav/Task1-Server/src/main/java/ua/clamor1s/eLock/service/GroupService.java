package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.entity.Permission;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();

    GroupResponse convertGroupToGroupResponse(Group group);

    Group createGroup(GroupRequest groupRequest);

    Group getGroupById(Long groupId);

    Group updateGroup(Group group, GroupRequest groupRequest);

    GroupResponse deleteGroup(Group group);

    List<GroupPermissionResponse> getGroupPermissions(Group group);

    GroupPermissionResponse addPermission(Group group, Permission permission);

    GroupPermissionResponse removePermission(Group group, Permission permission);
}
