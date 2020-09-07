package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}
