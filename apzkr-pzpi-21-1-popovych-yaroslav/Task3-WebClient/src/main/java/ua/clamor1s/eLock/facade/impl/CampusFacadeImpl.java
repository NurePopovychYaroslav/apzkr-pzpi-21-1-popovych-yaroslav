package ua.clamor1s.eLock.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.entity.Campus;
import ua.clamor1s.eLock.facade.CampusFacade;
import ua.clamor1s.eLock.service.CampusService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CampusFacadeImpl implements CampusFacade {
    private final CampusService campusService;


    @Transactional
    @Override
    public CampusResponse createCampus(CampusRequest campusRequest) {
        Campus campus = campusService.getOrCreateCampus(campusRequest);
        return campusService.convertCampusToCampusResponse(campus);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CampusResponse> getAllByCurrentUser() {
        List<Campus> campusList = campusService.getAllByCurrentUser();
        return campusList.stream()
                .map(campusService::convertCampusToCampusResponse)
                .toList();
    }

    @Transactional
    @Override
    public CampusResponse updateCampus(Long id, CampusRequest campusRequest) {
        Campus campus = campusService.getCampusById(id);
        campusService.updateCampusByCampusRequest(campus, campusRequest);
        return campusService.convertCampusToCampusResponse(campus);
    }

    @Transactional
    @Override
    public CampusResponse deleteCampus(Long id) {
        Campus campus = campusService.getCampusById(id);
        campus = campusService.deleteCampus(campus);
        return campusService.convertCampusToCampusResponse(campus);
    }

    @Transactional(readOnly = true)
    @Override
    public CampusResponse getById(Long campusId) {
        Campus campus = campusService.getCampusById(campusId);
        return campusService.convertCampusToCampusResponse(campus);
    }
}
