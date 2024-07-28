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
import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.facade.CampusFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campus")
@RequiredArgsConstructor
public class CampusRestController {
    private final CampusFacade campusFacade;

    @GetMapping
    public List<CampusResponse> index() {
        return campusFacade.getAllByCurrentUser();
    }

    @GetMapping("/{campusId}")
    public CampusResponse getById(@PathVariable Long campusId) {
        return campusFacade.getById(campusId);
    }

    @PutMapping("/{campusId}")
    public CampusResponse update(@PathVariable Long campusId,
                                 @RequestBody CampusRequest campusRequest) {
        return campusFacade.updateCampus(campusId, campusRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampusResponse create(@RequestBody CampusRequest campusRequest) {
        return campusFacade.createCampus(campusRequest);
    }

    @DeleteMapping("/{campusId}")
    public CampusResponse delete(@PathVariable Long campusId) {
        return campusFacade.deleteCampus(campusId);
    }
}
