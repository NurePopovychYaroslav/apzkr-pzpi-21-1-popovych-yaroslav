package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.clamor1s.eLock.dto.request.StudentRequest;
import ua.clamor1s.eLock.dto.response.StudentResponse;
import ua.clamor1s.eLock.entity.Student;
import ua.clamor1s.eLock.utils.annotations.IgnoreBaseMapping;

@Mapper(componentModel = "spring")
public interface StudentMapper extends CreatableUpdatableMapper<Student> {

    StudentResponse studentToStudentResponse(Student student);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @IgnoreBaseMapping
    Student studentRequestToStudent(StudentRequest studentRequest);

    void updateStudent(@MappingTarget Student student, StudentRequest studentRequest);
}
