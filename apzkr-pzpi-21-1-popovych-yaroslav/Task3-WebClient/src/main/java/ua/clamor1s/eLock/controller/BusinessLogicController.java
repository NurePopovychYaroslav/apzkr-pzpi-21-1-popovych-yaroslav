package ua.clamor1s.eLock.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.clamor1s.eLock.dto.response.AreaResponse;
import ua.clamor1s.eLock.dto.response.CampusResponse;
import ua.clamor1s.eLock.dto.response.DoorResponse;
import ua.clamor1s.eLock.dto.response.StudentResponse;
import ua.clamor1s.eLock.facade.AreaFacade;
import ua.clamor1s.eLock.facade.CampusFacade;
import ua.clamor1s.eLock.facade.DoorFacade;
import ua.clamor1s.eLock.facade.StudentFacade;
import ua.clamor1s.eLock.utils.annotations.Register;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logic")
public class BusinessLogicController {

    private final StudentFacade studentFacade;
    private final DoorFacade doorFacade;
    private final AreaFacade areaFacade;
    private final CampusFacade campusFacade;

    @GetMapping
    @Register
    public String index(Model model) {
        List<StudentResponse> students = studentFacade.getAllStudents();
        List<CampusResponse> campuses = campusFacade.getAllByCurrentUser();

        model.addAttribute("students", students);
        model.addAttribute("campuses", campuses);
        return "logic/index";
    }

    @GetMapping("/area")
    public String getArea(@RequestParam("campus") Long campusId, Model model) {
        if (campusId.equals(-1L)) {
            return "fragments/logic/logicInputs :: emptyAreaDoorFormFragment";
        }
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "fragments/logic/logicInputs :: areaDoorFormFragment";
    }

    @GetMapping("/area/path")
    public String getAreaPathFrom(@RequestParam("campus") Long campusId, Model model) {
        if (campusId.equals(-1L)) {
            return "fragments/logic/logicPath :: emptyAreaDoorFormPathFragment";
        }
        List<AreaResponse> areas = areaFacade.getAllByCampusId(campusId);
        model.addAttribute("areas", areas);
        return "fragments/logic/logicPath :: areaDoorFormPathFragment";
    }

    @PostMapping("/find-path")
    public String getPath(@RequestParam("student") Long studentId,
                          @RequestParam("campus") Long campusId,
                          @RequestParam("areaFrom") Long areaFromId,
                          @RequestParam("areaTo") Long areaToId, Model model) {
        if (studentId.equals(-1L) || campusId.equals(-1L) || areaToId.equals(-1L) || areaFromId.equals(-1L)) {
            return "fragments/logic/logicInputs :: emptyResult";
        }
        List<AreaResponse> doors = areaFacade.findPath(studentId, campusId, areaFromId, areaToId);
        String result = doorFacade.convertDoorsToPath(doors);
        model.addAttribute("result", result);
        return "fragments/logic/logicPath :: logicResultPathFragment";
    }

    @GetMapping("/door")
    public String getDoor(@RequestParam("area") Long areaId, Model model) {
        if (areaId.equals(-1L)) {
            return "fragments/logic/logicInputs :: emptyDoorFormFragment";
        }
        List<DoorResponse> doors = doorFacade.getFromDoors(areaId);
        model.addAttribute("doors", doors);
        return "fragments/logic/logicInputs :: doorFormFragment";
    }

    @GetMapping("/door-available")
    public String getAvailable(@RequestParam("door") Long doorId,
                               @RequestParam("student") Long studentId,
                               @RequestParam("area") Long areaId,
                               @RequestParam("campus") Long campusId,
                               Model model) {
        if (doorId.equals(-1L) || studentId.equals(-1L) || areaId.equals(-1L) || campusId.equals(-1L)) {
            return "fragments/logic/logicInputs :: emptyResult";
        }

        boolean available = studentFacade.isDoorAvailable(doorId, studentId);
        model.addAttribute("available", available);
        return "fragments/logic/logicInputs :: resultPermission";
    }
}
