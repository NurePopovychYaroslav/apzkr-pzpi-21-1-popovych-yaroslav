package ua.clamor1s.eLock.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.request.PathRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.facade.AreaFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/area/{campusId}")
public class AreaRestController {
    private final AreaFacade areaFacade;

    @GetMapping
    public List<AreaResponse> index(@PathVariable Long campusId) {
        return areaFacade.getAllByCampusId(campusId);
    }

    @GetMapping("/{areaId}")
    public AreaResponse getById(@PathVariable Long campusId, @PathVariable Long areaId) {
        return areaFacade.getById(campusId, areaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AreaResponse create(@PathVariable Long campusId, @RequestBody AreaRequest areaRequest) {
        return areaFacade.createArea(campusId, areaRequest);
    }

    @PutMapping("/{areaId}")
    public AreaResponse update(@PathVariable Long campusId, @PathVariable Long areaId, @RequestBody AreaRequest areaRequest) {
        return areaFacade.updateArea(campusId, areaId, areaRequest);
    }

    @DeleteMapping("/{areaId}")
    public AreaResponse delete(@PathVariable Long campusId, @PathVariable Long areaId) {
        return areaFacade.deleteAreaById(campusId, areaId);
    }

    @GetMapping("/path")
    public List<AreaResponse> getPath(@RequestBody PathRequest pathRequest) {
        return areaFacade.findPath(
                pathRequest.getStudentId(), pathRequest.getCampusId(),
                pathRequest.getAreaFromId(), pathRequest.getAreaToId());
    }
}
