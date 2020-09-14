package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.akjos.training_manager.domain.model.UserTraining;
import pl.akjos.training_manager.userTraining.UserTrainingDetailsDTO;
import pl.akjos.training_manager.userTraining.UserTrainingListDTO;

import java.util.List;

public interface UserTrainingRepository extends JpaRepository<UserTraining, Long> {
    Integer countAllById(Long id);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingListDTO(ut.id, t.title, ut.acceptByTeamLeader, ut.acceptByManager, ut.denied) FROM UserTraining ut LEFT JOIN Training t ON t.id = ut.trainingId WHERE ut.userId =:userId")
    List<UserTrainingListDTO> getAllUserTrainingListDTOByUserId(Long userId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingDetailsDTO(ut.id, t.title, t.description, t.trainingDays, t.dataStart, ut.acceptByTeamLeader, ut.acceptByManager, ut.denied) FROM UserTraining ut JOIN Training t ON ut.trainingId = t.id WHERE ut.id = :userTrainingId AND ut.userId = :userId")
    UserTrainingDetailsDTO getByUserIdAndTrainingId(Long userId, Long userTrainingId);

    UserTraining getToDeleteByIdAndUserId(Long id, Long userId);

}
