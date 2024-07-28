package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.entity.Area;
import ua.clamor1s.eLock.entity.Campus;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.mapper.AreaMapper;
import ua.clamor1s.eLock.repository.AreaRepository;
import ua.clamor1s.eLock.repository.CampusRepository;
import ua.clamor1s.eLock.service.AreaService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapper;
    private final AreaRepository areaRepository;
    private final CampusRepository campusRepository;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public List<Area> getAreasByCampus(Campus campus) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!campus.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        return campus.getAreas().stream()
                .sorted((a1, a2) -> a2.getUpdatedAt().compareTo(a1.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Area createArea(Campus campus, AreaRequest areaRequest) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!campus.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        Area area = areaMapper.areaRequestToArea(areaRequest);
        area.setCreatedBy(user.getEmail());
        area.setCampus(campus);
        campus.addArea(areaRepository.save(area));
        campusRepository.save(campus);
        return area;
    }

    @Transactional(readOnly = true)
    @Override
    public Area getAreaById(Long areaId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new RuntimeException());
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!area.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        return area;
    }

    @Transactional
    @Override
    public Area updateAreaByAreaRequest(Area area, AreaRequest areaRequest) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!area.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        areaMapper.updateAreByAreaRequest(area, areaRequest);
        return areaRepository.save(area);
    }

    @Transactional
    @Override
    public Area deleteArea(Area area) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!area.getCreatedBy().equals(user.getEmail())) {
            throw new RuntimeException();
        }
        Campus campus = area.getCampus();
        areaRepository.delete(area);
        campus.removeArea(area);
        return area;
    }

    @Override
    public AreaResponse convertAreaToAreaResponse(Area area) {
        return areaMapper.mapAreaToAreaResponse(area);
    }
}
