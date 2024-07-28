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
import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.DoorPermissionResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.facade.DoorFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/door")
@RequiredArgsConstructor
public class DoorRestController {
    private final DoorFacade doorFacade;

    @GetMapping("/{areaId}/from")
    public List<DoorResponse> getFrom(@PathVariable Long areaId) {
        return doorFacade.getFromDoors(areaId);
    }

    @GetMapping("/{areaId}/to")
    public List<DoorResponse> getTo(@PathVariable Long areaId) {
        return doorFacade.getToDoors(areaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoorResponse index(@RequestBody DoorRequest doorRequest) {
        return doorFacade.createDoor(doorRequest);
    }

    @PutMapping("/{doorId}")
    public DoorResponse update(@PathVariable Long doorId, @RequestBody DoorRequest doorRequest) {
        return doorFacade.updateDoor(doorId, doorRequest);
    }

    @DeleteMapping("/{doorId}")
    public DoorResponse delete(@PathVariable Long doorId) {
        return doorFacade.deleteDoor(doorId);
    }

    @GetMapping("/{areaId}/permission")
    public List<DoorPermissionResponse> getDoorPermissions(@PathVariable Long areaId) {
        return doorFacade.getDoorPermissions(areaId);
    }
}
