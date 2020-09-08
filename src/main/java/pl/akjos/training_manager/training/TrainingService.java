package pl.akjos.training_manager.training;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.Training;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.domain.repositories.DepartmentRepository;
import pl.akjos.training_manager.domain.repositories.TrainingRepository;
import pl.akjos.training_manager.domain.repositories.UserRepository;
import pl.akjos.training_manager.utils.SecurityUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public List<String> getDepartmentList() {
        return departmentRepository.findAll().stream().
                map(Department::getName).
                collect(Collectors.toList());
    }

    public void saveToDb(TrainingDTO trainingDTO) {
        String userRole = SecurityUtils.getUserRole();
        Training training = new Training();
        if (userRole.equals("ROLE_TEAM_LEADER")) {
            String userName = SecurityUtils.getUsername();
            User loggedUser = userRepository.getByUsername(userName);
            log.debug("Logged user: {}", loggedUser);
            List<Department> departmentList = Arrays.asList(getDepartmentByName(loggedUser.getDepartment().getName()));
            training.setDepartments(departmentList);
        } else {
            List<Department> departmentList = trainingDTO.getDepartment().stream().
                    map(this::getDepartmentByName).
                    collect(Collectors.toList());
            training.setDepartments(departmentList);
        }
        training.setTitle(trainingDTO.getTitle());
        training.setDescription(trainingDTO.getDescription());
        training.setActive(true);
        training.setPrice(trainingDTO.getPrice());
        training.setQuantityAvailable(trainingDTO.getQuantityAvailable());
        training.setTrainingDays(trainingDTO.getTrainingDays());
        training.setDataStart(trainingDTO.getDataStart());
        log.debug("Save training: {} department: {}", training, training.getDepartments());
        trainingRepository.save(training);
    }

    private Department getDepartmentByName(String name) {
        return departmentRepository.getByName(name);
    }

    public List<TrainingDTO> getTrainingList() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        if (loggedUser.getDepartment() == null) {
            return trainingRepository.findAll().stream()
                    .map(this::convertTrainingToDTO)
                    .collect(Collectors.toList());
        } else {
            return trainingRepository.getTrainingsByDepartments(loggedUser.getDepartment())
                    .stream()
                    .map(this::convertTrainingToDTO)
                    .collect(Collectors.toList());
        }
    }

    private TrainingDTO convertTrainingToDTO(Training t) {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setId(t.getId());
        trainingDTO.setTitle(t.getTitle());
        trainingDTO.setDescription(t.getDescription());
        trainingDTO.setPrice(t.getPrice());
        trainingDTO.setQuantityAvailable(t.getQuantityAvailable());
        trainingDTO.setTrainingDays(t.getTrainingDays());
        trainingDTO.setDataStart(t.getDataStart());
        List<String> departmentNameList = t.getDepartments().stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        trainingDTO.setDepartment(departmentNameList);
        return trainingDTO;
    }
}
