package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Campus;

import java.util.List;

public interface AreaService {
    List<Area> getAreasByCampus(Campus campus);

    AreaResponse convertAreaToAreaResponse(Area area);

    Area createArea(Campus campus, AreaRequest areaRequest);

    Area getAreaById(Long areaId);

    Area updateAreaByAreaRequest(Area area, AreaRequest areaRequest);

    Area deleteArea(Area area);
}
