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
import ua.clamor1s.eLock.dto.request.StudentGroupRequest;
import ua.clamor1s.eLock.dto.request.StudentRequest;
import ua.clamor1s.eLock.dto.response.StudentGroupResponse;
import ua.clamor1s.eLock.dto.response.StudentResponse;
import ua.clamor1s.eLock.facade.StudentFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentRestController {
    private final StudentFacade studentFacade;

    @GetMapping
    public List<StudentResponse> index() {
        return studentFacade.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public StudentResponse getById(@PathVariable Long studentId) {
        return studentFacade.getById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@RequestBody StudentRequest studentRequest) {
        return studentFacade.createStudent(studentRequest);
    }

    @PutMapping("/{studentId}")
    public StudentResponse update(@PathVariable Long studentId,
                                  @RequestBody StudentRequest studentRequest) {
        return studentFacade.updateStudent(studentId, studentRequest);
    }

    @DeleteMapping("/{studentId}")
    public StudentResponse delete(@PathVariable Long studentId) {
        return studentFacade.deleteStudent(studentId);
    }

    @GetMapping("/group")
    public List<StudentGroupResponse> getStudentGroups() {
        return studentFacade.getAllStudentGroups();
    }

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentGroupResponse createStudentGroup(@RequestBody StudentGroupRequest studentGroupRequest) {
        return studentFacade.addGroup(studentGroupRequest);
    }

    @DeleteMapping("/{studentId}/group/{groupId}")
    public StudentGroupResponse deleteStudentGroup(@PathVariable Long studentId,
                                                   @PathVariable Long groupId) {
        return studentFacade.removeGroup(studentId, groupId);
    }

    @GetMapping("/{studentId}/door/{doorId}/available")
    public boolean isDoorAvailable(@PathVariable Long studentId,
                                   @PathVariable Long doorId) {
        return studentFacade.isDoorAvailable(doorId, studentId);
    }
}
