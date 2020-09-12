package pl.akjos.training_manager.userTraining;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.domain.model.UserTraining;
import pl.akjos.training_manager.domain.repositories.UserRepository;
import pl.akjos.training_manager.domain.repositories.UserTrainingRepository;
import pl.akjos.training_manager.utils.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTrainingService {
    private final UserTrainingRepository userTrainingRepository;
    private final UserRepository userRepository;

    public void addTrainingToUser(Long id) {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        UserTraining userTraining = new UserTraining();
        userTraining.setUserId(loggedUser.getId());
        userTraining.setTrainingId(id);
        log.debug(userTraining.toString());
        userTrainingRepository.save(userTraining);
    }

    public List<UserTrainingListDTO> getUserTraining() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        return userTrainingRepository.getAllUserTrainingListDTOByUserId(loggedUser.getId());
    }

    public UserTrainingDetailsDTO getUserTrainingDetails(Long userTrainingId) {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());

        return userTrainingRepository.getByUserIdAndTrainingId(loggedUser.getId(), userTrainingId);
    }
}
