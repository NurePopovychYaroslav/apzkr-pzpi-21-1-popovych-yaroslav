package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.mapper.GroupMapper;
import ua.clamor1s.eLock.repository.GroupRepository;
import ua.clamor1s.eLock.service.GroupService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public Group createGroup(GroupRequest groupRequest) {
        Group group = groupMapper.groupRequestToGroup(groupRequest);
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    @Override
    public Group updateGroup(Group group, GroupRequest groupRequest) {
        groupMapper.updateGroup(group, groupRequest);
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public GroupResponse deleteGroup(Group group) {
        GroupResponse response = convertGroupToGroupResponse(group);
        groupRepository.delete(group);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupPermissionResponse> getGroupPermissions(Group group) {
        return group.getPermissions().stream()
                .map(permission -> new GroupPermissionResponse(group.getId(), permission.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GroupPermissionResponse addPermission(Group group, Permission permission) {
        GroupPermissionResponse response = new GroupPermissionResponse(group.getId(), permission.getId());
        group.addPermission(permission);
        groupRepository.save(group);
        return response;
    }

    @Transactional
    @Override
    public GroupPermissionResponse removePermission(Group group, Permission permission) {
        GroupPermissionResponse response = new GroupPermissionResponse(group.getId(), permission.getId());
        group.removePermission(permission);
        groupRepository.save(group);
        return response;
    }

    @Override
    public GroupResponse convertGroupToGroupResponse(Group group) {
        return groupMapper.groupToGroupResponse(group);
    }
}
