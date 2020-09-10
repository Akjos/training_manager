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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public void saveNewTrainingToDb(TrainingDTO trainingDTO) {
        Training training = new Training();
        String userRole = SecurityUtils.getUserRole();
        if (userRole.equals("ROLE_TEAM_LEADER")) {
            String userName = SecurityUtils.getUsername();
            User loggedUser = userRepository.getByUsername(userName);
            log.debug("Logged user: {}", loggedUser);
            List<Department> departmentList = Collections.singletonList(getDepartmentByName(loggedUser.getDepartment().getName()));
            training.setDepartments(departmentList);
        } else {
            List<Department> departmentList = trainingDTO.getDepartment().stream().
                    map(this::getDepartmentByName).
                    collect(Collectors.toList());
            training.setDepartments(departmentList);
        }
        convertTrainingDTOToDb(trainingDTO, training);
        log.debug("Save new training: {} department: {}", training, training.getDepartments());
        trainingRepository.save(training);
    }

    private void convertTrainingDTOToDb(TrainingDTO trainingDTO, Training training) {
        training.setTitle(trainingDTO.getTitle());
        training.setDescription(trainingDTO.getDescription());
        training.setActive(true);
        training.setPrice(trainingDTO.getPrice());
        training.setQuantityAvailable(trainingDTO.getQuantityAvailable());
        training.setTrainingDays(trainingDTO.getTrainingDays());
        training.setDataStart(trainingDTO.getDataStart());
    }

    public void updateTrainingToDb(TrainingDTO trainingDTO) {
        Training training = trainingRepository.findById(trainingDTO.getId()).get();
        String userRole = SecurityUtils.getUserRole();
        if (!userRole.equals("ROLE_TEAM_LEADER")) {
            List<Department> departmentList = trainingDTO.getDepartment().stream().
                    map(this::getDepartmentByName).
                    collect(Collectors.toList());
            training.setDepartments(departmentList);
        }
        convertTrainingDTOToDb(trainingDTO, training);
        log.debug("Update new training: {} department: {}", training, training.getDepartments());
        trainingRepository.save(training);
    }

    private Department getDepartmentByName(String name) {
        return departmentRepository.getByName(name);
    }

    public List<TrainingDTO> getTrainingList() {
        User loggedUser = userRepository.getByUsername(SecurityUtils.getUsername());
        if (loggedUser.getDepartment() == null) {
            return trainingRepository.getAllByActiveTrue().stream()
                    .map(this::convertTrainingToDTO)
                    .collect(Collectors.toList());
        } else {
            return trainingRepository.getTrainingsByDepartmentsAndActiveTrue(loggedUser.getDepartment())
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
                .map(Department::getName)
                .collect(Collectors.toList());
        trainingDTO.setDepartment(departmentNameList);
        return trainingDTO;
    }

    public TrainingDTO getTrainingById(Long id) {
        Optional<Training> training = trainingRepository.findById(id);
        return training.map(this::convertTrainingToDTO).orElse(null);
    }

    public void disable(Long id) {
        System.out.println(id + "id !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        Optional<Training> trainingOptional = trainingRepository.findById(id);
        if (trainingOptional.isPresent()) {
            Training training = trainingOptional.get();
            training.setActive(false);
            trainingRepository.save(training);
        }
    }
}
