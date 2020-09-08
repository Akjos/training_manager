package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
