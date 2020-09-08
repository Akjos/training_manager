package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.Training;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> getTrainingsByDepartments(Department department);
}
