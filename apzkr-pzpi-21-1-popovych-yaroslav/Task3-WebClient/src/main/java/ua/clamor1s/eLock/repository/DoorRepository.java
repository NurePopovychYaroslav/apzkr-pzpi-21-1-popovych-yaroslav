package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.Door;

public interface DoorRepository extends JpaRepository<Door, Long> {
}
