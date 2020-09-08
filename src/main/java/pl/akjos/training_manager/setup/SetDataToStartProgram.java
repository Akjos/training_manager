package pl.akjos.training_manager.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.akjos.training_manager.domain.model.Department;
import pl.akjos.training_manager.domain.model.Role;
import pl.akjos.training_manager.domain.model.User;
import pl.akjos.training_manager.domain.repositories.DepartmentRepository;
import pl.akjos.training_manager.domain.repositories.RoleRepository;
import pl.akjos.training_manager.domain.repositories.UserRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetDataToStartProgram implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) {
        setRoleInDb();
        setDepartmentInDb();
        setUserInDb();

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
