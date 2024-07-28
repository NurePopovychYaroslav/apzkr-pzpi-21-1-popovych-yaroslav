package ua.clamor1s.eLock.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.clamor1s.eLock.dto.request.GroupPermissionRequest;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.facade.GroupFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupRestController {

    private final GroupFacade groupFacade;

    @GetMapping
    public List<GroupResponse> index() {
        return groupFacade.getAllGroups();
    }

    @GetMapping("/{groupId}")
    public GroupResponse getById(@PathVariable Long groupId) {
        return groupFacade.getGroupById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse create(@RequestBody GroupRequest groupRequest) {
        return groupFacade.createGroup(groupRequest);
    }

    @PutMapping("/{groupId}")
    public GroupResponse update(@PathVariable Long groupId, @RequestBody GroupRequest groupRequest) {
        return groupFacade.updateGroup(groupId, groupRequest);
    }

    @DeleteMapping("/{groupId}")
    public GroupResponse delete(@PathVariable Long groupId) {
        return groupFacade.deleteGroup(groupId);
    }

    @GetMapping("/permission")
    public List<GroupPermissionResponse> getAllPermissions() {
        return groupFacade.getAllGroupPermissions();
    }

    @PostMapping("/permission")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupPermissionResponse createPermission(@RequestBody GroupPermissionRequest groupPermissionRequest) {
        return groupFacade.addPermission(groupPermissionRequest);
    }

    @DeleteMapping("/{groupId}/permission/{permissionId}")
    public GroupPermissionResponse deletePermission(@PathVariable Long groupId,
                                                    @PathVariable Long permissionId) {
        return groupFacade.deleteGroupPermission(groupId, permissionId);
    }
}
