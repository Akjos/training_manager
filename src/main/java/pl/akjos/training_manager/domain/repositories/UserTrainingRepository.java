package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akjos.training_manager.domain.model.UserTraining;

import java.util.List;

public interface UserTrainingRepository extends JpaRepository<UserTraining, Long> {
    Integer countAllById(Long id);

    List<UserTraining> getAllByUserId(Long id);
}
