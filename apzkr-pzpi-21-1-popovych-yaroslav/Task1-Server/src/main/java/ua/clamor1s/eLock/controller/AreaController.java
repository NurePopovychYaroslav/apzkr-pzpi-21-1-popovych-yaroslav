package ua.clamor1s.eLock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.clamor1s.eLock.dto.request.AreaRequest;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.facade.AreaFacade;

import java.util.List;

@RequestMapping("/campus/{campusId}/area")
@Controller
@RequiredArgsConstructor
public class AreaController {

    private final AreaFacade areaFacade;

    @GetMapping
    public String index(@PathVariable Long campusId, Model model) {
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "area/index";
    }

    @PostMapping
    public String create(@PathVariable Long campusId, @ModelAttribute("area") AreaRequest areaRequest,
                         Model model) {
        AreaResponse areaResponse = areaFacade.createArea(campusId, areaRequest);
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "fragments/area/areas :: areasFragment";
    }

    @PutMapping("/{areaId}")
    public String update(@PathVariable Long campusId, @PathVariable Long areaId,
                         @ModelAttribute("area") AreaRequest areaRequest, Model model) {
        AreaResponse areaResponse = areaFacade.updateArea(campusId, areaId, areaRequest);
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "fragments/area/areas :: areasFragment";
    }

    @DeleteMapping("/{areaId}")
    public String delete(@PathVariable Long campusId, @PathVariable Long areaId,
                         Model model) {
        AreaResponse areaResponse = areaFacade.deleteAreaById(campusId, areaId);
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "fragments/area/areas :: areasFragment";
    }
}
