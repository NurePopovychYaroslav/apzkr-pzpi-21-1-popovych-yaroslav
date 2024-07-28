package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;

import java.util.List;

public interface CampusFacade {
    CampusResponse createCampus(CampusRequest campusRequest);

    List<CampusResponse> getAllByCurrentUser();

    CampusResponse updateCampus(Long id, CampusRequest campusRequest);

    CampusResponse deleteCampus(Long id);

    CampusResponse getById(Long campusId);
}
