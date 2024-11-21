package _oormthon.univ.flakeide.backend.auth.domain.repository;

import _oormthon.univ.flakeide.backend.auth.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
}
