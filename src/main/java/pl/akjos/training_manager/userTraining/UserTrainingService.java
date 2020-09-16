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

    public void deleteUserTrainingById(Long userTrainingId) {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        UserTraining userTrainingToDelete = userTrainingRepository.getToDeleteByIdAndUserId(userTrainingId, loggedUser.getId());
        log.debug("user training to delete {}", userTrainingToDelete);
        userTrainingRepository.delete(userTrainingToDelete);
    }

    public List<UserTrainingViewToManageListDTO> getTrainingListToAcceptForTeamLeader() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        log.debug("User: {}", loggedUser);
        return userTrainingRepository.getAllUserTrainingByDepartmentId(loggedUser.getDepartment().getId());
    }

    public UserTrainingDetailsToManageDTO getUserTrainingDetailsForManage(Long id) {
        return userTrainingRepository.getUserTrainingDetailsToManageById(id);
    }

    public Integer getCountAcceptTraining(String trainingName) {
        return userTrainingRepository.countAllByTrainingTitleAndAcceptByManager(trainingName);
    }

    public void acceptTraining(Long id) {
        UserTraining userTraining = userTrainingRepository.findById(id).get();
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            userTraining.setAcceptByTeamLeader(true);
        } else {
            userTraining.setAcceptByManager(true);
        }
        userTraining.setDenied(false);
        userTrainingRepository.save(userTraining);
    }

    public void deniedTraining(Long id) {
        UserTraining userTraining = userTrainingRepository.findById(id).get();
        if (SecurityUtils.getUserRole().equals("ROLE_TEAM_LEADER")) {
            userTraining.setAcceptByTeamLeader(false);
        } else {
            userTraining.setAcceptByManager(false);
        }
        userTraining.setDenied(true);
        userTrainingRepository.save(userTraining);
    }

    public List<UserTrainingViewToManageListDTO> getAcceptUserTrainingListForTeamLeader() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        log.debug("User: {}", loggedUser);
        return userTrainingRepository.getAllUserTrainingAcceptToEditForTeamLeaderByDepartmentId(loggedUser.getDepartment().getId());
    }

    public List<UserTrainingViewToManageListDTO> getDeniedUserTrainingListForTeamLeader() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        log.debug("User: {}", loggedUser);
        return userTrainingRepository.getAllUserTrainingDeniedToEditForTeamLeaderByDepartmentId(loggedUser.getDepartment().getId());
    }
}
