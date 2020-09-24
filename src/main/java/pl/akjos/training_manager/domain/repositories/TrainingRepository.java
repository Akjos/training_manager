package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.Training;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    Training getTrainingByTitle(String title);

    List<Training> getTrainingsByDepartmentsAndActiveTrue(Department department);

    List<Training> getAllByActiveTrue();

    @Query("SELECT t FROM Training t ,UserTraining ut WHERE ut.userId=:id AND t.id = ut.trainingId")
    List<Training> getAllTrainingAssignToUserByUserId(Long id);

    @Query("SELECT t FROM Training t JOIN t.departments d WHERE SIZE(t.departments) = 1 AND d.id = :id")
    List<Training> getTrainingsByDepartmentsSizeOneAndActiveTrue(Long id);
}
