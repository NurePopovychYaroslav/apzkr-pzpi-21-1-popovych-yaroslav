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
import ua.clamor1s.eLock.dto.request.CampusRequest;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.facade.CampusFacade;
import ua.clamor1s.eLock.utils.annotations.Register;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/campus")
public class CampusController {
    private final CampusFacade campusFacade;

    @GetMapping
    @Register
    public String index(Model model) {
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();
        model.addAttribute("campuses", campuses);
        return "campus/index";
    }

    @PostMapping
    @Register
    public String create(@ModelAttribute("campus") CampusRequest campusRequest, Model model) {
        campusFacade.createCampus(campusRequest);
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();
        model.addAttribute("campuses", campuses);
        return "fragments/campus/campuses :: campusesFragment";
    }

    @PutMapping("/{campusId}")
    @Register
    public String update(@ModelAttribute("campus") CampusRequest campusRequest,
                         @PathVariable("campusId") Long id, Model model) {
        CampusResponse campusResponse = campusFacade.updateCampus(id, campusRequest);
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();
        model.addAttribute("campuses", campuses);
        return "fragments/campus/campuses :: campusesFragment";
    }

    @DeleteMapping("/{campusId}")
    @Register
    public String delete(@PathVariable("campusId") Long id, Model model) {
        CampusResponse campusResponse = campusFacade.deleteCampus(id);
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();
        model.addAttribute("campuses", campuses);
        return "fragments/campus/campuses :: campusesFragment";
    }
}
