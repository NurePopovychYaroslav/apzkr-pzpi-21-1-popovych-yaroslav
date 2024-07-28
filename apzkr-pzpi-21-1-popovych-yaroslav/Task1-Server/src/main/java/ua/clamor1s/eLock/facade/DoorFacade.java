package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.DoorPermissionRequest;
import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.dto.response.DoorPermissionResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;

import java.util.List;

public interface DoorFacade {
    List<DoorResponse> getFromDoors(Long areaId);

    List<DoorResponse> getToDoors(Long areaId);

    DoorResponse createDoor(DoorRequest doorRequest);

    DoorResponse updateDoor(Long doorId, DoorRequest doorRequest);

    DoorResponse deleteDoor(Long doorId);

    void addPermission(DoorPermissionRequest doorPermissionRequest);

    List<DoorPermissionResponse> getDoorPermissions(Long areaId);

    void deleteDoorPermission(Long doorId, Long permissionId);

    String convertDoorsToPath(List<AreaResponse> doors);
}
