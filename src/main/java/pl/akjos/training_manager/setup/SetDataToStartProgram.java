package pl.akjos.training_manager.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.akjos.training_manager.domain.model.*;
import pl.akjos.training_manager.domain.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetDataToStartProgram implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final TrainingRepository trainingRepository;
    private final UserTrainingRepository userTrainingRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) {
        setRoleInDb();
        setDepartmentInDb();
        setUserInDb();
        setTrainingInDb();
        setUserTrainingInDb();
    }

    private void setUserTrainingInDb() {

        UserTraining userTraining = new UserTraining();
        userTraining.setUserId(4L);
        userTraining.setTrainingId(1L);
        userTraining.setAcceptByManager(false);
        userTraining.setAcceptByTeamLeader(false);
        userTraining.setDenied(false);
        userTrainingRepository.save(userTraining);

        UserTraining userTraining2 = new UserTraining();
        userTraining2.setUserId(4L);
        userTraining2.setTrainingId(2L);
        userTraining2.setAcceptByManager(false);
        userTraining2.setAcceptByTeamLeader(false);
        userTraining2.setDenied(false);
        userTrainingRepository.save(userTraining2);

        UserTraining userTraining3 = new UserTraining();
        userTraining3.setUserId(3L);
        userTraining3.setTrainingId(1L);
        userTraining3.setAcceptByManager(false);
        userTraining3.setAcceptByTeamLeader(false);
        userTraining3.setDenied(false);
        userTrainingRepository.save(userTraining3);

        UserTraining userTraining4 = new UserTraining();
        userTraining4.setUserId(2L);
        userTraining4.setTrainingId(2L);
        userTraining4.setAcceptByManager(false);
        userTraining4.setAcceptByTeamLeader(false);
        userTraining4.setDenied(false);
        userTrainingRepository.save(userTraining4);
    }

    private void setTrainingInDb() {
        Training training = new Training();
        training.setTitle("Native English");
        training.setDescription("48 h with native english for beginners");
        training.setDataStart(LocalDate.of(2021, 6, 15));
        training.setPrice(800.0);
        training.setQuantityAvailable(5);
        training.setTrainingDays(20);
        training.setActive(true);
        List<Department> list = new ArrayList<>();
        list.add(departmentRepository.findById(2L).get());
        training.setDepartments(list);
        trainingRepository.save(training);

        Training training2 = new Training();
        training2.setTitle("Native Polish");
        training2.setDescription("48 h with native english for beginners");
        training2.setDataStart(LocalDate.of(2021, 6, 15));
        training2.setPrice(600.0);
        training2.setQuantityAvailable(3);
        training2.setTrainingDays(20);
        training2.setActive(true);
        list = new ArrayList<>();
        list.add(departmentRepository.findById(2L).get());
        list.add(departmentRepository.findById(1L).get());
        training2.setDepartments(list);
        trainingRepository.save(training2);
    }

    private void setDepartmentInDb() {
        Department accountingDepartment = new Department();
        accountingDepartment.setName("Accounting");
        departmentRepository.save(accountingDepartment);

        Department humaneResourceDepartment = new Department();
        humaneResourceDepartment.setName("Humane resource");
        departmentRepository.save(humaneResourceDepartment);
    }

    private void setUserInDb() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setEmail("email_admin");
        adminUser.setActive(true);
        adminUser.setPassword(passwordEncoder.encode("pass"));
        adminUser.setRole(roleRepository.getByName("ROLE_ADMIN"));
        userRepository.save(adminUser);

        User managerUser = new User();
        managerUser.setUsername("manager");
        managerUser.setEmail("email_manager");
        managerUser.setActive(true);
        managerUser.setPassword(passwordEncoder.encode("pass"));
        managerUser.setRole(roleRepository.getByName("ROLE_MANAGER"));
        userRepository.save(managerUser);

        User hrLeader = new User();
        hrLeader.setUsername("hrLead");
        hrLeader.setEmail("email_hr_team_leader");
        hrLeader.setActive(true);
        hrLeader.setPassword(passwordEncoder.encode("pass"));
        hrLeader.setRole(roleRepository.getByName("ROLE_TEAM_LEADER"));
        hrLeader.setDepartment(departmentRepository.getByName("Humane resource"));
        userRepository.save(hrLeader);

        User hrworker = new User();
        hrworker.setUsername("hrworker");
        hrworker.setEmail("email_hr_worker_1");
        hrworker.setActive(true);
        hrworker.setPassword(passwordEncoder.encode("pass"));
        hrworker.setRole(roleRepository.getByName("ROLE_WORKER"));
        hrworker.setDepartment(departmentRepository.getByName("Humane resource"));
        userRepository.save(hrworker);


    }

    private void setRoleInDb() {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role managerRole = new Role();
        managerRole.setName("ROLE_MANAGER");
        roleRepository.save(managerRole);

        Role teamLeaderRole = new Role();
        teamLeaderRole.setName("ROLE_TEAM_LEADER");
        roleRepository.save(teamLeaderRole);

        Role workerRole = new Role();
        workerRole.setName("ROLE_WORKER");
        roleRepository.save(workerRole);
    }
}
