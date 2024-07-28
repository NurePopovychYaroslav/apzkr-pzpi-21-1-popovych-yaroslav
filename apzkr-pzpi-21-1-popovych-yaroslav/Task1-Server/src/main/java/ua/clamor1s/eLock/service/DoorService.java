package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.dto.response.DoorPermissionResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Door;
import ua.clamor1s.eLock.entity.Permission;

import java.util.List;
import java.util.Set;

public interface DoorService {
    List<Door> getFromDoorsByArea(Area area);

    List<Door> getToDoorsByArea(Area area);

    Door createDoor(Area from, Area to, DoorRequest doorRequest);

    DoorResponse convertDoorToDoorResponse(Door door);

    Door getDoorById(Long doorId);

    Door updateDoor(Door door, DoorRequest doorRequest, Area from, Area to);

    void deleteDoor(Door door);

    void addPermission(Door door, Permission permission);

    List<DoorPermissionResponse> getDoorPermissions(Door door);

    void deleteDoorPermission(Door door, Permission permission);

    List<AreaResponse> getDoorsPath(Area areaFrom, Area areaTo, Set<Permission> studentPermissions);
}
