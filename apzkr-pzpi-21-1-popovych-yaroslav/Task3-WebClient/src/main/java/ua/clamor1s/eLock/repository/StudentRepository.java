package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
