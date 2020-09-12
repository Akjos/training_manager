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
import java.util.stream.Collectors;

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

    public List<UserTrainingDTO> getUserTraining() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        return userTrainingRepository.getAllByUserId(loggedUser.getId())
                .stream()
                .map(this::convertUserTrainingToDTO)
                .collect(Collectors.toList());
    }

    public UserTrainingDTO convertUserTrainingToDTO(UserTraining userTraining) {
        UserTrainingDTO userTrainingDTO = new UserTrainingDTO();
        userTrainingDTO.setTitle(userTraining.getTraining().getTitle());
        userTrainingDTO.setTrainingId(userTraining.getTraining().getId());
        userTrainingDTO.setAcceptByManager(userTraining.isAcceptByManager());
        userTrainingDTO.setAcceptByTeamLeader(userTraining.isAcceptByTeamLeader());
        userTrainingDTO.setDenied(userTraining.isDenied());
        return userTrainingDTO;
    }
}
