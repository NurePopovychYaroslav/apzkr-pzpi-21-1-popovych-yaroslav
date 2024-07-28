package ua.clamor1s.eLock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.clamor1s.eLock.dto.request.GroupPermissionRequest;
import ua.clamor1s.eLock.dto.request.GroupRequest;
import ua.clamor1s.eLock.dto.response.GroupPermissionResponse;
import ua.clamor1s.eLock.dto.response.GroupResponse;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.facade.GroupFacade;
import ua.clamor1s.eLock.facade.PermissionFacade;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupFacade groupFacade;
    private final PermissionFacade permissionFacade;

    @GetMapping
    public String index(Model model) {
        List<GroupResponse> groups = groupFacade.getAllGroups();
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        List<GroupPermissionResponse> groupPermissions = groupFacade.getAllGroupPermissions();
        model.addAttribute("groups", groups);
        model.addAttribute("permissions", permissions);
        model.addAttribute("groupPermissions", groupPermissions);
        return "group/index";
    }

    @PostMapping("/permission")
    public String addPermission(@ModelAttribute("groupPermission") GroupPermissionRequest groupPermissionRequest,
                                Model model) {
        groupFacade.addPermission(groupPermissionRequest);
        List<GroupPermissionResponse> groupPermissions = groupFacade.getAllGroupPermissions();
        model.addAttribute("groupPermissions", groupPermissions);
        return "fragments/group/groupPermissions :: groupPermissionsFragment";
    }

    @DeleteMapping("/{groupId}/permission/{permissionId}")
    public String deletePermission(@PathVariable Long groupId, @PathVariable Long permissionId,
                                   Model model) {
        groupFacade.deleteGroupPermission(groupId, permissionId);
        List<GroupPermissionResponse> groupPermissions = groupFacade.getAllGroupPermissions();
        model.addAttribute("groupPermissions", groupPermissions);
        return "fragments/group/groupPermissions :: groupPermissionsFragment";
    }

    @PostMapping
    public String create(@ModelAttribute("group") GroupRequest groupRequest,
                         Model model) {
        groupFacade.createGroup(groupRequest);
        List<GroupResponse> groups = groupFacade.getAllGroups();
        model.addAttribute("groups", groups);
        return "fragments/group/groups :: groupsFragment";
    }

    @PutMapping("/{groupId}")
    public String update(@PathVariable Long groupId,
                         @ModelAttribute("group") GroupRequest groupRequest,
                         Model model) {
        groupFacade.updateGroup(groupId, groupRequest);
        List<GroupResponse> groups = groupFacade.getAllGroups();
        model.addAttribute("groups", groups);
        return "fragments/group/groups :: groupsFragment";
    }

    @DeleteMapping("/{groupId}")
    public String delete(@PathVariable Long groupId, Model model) {
        groupFacade.deleteGroup(groupId);
        List<GroupResponse> groups = groupFacade.getAllGroups();
        model.addAttribute("groups", groups);
        return "fragments/group/groups :: groupsFragment";
    }
}
