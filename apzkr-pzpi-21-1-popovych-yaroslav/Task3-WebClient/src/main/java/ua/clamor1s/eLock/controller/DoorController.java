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
import ua.clamor1s.eLock.dto.request.DoorPermissionRequest;
import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.dto.response.DoorPermissionResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.dto.response.PermissionResponse;
import ua.clamor1s.eLock.facade.AreaFacade;
import ua.clamor1s.eLock.facade.CampusFacade;
import ua.clamor1s.eLock.facade.DoorFacade;
import ua.clamor1s.eLock.facade.PermissionFacade;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/campus/{campusId}/area/{areaId}/door")
@RequiredArgsConstructor
public class DoorController {

    private final DoorFacade doorFacade;
    private final CampusFacade campusFacade;
    private final AreaFacade areaFacade;
    private final PermissionFacade permissionFacade;

    @GetMapping
    public String index(@PathVariable Long campusId,
                        @PathVariable Long areaId,
                        Model model) {
        List<DoorResponse> fromDoors = doorFacade.getFromDoors(areaId);
        List<DoorResponse> toDoors = doorFacade.getToDoors(areaId);
        model.addAttribute("fromDoors", fromDoors);
        model.addAttribute("toDoors", toDoors);
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();
        List<AreaResponse> areas = campuses.stream()
                .flatMap(campus -> areaFacade.getAllByCampusId(campus.id()).stream())
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("areas", areas);
        List<PermissionResponse> permissions = permissionFacade.getAllPermissions();
        model.addAttribute("permissions", permissions);
        List<DoorPermissionResponse> doorPermissions = doorFacade.getDoorPermissions(areaId);
        model.addAttribute("doorPermissions", doorPermissions);
        return "door/index";
    }

    @PostMapping("/permission")
    public String addPermission(@PathVariable Long areaId, @PathVariable Long campusId,
                                @ModelAttribute("doorPermission") DoorPermissionRequest doorPermissionRequest,
                                Model model) {
        doorFacade.addPermission(doorPermissionRequest);
        List<DoorPermissionResponse> doorPermissions = doorFacade.getDoorPermissions(areaId);
        model.addAttribute("doorPermissions", doorPermissions);
        return "fragments/door/doorPermissions :: doorPermissionsFragment";
    }

    @DeleteMapping("/{doorId}/permission/{permissionId}")
    public String deletePermission(@PathVariable Long doorId, @PathVariable Long permissionId,
                                   @PathVariable Long areaId, @PathVariable Long campusId, Model model) {
        doorFacade.deleteDoorPermission(doorId, permissionId);
        List<DoorPermissionResponse> doorPermissions = doorFacade.getDoorPermissions(areaId);
        model.addAttribute("doorPermissions", doorPermissions);
        return "fragments/door/doorPermissions :: doorPermissionsFragment";
    }

    @PostMapping
    public String create(@PathVariable Long areaId,
                         @ModelAttribute("door") DoorRequest doorRequest, Model model) {
        doorFacade.createDoor(doorRequest);
        List<DoorResponse> fromDoors = doorFacade.getFromDoors(areaId);
        List<DoorResponse> toDoors = doorFacade.getToDoors(areaId);
        model.addAttribute("fromDoors", fromDoors);
        model.addAttribute("toDoors", toDoors);
        return "fragments/door/doorsListContainer :: doorsListFragment";
    }

    @PutMapping("/{doorId}")
    public String update(@PathVariable Long doorId, @PathVariable Long areaId,
                         @ModelAttribute("door") DoorRequest doorRequest,
                         Model model) {
        doorFacade.updateDoor(doorId, doorRequest);
        List<DoorResponse> fromDoors = doorFacade.getFromDoors(areaId);
        List<DoorResponse> toDoors = doorFacade.getToDoors(areaId);
        model.addAttribute("fromDoors", fromDoors);
        model.addAttribute("toDoors", toDoors);
        return "fragments/door/doorsListContainer :: doorsListFragment";
    }

    @DeleteMapping("/{doorId}")
    public String delete(@PathVariable Long doorId, @PathVariable Long areaId,
                         Model model) {
        doorFacade.deleteDoor(doorId);
        List<DoorResponse> fromDoors = doorFacade.getFromDoors(areaId);
        List<DoorResponse> toDoors = doorFacade.getToDoors(areaId);
        model.addAttribute("fromDoors", fromDoors);
        model.addAttribute("toDoors", toDoors);
        return "fragments/door/doorsListContainer :: doorsListFragment";
    }
}
