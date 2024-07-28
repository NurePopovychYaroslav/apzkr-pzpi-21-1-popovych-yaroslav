package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.entity.Campus;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.mapper.CampusMapper;
import ua.clamor1s.eLock.repository.CampusRepository;
import ua.clamor1s.eLock.service.CampusService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampusServiceImpl implements CampusService {
    private final CampusRepository campusRepository;
    private final CampusMapper campusMapper;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public Campus getOrCreateCampus(CampusRequest campusRequest) {
        Campus campus = campusMapper.campusRequestToCampus(campusRequest);
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        campus.setCreatedBy(user.getEmail());
        return campusRepository.save(campus);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Campus> getAllByCurrentUser() {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        return campusRepository.findAllByCreatedByOrderByUpdatedAtDesc(user.getEmail());
    }

    @Transactional(readOnly = true)
    @Override
    public Campus getCampusById(Long id) {
        var campus = campusRepository.findById(id).orElseThrow(() -> new RuntimeException());
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!campus.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        return campus;
    }

    @Transactional
    @Override
    public Campus updateCampusByCampusRequest(Campus campus, CampusRequest campusRequest) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!campus.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        campusMapper.updateCampusByCampusRequest(campus, campusRequest);
        return campusRepository.save(campus);
    }

    @Transactional
    @Override
    public Campus deleteCampus(Campus campus) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!campus.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        campusRepository.delete(campus);
        return campus;
    }

    @Override
    public CampusResponse convertCampusToCampusResponse(Campus campus) {
        return campusMapper.campusToCampusResponse(campus);
    }
}
