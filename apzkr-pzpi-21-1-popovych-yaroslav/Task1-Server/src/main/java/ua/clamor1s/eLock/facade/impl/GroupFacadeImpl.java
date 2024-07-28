package ua.clamor1s.eLock.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.GroupPermissionRequest;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.facade.GroupFacade;
import ua.clamor1s.eLock.service.GroupService;
import ua.clamor1s.eLock.service.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupFacadeImpl implements GroupFacade {

    private final GroupService groupService;
    private final PermissionService permissionService;

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return groups.stream()
                .map(groupService::convertGroupToGroupResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {
        Group group = groupService.createGroup(groupRequest);
        return groupService.convertGroupToGroupResponse(group);
    }

    @Transactional
    @Override
    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        Group group = groupService.getGroupById(groupId);
        group = groupService.updateGroup(group, groupRequest);
        return groupService.convertGroupToGroupResponse(group);
    }

    @Transactional
    @Override
    public GroupResponse deleteGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return groupService.deleteGroup(group);
    }

    @Transactional(readOnly = true)
    @Override
    public GroupResponse getGroupById(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return groupService.convertGroupToGroupResponse(group);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupPermissionResponse> getAllGroupPermissions() {
        List<Group> groups = groupService.getAllGroups();
        return groups.stream()
                .flatMap(group -> groupService.getGroupPermissions(group).stream())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GroupPermissionResponse addPermission(GroupPermissionRequest groupPermissionRequest) {
        Group group = groupService.getGroupById(groupPermissionRequest.getGroupId());
        Permission permission = permissionService.getById(groupPermissionRequest.getPermissionId());
        return groupService.addPermission(group, permission);
    }

    @Transactional
    @Override
    public GroupPermissionResponse deleteGroupPermission(Long groupId, Long permissionId) {
        Group group = groupService.getGroupById(groupId);
        Permission permission = permissionService.getById(permissionId);
        return groupService.removePermission(group, permission);
    }
}
