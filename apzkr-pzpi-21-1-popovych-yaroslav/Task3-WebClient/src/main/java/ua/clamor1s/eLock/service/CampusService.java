package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.entity.Campus;

import java.util.List;

public interface CampusService {
    Campus getOrCreateCampus(CampusRequest campusRequest);

    CampusResponse convertCampusToCampusResponse(Campus campus);

    List<Campus> getAllByCurrentUser();

    Campus getCampusById(Long id);

    Campus updateCampusByCampusRequest(Campus campus, CampusRequest campusRequest);

    Campus deleteCampus(Campus campus);
}
