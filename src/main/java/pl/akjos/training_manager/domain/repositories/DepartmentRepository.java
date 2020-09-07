package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department getByName(String name);
}
