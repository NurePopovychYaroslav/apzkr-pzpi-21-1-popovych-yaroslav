package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;

import java.util.List;

public interface AreaFacade {
    List<AreaResponse> getAllByCampusId(Long campusId);

    AreaResponse createArea(Long campusId, AreaRequest areaRequest);

    AreaResponse updateArea(Long campusId, Long areaId, AreaRequest areaRequest);

    AreaResponse deleteAreaById(Long campusId, Long areaId);

    List<AreaResponse> findPath(Long studentId, Long campusId, Long areaFromId, Long areaToId);

    AreaResponse getById(Long campusId, Long areaId);
}
