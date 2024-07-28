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
import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.facade.PermissionFacade;

import java.util.List;

@Controller
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionFacade permissionFacade;

    @GetMapping
    public String index(Model model) {
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "permission/index";
    }

    @PostMapping
    public String create(@ModelAttribute("permission") PermissionRequest permissionRequest,
                         Model model) {
        permissionFacade.createPermission(permissionRequest);
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "fragments/permission/permissions :: permissionsFragment";
    }

    @PutMapping("/{permissionId}")
    public String update(@PathVariable Long permissionId,
                         @ModelAttribute("permission") PermissionRequest permissionRequest,
                         Model model) {
        permissionFacade.updatePermission(permissionId, permissionRequest);
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "fragments/permission/permissions :: permissionsFragment";
    }

    @DeleteMapping("/{permissionId}")
    public String delete(@PathVariable Long permissionId, Model model) {
        permissionFacade.deletePermission(permissionId);
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "fragments/permission/permissions :: permissionsFragment";
    }
}
