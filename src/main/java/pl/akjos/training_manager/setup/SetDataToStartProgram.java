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
import java.util.Optional;

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
        userTrainingRepository.save(userTraining);

        UserTraining userTraining2 = new UserTraining();
        userTraining2.setUserId(4L);
        userTraining2.setTrainingId(2L);
        userTrainingRepository.save(userTraining2);

        UserTraining userTraining3 = new UserTraining();
        userTraining3.setUserId(3L);
        userTraining3.setTrainingId(1L);
        userTrainingRepository.save(userTraining3);

        UserTraining userTraining4 = new UserTraining();
        userTraining4.setUserId(5L);
        userTraining4.setTrainingId(3L);
        userTrainingRepository.save(userTraining4);

        UserTraining userTraining5 = new UserTraining();
        userTraining5.setUserId(5L);
        userTraining5.setTrainingId(4L);
        userTrainingRepository.save(userTraining5);

//        set accept by team leader to test manager
        Optional<UserTraining> userTrainingForManagerToAccept = userTrainingRepository.findById(4L);
        if(userTrainingForManagerToAccept.isPresent()) {
            UserTraining ut = userTrainingForManagerToAccept.get();
            ut.setAcceptByTeamLeader(true);
            userTrainingRepository.save(ut);
        }
        userTrainingForManagerToAccept = userTrainingRepository.findById(5L);
        if (userTrainingForManagerToAccept.isPresent()) {
            UserTraining ut = userTrainingForManagerToAccept.get();
            ut.setAcceptByTeamLeader(true);
            userTrainingRepository.save(ut);
        }
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

        Training training3 = new Training();
        training3.setTitle("Junior accounting");
        training3.setDescription("The world of accounting can be vast and complex at times.  Once you get past the debits and credits, general ledger entries and financial reports, you realize there is much, much more to it!  In this course you and I will cover that much, much more!");
        training3.setDataStart(LocalDate.of(2020, 10, 15));
        training3.setPrice(1220.0);
        training3.setQuantityAvailable(2);
        training3.setTrainingDays(10);
        training3.setActive(true);
        list = new ArrayList<>();
        list.add(departmentRepository.findById(1L).get());
        training.setDepartments(list);
        trainingRepository.save(training3);

        Training training4 = new Training();
        training4.setTitle("Ask Better Questions - Build Better Relationships");
        training4.setDescription("Relationships: we all want better professional and personal relationships. Improving your questioning skills is a powerful way to build stronger, better relationships. That's why, according to Anthony Robbins, successful people ask better questions.");
        training4.setDataStart(LocalDate.of(2020, 10, 15));
        training4.setPrice(3000.0);
        training4.setQuantityAvailable(2);
        training4.setTrainingDays(10);
        training4.setActive(true);
        list = new ArrayList<>();
        list.add(departmentRepository.findById(1L).get());
        list.add(departmentRepository.findById(2L).get());
        training.setDepartments(list);
        trainingRepository.save(training4);
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

        User acWorker = new User();
        acWorker.setUsername("acworker");
        acWorker.setEmail("email_ac_worker_1");
        acWorker.setActive(true);
        acWorker.setPassword(passwordEncoder.encode("pass"));
        acWorker.setRole(roleRepository.getByName("ROLE_WORKER"));
        acWorker.setDepartment(departmentRepository.getByName("Accounting"));
        userRepository.save(acWorker);

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
