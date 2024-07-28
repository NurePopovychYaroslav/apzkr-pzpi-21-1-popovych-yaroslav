package ua.clamor1s.eLock.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.StudentGroupRequest;
import ua.clamor1s.eLock.dto.request.StudentRequest;
import ua.clamor1s.eLock.dto.response.StudentGroupResponse;
import ua.clamor1s.eLock.dto.response.StudentResponse;
import ua.clamor1s.eLock.entity.Door;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.entity.Permission;
import ua.clamor1s.eLock.entity.Student;
import ua.clamor1s.eLock.facade.StudentFacade;
import ua.clamor1s.eLock.service.DoorService;
import ua.clamor1s.eLock.service.GroupService;
import ua.clamor1s.eLock.service.StudentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;
    private final GroupService groupService;
    private final DoorService doorService;

    @Transactional(readOnly = true)
    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return students.stream()
                .map(studentService::convertStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentGroupResponse> getAllStudentGroups() {
        List<Student> students = studentService.getAllStudents();
        return students.stream()
                .flatMap(student -> studentService.getStudentGroups(student).stream())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        Student student = studentService.createStudent(studentRequest);
        return studentService.convertStudentToStudentResponse(student);
    }

    @Transactional
    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) {
        Student student = studentService.getStudentById(studentId);
        student = studentService.updateStudent(student, studentRequest);
        return studentService.convertStudentToStudentResponse(student);
    }

    @Transactional
    @Override
    public StudentResponse deleteStudent(Long studentId) {
        Student student = studentService.getStudentById(studentId);
        StudentResponse response = studentService.convertStudentToStudentResponse(student);
        studentService.deleteStudent(student);
        return response;
    }

    @Transactional
    @Override
    public StudentGroupResponse addGroup(StudentGroupRequest studentGroupRequest) {
        Student student = studentService.getStudentById(studentGroupRequest.getStudentId());
        Group group = groupService.getGroupById(studentGroupRequest.getGroupId());
        return studentService.addGroup(student, group);
    }

    @Transactional
    @Override
    public StudentGroupResponse removeGroup(Long studentId, Long groupId) {
        Student student = studentService.getStudentById(studentId);
        Group group = groupService.getGroupById(groupId);
        return studentService.removeGroup(student, group);
    }

    @Transactional(readOnly = true)
    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 300))
    @Override
    public boolean isDoorAvailable(Long doorId, Long studentId) {
        Student student = studentService.getStudentById(studentId);
        Door door = doorService.getDoorById(doorId);

        Set<Permission> doorPermissions = door.getPermissions();
        Set<Permission> studentPermissions = student.getGroups().stream()
                .flatMap(group -> group.getPermissions().stream())
                .collect(Collectors.toSet());
        return studentPermissions.stream().anyMatch(doorPermissions::contains);
    }

    @Transactional(readOnly = true)
    @Override
    public StudentResponse getById(Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return studentService.convertStudentToStudentResponse(student);
    }
}
