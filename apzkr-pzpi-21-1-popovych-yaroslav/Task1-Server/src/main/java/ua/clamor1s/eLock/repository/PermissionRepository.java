package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
