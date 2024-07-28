package ua.clamor1s.eLock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.clamor1s.eLock.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
