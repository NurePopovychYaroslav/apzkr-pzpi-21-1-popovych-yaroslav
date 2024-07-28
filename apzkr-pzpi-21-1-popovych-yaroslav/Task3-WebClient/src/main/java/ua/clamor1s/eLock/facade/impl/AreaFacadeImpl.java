package ua.clamor1s.eLock.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Campus;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.entity.Student;
import ua.clamor1s.eLock.facade.AreaFacade;
import ua.clamor1s.eLock.service.AreaService;
import ua.clamor1s.eLock.service.CampusService;
import ua.clamor1s.eLock.service.DoorService;
import ua.clamor1s.eLock.service.StudentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AreaFacadeImpl implements AreaFacade {
    private final CampusService campusService;
    private final AreaService areaService;
    private final StudentService studentService;
    private final DoorService doorService;

    @Transactional(readOnly = true)
    @Override
    public List<AreaResponse> getAllByCampusId(Long campusId) {
        Campus campus = campusService.getCampusById(campusId);
        List<Area> areas = areaService.getAreasByCampus(campus);
        return areas.stream()
                .map(area -> areaService.convertAreaToAreaResponse(area))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AreaResponse createArea(Long campusId, AreaRequest areaRequest) {
        Campus campus = campusService.getCampusById(campusId);
        Area area = areaService.createArea(campus, areaRequest);
        return areaService.convertAreaToAreaResponse(area);
    }

    @Transactional
    @Override
    public AreaResponse updateArea(Long campusId, Long areaId, AreaRequest areaRequest) {
        Area area = areaService.getAreaById(areaId);
        areaService.updateAreaByAreaRequest(area, areaRequest);
        return areaService.convertAreaToAreaResponse(area);
    }

    @Transactional
    @Override
    public AreaResponse deleteAreaById(Long campusId, Long areaId) {
        Area area = areaService.getAreaById(areaId);
        area = areaService.deleteArea(area);
        return areaService.convertAreaToAreaResponse(area);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AreaResponse> findPath(Long studentId, Long campusId, Long areaFromId, Long areaToId) {
        Student student = studentService.getStudentById(studentId);
        Campus campus = campusService.getCampusById(campusId);
        Area areaFrom = areaService.getAreaById(areaFromId);
        Area areaTo = areaService.getAreaById(areaToId);
        Set<Permission> studentPermissions = student.getGroups().stream()
                .flatMap(group -> group.getPermissions().stream())
                .collect(Collectors.toSet());
        return doorService.getDoorsPath(areaFrom, areaTo, studentPermissions);
    }

    @Transactional(readOnly = true)
    @Override
    public AreaResponse getById(Long campusId, Long areaId) {
        Area area = areaService.getAreaById(areaId);
        if (!area.getCampus().getId().equals(campusId)) {
            throw new RuntimeException();
        }
        return areaService.convertAreaToAreaResponse(area);
    }
}
