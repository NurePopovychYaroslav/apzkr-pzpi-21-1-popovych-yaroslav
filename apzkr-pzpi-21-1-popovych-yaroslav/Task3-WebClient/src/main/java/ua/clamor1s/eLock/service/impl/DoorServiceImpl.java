package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.DoorRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.dto.response.DoorPermissionResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Door;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.mapper.AreaMapper;
import ua.clamor1s.eLock.mapper.DoorMapper;
import ua.clamor1s.eLock.repository.DoorRepository;
import ua.clamor1s.eLock.service.DoorService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoorServiceImpl implements DoorService {

    private final AuthUtils authUtils;
    private final DoorMapper doorMapper;
    private final DoorRepository doorRepository;
    private final AreaMapper areaMapper;


    @Override
    @Transactional(readOnly = true)
    public List<Door> getFromDoorsByArea(Area area) {
        authUtils.validateUser(area.getCreatedBy());
        return area.getDoorsFrom();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Door> getToDoorsByArea(Area area) {
        authUtils.validateUser(area.getCreatedBy());
        return area.getDoorsTo();
    }

    @Override
    @Transactional
    public Door createDoor(Area from, Area to, DoorRequest doorRequest) {
        authUtils.validateUser(from.getCreatedBy());
        authUtils.validateUser(to.getCreatedBy());
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        Door door = doorMapper.doorRequestToDoorEntity(doorRequest, from, to);
        door.setCreatedBy(user.getEmail());
        return doorRepository.save(door);
    }

    @Override
    @Transactional(readOnly = true)
    public Door getDoorById(Long doorId) {
        return doorRepository.findById(doorId).orElseThrow(() -> new RuntimeException());
    }

    @Override
    @Transactional
    public Door updateDoor(Door door, DoorRequest doorRequest, Area from, Area to) {
        door.setFrom(from);
        door.setTo(to);
        doorMapper.updateDoorByDoorRequest(door, doorRequest);
        return doorRepository.save(door);
    }

    @Override
    @Transactional
    public void deleteDoor(Door door) {
        doorRepository.delete(door);
    }

    @Override
    @Transactional
    public void addPermission(Door door, Permission permission) {
        door.addPermission(permission);
        doorRepository.save(door);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoorPermissionResponse> getDoorPermissions(Door door) {
        Set<Permission> permissions = door.getPermissions();
        return permissions.stream()
                .map(permission -> new DoorPermissionResponse(door.getId(), permission.getId()))
                .toList();
    }

    @Transactional
    @Override
    public void deleteDoorPermission(Door door, Permission permission) {
        door.removePermission(permission);
        doorRepository.save(door);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AreaResponse> getDoorsPath(Area areaFrom, Area areaTo, Set<Permission> studentPermissions) {
        if (areaFrom.equals(areaTo)) {
            return List.of(areaMapper.mapAreaToAreaResponse(areaFrom));
        }
        Deque<Area> queue = new ArrayDeque<>();
        Map<Area, Area> from = new HashMap<>();
        queue.add(areaFrom);
        while (!queue.isEmpty()) {
            Area area = queue.poll();
            area.getDoorsFrom().stream()
                    .filter(door -> door.getPermissions().stream()
                            .anyMatch(studentPermissions::contains))
                    .map(Door::getTo)
                    .forEach(areaIterateTo -> {
                        Area areaIterateToFrom = from.getOrDefault(areaIterateTo, null);
                        boolean cont = false;
                        if (Objects.nonNull(areaIterateToFrom)) {
                            cont = true;
                        }
                        if (!cont) {
                            from.put(areaIterateTo, area);
                            queue.add(areaIterateTo);
                        }
                    });
        }
        if (Objects.isNull(from.getOrDefault(areaTo, null))) {
            return List.of();
        }
        List<Area> areas = new ArrayList<>();
        Area lastArea = areaTo;
        do {
            areas.add(lastArea);
            lastArea = from.get(lastArea);
            if (Objects.isNull(lastArea)) {
                return List.of();
            }
        } while (!lastArea.equals(areaFrom));
        areas.add(areaFrom);

        List<AreaResponse> result = areas.stream()
                .map(areaMapper::mapAreaToAreaResponse)
                .collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    @Override
    public DoorResponse convertDoorToDoorResponse(Door door) {
        return doorMapper.convertDoorToDoorResponse(door);
    }
}
