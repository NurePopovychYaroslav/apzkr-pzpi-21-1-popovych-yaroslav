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
import ua.clamor1s.eLock.dto.request.PermissionRequest;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.facade.PermissionFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionRestController {
    private final PermissionFacade permissionFacade;

    @GetMapping
    public List<PermissionResponse> index() {
        return permissionFacade.getAllPermissions();
    }

    @GetMapping("/{permissionId}")
    public PermissionResponse getById(@PathVariable Long permissionId) {
        return permissionFacade.getById(permissionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponse create(@RequestBody PermissionRequest permissionRequest) {
        return permissionFacade.createPermission(permissionRequest);
    }

    @PutMapping("/{permissionId}")
    public PermissionResponse update(@PathVariable Long permissionId,
                                     @RequestBody PermissionRequest permissionRequest) {
        return permissionFacade.updatePermission(permissionId, permissionRequest);
    }

    @DeleteMapping("/{permissionId}")
    public PermissionResponse delete(@PathVariable Long permissionId) {
        return permissionFacade.deletePermission(permissionId);
    }
}
