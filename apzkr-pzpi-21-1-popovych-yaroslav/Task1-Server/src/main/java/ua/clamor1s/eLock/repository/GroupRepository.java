package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
