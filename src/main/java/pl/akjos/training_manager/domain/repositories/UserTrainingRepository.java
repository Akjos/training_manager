package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.UserTraining;

public interface UserTrainingRepository extends JpaRepository<UserTraining, Long> {
}
