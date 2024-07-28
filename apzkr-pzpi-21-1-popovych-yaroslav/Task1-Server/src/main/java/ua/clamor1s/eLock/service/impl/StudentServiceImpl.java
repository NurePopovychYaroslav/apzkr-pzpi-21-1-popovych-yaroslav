package ua.clamor1s.eLock.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.StudentRequest;
import ua.clamor1s.eLock.dto.response.StudentGroupResponse;
import ua.clamor1s.eLock.dto.response.StudentResponse;
import ua.clamor1s.eLock.entity.Group;
import ua.clamor1s.eLock.entity.Student;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.mapper.StudentMapper;
import ua.clamor1s.eLock.repository.StudentRepository;
import ua.clamor1s.eLock.service.StudentService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final AuthUtils authUtils;

    @Transactional(readOnly = true)
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentGroupResponse> getStudentGroups(Student student) {
        return student.getGroups().stream()
                .map(group -> new StudentGroupResponse(student.getId(), group.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Student createStudent(StudentRequest studentRequest) {
        User user = authUtils.getCurrentUser().orElseThrow(() -> new RuntimeException());
        Student student = studentMapper.studentRequestToStudent(studentRequest);
        student.setCreatedBy(user.getEmail());

        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException());
    }

    @Transactional
    @Override
    public Student updateStudent(Student student, StudentRequest studentRequest) {
        studentMapper.updateStudent(student, studentRequest);
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Transactional
    @Override
    public StudentGroupResponse addGroup(Student student, Group group) {
        student.addGroup(group);
        student = studentRepository.save(student);
        return new StudentGroupResponse(student.getId(), group.getId());
    }

    @Transactional
    @Override
    public StudentGroupResponse removeGroup(Student student, Group group) {
        StudentGroupResponse response = new StudentGroupResponse(student.getId(), group.getId());
        student.removeGroup(group);
        studentRepository.save(student);
        return response;
    }

    @Override
    public StudentResponse convertStudentToStudentResponse(Student student) {
        return studentMapper.studentToStudentResponse(student);
    }
}
