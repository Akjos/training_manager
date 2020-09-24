package pl.akjos.training_manager.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.akjos.training_manager.domain.model.*;
import pl.akjos.training_manager.domain.repositories.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SetupDatabaseService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final TrainingRepository trainingRepository;
    private final UserTrainingRepository userTrainingRepository;
    private final PasswordEncoder passwordEncoder;

    public void setStartingData() {
        setRoleInDb();
        setDataInDatabase();
    }

    public void restoreDatabase() {
        deleteDatabase();
        setDataInDatabase();
    }

    public void setDataInDatabase() {
        setDepartmentInDb();
        setUserInDb();
        setTrainingInDb();
        setUserTrainingInDb();
    }

    private void deleteDatabase() {
        userTrainingRepository.deleteAll();
        trainingRepository.deleteAll();
        userRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    private void setUserTrainingInDb() {

        Long userId1 = userRepository.getByUsername("kowarad").getId();
        Long userId2 = userRepository.getByUsername("sowakla").getId();
        Long userId3 = userRepository.getByUsername("sojkprz").getId();

        Long trainingId1 = trainingRepository.getTrainingByTitle("Native English").getId();
        Long trainingId2 = trainingRepository.getTrainingByTitle("Problem-Solving Skills").getId();
        Long trainingId3 = trainingRepository.getTrainingByTitle("Ask Better Questions - Build Better Relationships").getId();

        List<UserTraining> trainingList = new ArrayList<>();
        UserTraining userTraining = new UserTraining();
        userTraining.setUserId(userId1);
        userTraining.setTrainingId(trainingId1);
        trainingList.add(userTraining);

        UserTraining userTraining2 = new UserTraining();
        userTraining2.setUserId(userId1);
        userTraining2.setTrainingId(trainingId2);
        trainingList.add(userTraining2);


        UserTraining userTraining3 = new UserTraining();
        userTraining3.setUserId(userId2);
        userTraining3.setTrainingId(trainingId2);
        trainingList.add(userTraining3);

        UserTraining userTraining4 = new UserTraining();
        userTraining4.setUserId(userId3);
        userTraining4.setTrainingId(trainingId2);
        trainingList.add(userTraining4);

        UserTraining userTraining5 = new UserTraining();
        userTraining5.setUserId(userId3);
        userTraining5.setTrainingId(trainingId3);
        trainingList.add(userTraining5);

        userTrainingRepository.saveAll(trainingList);

    }

    private void setTrainingInDb() {
        Training training = new Training();
        training.setTitle("Native English");
        training.setDescription("This course provides a strong foundation in basic reading skills for new or beginning-level learners of English. Your reading fluency and comprehension will increase as you study and practice effective strategies, develop your vocabulary, and read many interesting stories and articles.");
        training.setDataStart(LocalDate.of(2021, 6, 15));
        training.setPrice(800.0);
        training.setQuantityAvailable(5);
        training.setTrainingDays(20);
        training.setActive(true);
        List<Department> list = new ArrayList<>();
        list.add(departmentRepository.getByName("Humane resource"));
        training.setDepartments(list);
        trainingRepository.save(training);

        Training training2 = new Training();
        training2.setTitle("Problem-Solving Skills");
        training2.setDescription("When employers talk about problem-solving skills, they are often referring to the ability to handle difficult or unexpected situations in the workplace as well as complex business challenges. Organizations rely on people who can assess both kinds of situations and calmly identify solutions. Problem-solving skills are traits that enable you to do that. While problem-solving skills are valued by employers, they are also highly useful in other areas of life like relationship building and day-to-day decision making.");
        training2.setDataStart(LocalDate.of(2021, 6, 15));
        training2.setPrice(600.0);
        training2.setQuantityAvailable(3);
        training2.setTrainingDays(20);
        training2.setActive(true);
        list = new ArrayList<>();
        list.add(departmentRepository.getByName("Accounting"));
        list.add(departmentRepository.getByName("Humane resource"));
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
        list.add(departmentRepository.getByName("Accounting"));
        training3.setDepartments(list);
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
        list.add(departmentRepository.getByName("Accounting"));
        list.add(departmentRepository.getByName("Humane resource"));
        training4.setDepartments(list);
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
        List<User> userList = new ArrayList<>();
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setEmail("admin@company.com");
        adminUser.setActive(true);
        adminUser.setPassword(passwordEncoder.encode("pass"));
        adminUser.setRole(roleRepository.getByName("ROLE_ADMIN"));
        adminUser.setUserDetails(new UserDetails("admin", "admin"));
        userList.add(adminUser);

        User managerUser = new User();
        managerUser.setUsername("manager");
        managerUser.setEmail("manager@company.com");
        managerUser.setActive(true);
        managerUser.setPassword(passwordEncoder.encode("pass"));
        managerUser.setRole(roleRepository.getByName("ROLE_MANAGER"));
        managerUser.setUserDetails(new UserDetails("Anna", "Nowak"));
        userList.add(managerUser);

        User hrLeader = new User();
        hrLeader.setUsername("sowakla");
        hrLeader.setEmail("sowa.k@company.com");
        hrLeader.setActive(true);
        hrLeader.setPassword(passwordEncoder.encode("pass"));
        hrLeader.setRole(roleRepository.getByName("ROLE_TEAM_LEADER"));
        hrLeader.setDepartment(departmentRepository.getByName("Humane resource"));
        hrLeader.setUserDetails(new UserDetails("Klaudia", "Sowa"));
        userList.add(hrLeader);

        User hrWorker = new User();
        hrWorker.setUsername("kowarad");
        hrWorker.setEmail("kowalski.r@company.com");
        hrWorker.setActive(true);
        hrWorker.setPassword(passwordEncoder.encode("pass"));
        hrWorker.setRole(roleRepository.getByName("ROLE_WORKER"));
        hrWorker.setDepartment(departmentRepository.getByName("Humane resource"));
        hrWorker.setUserDetails(new UserDetails("Radosław", "Kowalski"));
        userList.add(hrWorker);

        User acWorker = new User();
        acWorker.setUsername("sojkprz");
        acWorker.setEmail("sojka.p@company.com");
        acWorker.setActive(true);
        acWorker.setPassword(passwordEncoder.encode("pass"));
        acWorker.setRole(roleRepository.getByName("ROLE_WORKER"));
        acWorker.setDepartment(departmentRepository.getByName("Accounting"));
        acWorker.setUserDetails(new UserDetails("Przemysław", "Sojka"));
        userList.add(acWorker);

        userRepository.saveAll(userList);
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
