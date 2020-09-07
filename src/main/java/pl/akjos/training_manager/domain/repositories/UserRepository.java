package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String name);
}
