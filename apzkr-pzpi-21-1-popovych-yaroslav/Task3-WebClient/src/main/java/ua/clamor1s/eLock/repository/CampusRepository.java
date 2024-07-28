package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.Campus;

import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {

    List<Campus> findAllByCreatedByOrderByUpdatedAtDesc(String createdBy);
}
